package render.strategies;

import game.GameState;
import render.Renderer;

public interface IRenderStrategy {
    void render(GameState state, Renderer renderer);
}
