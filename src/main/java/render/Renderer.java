package render;

import render.strategies.LevelEditorStrategy;
import state.GameState;
import org.lwjgl.glfw.GLFW;
import render.camera.ICamera;
import render.opengl.*;
import render.resources.ResourceCache;
import render.shaders.SpritesShader;
import render.strategies.IRenderStrategy;

import static org.lwjgl.opengl.GL33.*;

public class Renderer implements GameState.RenderObserver {
    private IWindow window;
    private GameState state;
    private ResourceCache res;
    private IRenderStrategy renderStrategy;

    private int width = 0;
    private int height = 0;
    private double lastFramerateCheck;
    private int framesPerSecond;

    public Renderer(IWindow window, GameState state) {
        this.window = window;
        this.state = state;
        res = ResourceCache.getInstance();

        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);
        glEnable(GL_MULTISAMPLE);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_CULL_FACE);

        res.setupWindowRendering(this);

        renderStrategy = new LevelEditorStrategy();

        lastFramerateCheck = GLFW.glfwGetTime();
        framesPerSecond = 0;
    }

    public synchronized void render() {
        if (width != window.getWidth() || height != window.getHeight()) {
            width = window.getWidth();
            height = window.getHeight();
        }

        renderStrategy.render(state, this);

        framesPerSecond++;
        if (GLFW.glfwGetTime() - lastFramerateCheck > 1.0) {
            lastFramerateCheck = Math.floor(GLFW.glfwGetTime() - lastFramerateCheck) + lastFramerateCheck;
            System.out.println("FPS: " + framesPerSecond);
            framesPerSecond = 0;
        }
    }

    public void renderObjectGroup(GlRenderObjectGroup objectGroup, ICamera camera) {
        objectGroup.update();

        SpritesShader shader = res.getLevelShader();
        shader.bind();
        shader.bindMesh(res.getSquareMesh());
        shader.bindMatrices(camera.getViewProjectionMatrix());
        shader.bindRenderObjects(objectGroup);

        shader.bindTexture(objectGroup.getTexture());

        res.getSquareMesh().getFaces().bind(GL_ELEMENT_ARRAY_BUFFER);
        glDrawElementsInstanced(GL_TRIANGLES, res.getSquareMesh().getElementCount(), GL_UNSIGNED_INT, 0, objectGroup.getRenderObjects().size());
    }

    public void renderScene(RenderScene scene) {
        scene.getRenderTarget().bind();
        for (GlRenderObjectGroup objectGroup : scene.getRenderGroups()) {
            renderObjectGroup(objectGroup, scene.getCamera());
        }
    }

    @Override
    public void updateLevel() {
        if (res.getLevelTiles() != null) {
            res.getLevelTiles().freeObjectGroup();
        }
        res.setLevelTiles(new SpriteGrid(state.getCurrentLevel(), res.getLevelRenderSettings()));
        res.getLevelTiles().createObjectGroup(res.getTileCamera());
    }

    public IWindow getWindow() {
        return window;
    }
}
