package state;

import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    public interface RenderObserver {
        void updateLevel();
    }

    public enum GameMode {
        LEVEL,
        LEVEL_EDITOR
    }

    private List<RenderObserver> renderObservers = new ArrayList<>();
    private GameMode mode = GameMode.LEVEL;

    private float cameraX = 0.0f;
    private float cameraY = 0.0f;

    // Level
    private GameLevel currentLevel = null;

    // Level Editor
    private Vector2i editorSelectorPos = new Vector2i(0, 0);

    public void addObserver(RenderObserver observer) {
        renderObservers.add(observer);
    }

    public GameMode getMode() {
        return mode;
    }

    public void setMode(GameMode mode) {
        this.mode = mode;
    }

    public float getCameraX() {
        return cameraX;
    }

    public void setCameraX(float cameraX) {
        this.cameraX = cameraX;
    }

    public float getCameraY() {
        return cameraY;
    }

    public void setCameraY(float cameraY) {
        this.cameraY = cameraY;
    }

    public GameLevel getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(GameLevel currentLevel) {
        this.currentLevel = currentLevel;
        for (RenderObserver observer : renderObservers) {
            observer.updateLevel();
        }
    }

    public Vector2i getEditorSelectorPos() {
        return editorSelectorPos;
    }

    public void setEditorSelectorPos(Vector2i editorSelectorPos) {
        this.editorSelectorPos = editorSelectorPos;
    }
}
