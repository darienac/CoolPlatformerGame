package render.strategies;

import state.GameState;
import org.joml.Vector3f;
import render.Renderer;
import render.resources.ResourceCache;

import java.util.Arrays;

public class TestStrategy extends PixelatedStrategy {
    @Override
    protected void renderPixelated(GameState state, Renderer renderer) {
        ResourceCache res = ResourceCache.getInstance();

        res.getTileCamera().setPosition(new Vector3f(state.getCameraX(), state.getCameraY(), 0.0f));
        res.getTileObjectGroup().free();
        res.setTileObjectGroup(res.getSpriteGrid().createObjectGroup(res.getTileCamera()));
        // System.out.println("Tiles drawn: " + res.getTileObjectGroup().getRenderObjects().size());
        res.getTileScene().setRenderGroups(Arrays.asList(res.getTileObjectGroup()));
        renderer.renderScene(res.getTileScene());
    }
}
