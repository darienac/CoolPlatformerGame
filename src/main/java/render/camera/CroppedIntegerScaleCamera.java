package render.camera;

import org.joml.Matrix4f;
import render.IWindow;

public class CroppedIntegerScaleCamera implements ICamera {
    private IWindow window;
    private int gameWidth;
    private int gameHeight;

    public CroppedIntegerScaleCamera(IWindow window, int gameWidth, int gameHeight) {
        this.window = window;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
    }

    @Override
    public Matrix4f getViewProjectionMatrix() {
        int scale = Math.min(window.getWidth() / gameWidth, window.getHeight() / gameHeight);

        float xStretch = 0.5f * window.getWidth() / (gameWidth * scale);
        float yStretch = 0.5f * window.getHeight() / (gameHeight * scale);

        return new Matrix4f().ortho2D(-xStretch, xStretch, -yStretch, yStretch);
    }
}
