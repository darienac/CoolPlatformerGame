package render.camera;

import org.joml.Matrix4f;

public class BasicCamera implements ICamera {
    private Matrix4f identity = new Matrix4f();

    @Override
    public Matrix4f getViewProjectionMatrix() {
        return identity;
    }
}
