package render.camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera2D implements ICamera {
    private Vector3f position;
    private float width;
    private float height;

    public Camera2D(Vector3f position, float width, float height) {
        this.position = position;
        this.width = width;
        this.height = height;
    }

    @Override
    public Matrix4f getViewProjectionMatrix() {
        return (new Matrix4f()).ortho(position.x - width / 2, position.x + width / 2, position.y - height / 2, position.y + height / 2, -1.0f, 1.0f);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
