package render.strategies;

import state.GameState;
import render.Renderer;

public interface IRenderStrategy {
    void render(GameState state, Renderer renderer);
}
