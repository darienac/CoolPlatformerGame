package render.strategies;

import org.joml.Matrix4f;
import render.Renderer;
import render.resources.ResourceCache;
import state.GameState;

public class LevelEditorStrategy extends LevelStrategy {
    @Override
    protected void renderTileScene(GameState state, Renderer renderer) {
        ResourceCache res = ResourceCache.getInstance();

        res.getLevelEditorSelector().setTransform(res.getLevelTiles().getGridPosTransform(state.getEditorSelectorPos().x, state.getEditorSelectorPos().y));
        res.getTileScene().getRenderGroups().add(res.getLevelEditorSelector());

        super.renderTileScene(state, renderer);
    }
}
