package render.strategies;

import game.GameState;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import render.Renderer;
import render.ResourceCache;

import java.util.Arrays;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;

public class PixelatedStrategy implements IRenderStrategy {
    @Override
    public void render(GameState state, Renderer renderer) {
        ResourceCache res = ResourceCache.getInstance();

        res.getPixelatedBuffer().bind();

        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        res.getTileCamera().setPosition(new Vector3f(state.getCameraX(), state.getCameraY(), 0.0f));
        res.getTileObjectGroup().free();
        res.setTileObjectGroup(res.getSpriteGrid().createObjectGroup(res.getTileCamera()));
        // System.out.println("Tiles drawn: " + res.getTileObjectGroup().getRenderObjects().size());
        res.getTileScene().setRenderGroups(Arrays.asList(res.getTileObjectGroup()));
        renderer.renderScene(res.getTileScene());

        res.getCropScene().getRenderTarget().bind();
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        renderer.renderScene(res.getCropScene());
    }
}
