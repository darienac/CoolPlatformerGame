package render.strategies;

import render.opengl.GlRenderObjectGroup;
import state.GameState;
import org.joml.Vector3f;
import render.Renderer;
import render.resources.ResourceCache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LevelStrategy extends PixelatedStrategy {
    @Override
    protected void renderPixelated(GameState state, Renderer renderer) {
        ResourceCache res = ResourceCache.getInstance();

        res.getTileCamera().setPosition(new Vector3f(state.getCameraPos(), 0.0f));
        if (res.getTileObjectGroup() != null) {
            res.getTileObjectGroup().free();
        }
        res.setTileObjectGroup(res.getLevelTiles().createObjectGroup(res.getTileCamera()));
        // System.out.println("Tiles drawn: " + res.getTileObjectGroup().getRenderObjects().size());

        List<GlRenderObjectGroup> renderGroups = new ArrayList<>();
        renderGroups.add(res.getTileObjectGroup());
        res.getTileScene().setRenderGroups(renderGroups);

        renderTileScene(state, renderer);
        // TODO: make sure renderer.renderScene is the last call for LevelEditor
    }

    protected void renderTileScene(GameState state, Renderer renderer) {
        renderer.renderScene(ResourceCache.getInstance().getTileScene());
    }
}
