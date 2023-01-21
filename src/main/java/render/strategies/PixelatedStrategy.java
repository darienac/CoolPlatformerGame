package render.strategies;

import state.GameState;
import render.Renderer;
import render.resources.ResourceCache;

import static org.lwjgl.opengl.GL33.*;

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
