package render.camera;

import org.joml.Matrix4f;
import render.IWindow;

public class CroppedFillScreenCamera implements ICamera {
    private IWindow window;
    private float desiredAspect;

    public CroppedFillScreenCamera(IWindow window, float desiredAspect) {
        this.window = window;
        this.desiredAspect = desiredAspect;
    }

    @Override
    public Matrix4f getViewProjectionMatrix() {
        float windowAspect = (float) window.getWidth() / window.getHeight();
        if (windowAspect > desiredAspect) {
            float diff = windowAspect - desiredAspect;
            return new Matrix4f().ortho2D(-0.5f - (diff * 0.5f), 0.5f + (diff * 0.5f), -0.5f, 0.5f);
        } else {
            float diff = (1 / windowAspect) - (1 / desiredAspect);
            return new Matrix4f().ortho2D(-0.5f, 0.5f, -0.5f - (diff * 0.5f), 0.5f + (diff * 0.5f));
        }
    }
}
