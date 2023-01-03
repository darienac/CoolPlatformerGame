package state;

public class GameState {
    public enum GameMode {
        LEVEL
    }

    private GameMode mode = GameMode.LEVEL;

    private float cameraX = 0.0f;
    private float cameraY = 0.0f;

    private GameLevel currentLevel;

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
    }
}
