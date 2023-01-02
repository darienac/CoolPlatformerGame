package render;

import game.GameState;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import render.camera.BasicCamera;
import render.camera.Camera2D;
import render.camera.CroppedFillScreenCamera;
import render.camera.ICamera;
import render.opengl.*;
import render.shaders.SpritesShader;

import java.util.Arrays;

import static org.lwjgl.opengl.GL33.*;

public class Renderer {
    private static final int GAME_WIDTH = 432;
    private static final int GAME_HEIGHT = 240;
    private static final int TILE_RESOLUTION = 16;

    private IWindow window;
    private GameState state;
    private ResourceCache res;

    private int width = 0;
    private int height = 0;
    private double lastFramerateCheck;
    private int framesPerSecond;

    private GlFramebuffer screenTarget;
    private GlFramebuffer pixelatedBuffer;
    private RenderScene cropScene;
    private RenderScene tileScene;
    private Camera2D tileCamera;
    private GlRenderObjectGroup tileObjectGroup;

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

        screenTarget = new GlScreenBuffer(window);

        // cropScene setup
        Texture pixelatedTexture = new Texture(GAME_WIDTH, GAME_HEIGHT);
        pixelatedBuffer = new GlFramebuffer(Arrays.asList(pixelatedTexture), true);
        Sprite pixelatedSprite = new Sprite(pixelatedTexture);
        GlRenderObjectGroup objectGroup = new GlRenderObjectGroup(Arrays.asList(new GlRenderObject(pixelatedSprite, new Matrix4f())), pixelatedTexture, 1);
        ICamera cropCamera = new CroppedFillScreenCamera(window, (float) GAME_WIDTH / GAME_HEIGHT);
        cropScene = new RenderScene(cropCamera, Arrays.asList(objectGroup), screenTarget);

        // tileScene setup
        tileCamera = new Camera2D(new Vector3f(0.0f, 0.0f, 0.0f), (float) GAME_WIDTH / TILE_RESOLUTION, (float) GAME_HEIGHT / TILE_RESOLUTION);
        tileObjectGroup = res.getSpriteGrid().createObjectGroup(tileCamera);
        tileScene = new RenderScene(tileCamera, Arrays.asList(tileObjectGroup), pixelatedBuffer);

        lastFramerateCheck = GLFW.glfwGetTime();
        framesPerSecond = 0;
    }

    public synchronized void render() {
        if (width != window.getWidth() || height != window.getHeight()) {
            width = window.getWidth();
            height = window.getHeight();
        }

        pixelatedBuffer.bind();

        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        tileCamera.setPosition(new Vector3f(state.getCameraX(), state.getCameraY(), 0.0f));
        tileObjectGroup.free();
        tileObjectGroup = res.getSpriteGrid().createObjectGroup(tileCamera);
        System.out.println("Tiles drawn: " + tileObjectGroup.getRenderObjects().size());
        tileScene.setRenderGroups(Arrays.asList(tileObjectGroup));
        renderScene(tileScene);

        cropScene.getRenderTarget().bind();
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        renderScene(cropScene);

        framesPerSecond++;
        if (GLFW.glfwGetTime() - lastFramerateCheck > 1.0) {
            lastFramerateCheck = Math.floor(GLFW.glfwGetTime() - lastFramerateCheck) + lastFramerateCheck;
            System.out.println("FPS: " + framesPerSecond);
            framesPerSecond = 0;
        }
    }

    public void renderObjectGroup(GlRenderObjectGroup objectGroup, ICamera camera) {
        objectGroup.update();

        SpritesShader shader = res.getTestShader();
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
}
