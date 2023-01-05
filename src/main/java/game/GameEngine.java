package game;

import state.GameLevel;
import state.GameState;
import org.lwjgl.glfw.GLFW;

public class GameEngine implements Runnable {
    private IWindow window;
    private GameState state;
    private KeyboardControls controls;
    private double lastTime;
    private double lastTimeAbsolute;

    public GameEngine(IWindow window, GameState state) {
        this.window = window;
        this.state = state;
        controls = new KeyboardControls(window);

        state.setMode(GameState.GameMode.LEVEL_EDITOR);
        state.setCurrentLevel(new GameLevel(new GameLevel.Tile[][] {{GameLevel.Tile.HIT_BLOCK, GameLevel.Tile.QBLOCK}, {GameLevel.Tile.ROCK, GameLevel.Tile.ROCK_GRASS}}, 2, 2));
    }

    @Override
    public synchronized void run() {
        lastTime = GLFW.glfwGetTime();
        lastTimeAbsolute = lastTime;
        while (true) {
            lastTime = runSteps(lastTime);
            lastTimeAbsolute = GLFW.glfwGetTime();
            try {
                this.wait(10);
            } catch (InterruptedException ex) {
                break;
            }
            if (Thread.currentThread().isInterrupted()) {
                break;
            }
        }
    }

    public double runSteps(double lastTime) {
        double time = GLFW.glfwGetTime();
        double timeDelta = time - lastTime;

        if (controls.isCameraMoveLeft()) {
            state.setCameraX(state.getCameraX() - 8.0f * (float) timeDelta);
        }
        if (controls.isCameraMoveRight()) {
            state.setCameraX(state.getCameraX() + 8.0f * (float) timeDelta);
        }
        if (controls.isCameraMoveUp()) {
            state.setCameraY(state.getCameraY() + 8.0f * (float) timeDelta);
        }
        if (controls.isCameraMoveDown()) {
            state.setCameraY(state.getCameraY() - 8.0f * (float) timeDelta);
        }

        return time;
    }
}
