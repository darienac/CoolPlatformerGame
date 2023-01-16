package game;

import org.joml.Vector2f;
import org.joml.Vector2i;
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
        state.setCurrentLevel(new GameLevel(new GameLevel.Tile[10][10], 10, 10));
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

        Vector2f cameraMove = new Vector2f();
        controls.getCameraMove().mul((float) (10.0 * timeDelta), cameraMove);
        state.getCameraPos().add(cameraMove);

        Vector2i editorPos = state.getEditorSelectorPos();
        editorPos.add(controls.getEditorSelectorMove());
        if (editorPos.x < 0) {
            editorPos.x = 0;
        }
        if (editorPos.y < 0) {
            editorPos.y = 0;
        }
        if (editorPos.x >= state.getCurrentLevel().getWidth()) {
            editorPos.x = state.getCurrentLevel().getWidth() - 1;
        }
        if (editorPos.y >= state.getCurrentLevel().getHeight()) {
            editorPos.y = state.getCurrentLevel().getHeight() - 1;
        }

        controls.resetEditorSelectorMove();

        if (controls.getEditorSelectedTileMove() > 0) {
            state.nextEditorSelectedTile();
        } else if (controls.getEditorSelectedTileMove() < 0) {
            state.previousEditorSelectedTile();
        }
        controls.resetEditorSelectedTileMove();

        if (controls.getEditorSelectorPlace()) {
            state.getCurrentLevel().getTiles()[state.getEditorSelectorPos().x()][state.getEditorSelectorPos().y()] = state.getEditorSelectedTile();
            state.updateCurrentLevel();
            controls.resetEditorSelectorPlace();
        }

        return time;

        // TODO: make behavior based on current state (maybe use strategies here too?)
    }
}
