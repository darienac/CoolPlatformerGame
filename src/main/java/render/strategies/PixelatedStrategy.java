package render.strategies;

import game.GameState;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import render.Renderer;
import render.ResourceCache;

import java.util.Arrays;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;

public abstract class PixelatedStrategy implements IRenderStrategy {
    @Override
    public void render(GameState state, Renderer renderer) {
        ResourceCache res = ResourceCache.getInstance();

        res.getPixelatedBuffer().bind();

        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        renderPixelated(state, renderer);

        res.getCropScene().getRenderTarget().bind();
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        renderer.renderScene(res.getCropScene());
    }

    protected abstract void renderPixelated(GameState state, Renderer renderer);
}
