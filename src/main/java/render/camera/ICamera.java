package render.camera;

import org.joml.Matrix4f;

public interface ICamera {
    public Matrix4f getViewProjectionMatrix();
}
