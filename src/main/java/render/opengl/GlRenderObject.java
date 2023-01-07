package render.opengl;

import org.joml.Matrix4f;
import render.Sprite;

public class GlRenderObject {
    private Sprite sprite;
    private Matrix4f transform;

    public GlRenderObject(Sprite sprite) {
        this(sprite, new Matrix4f());
    }

    public GlRenderObject(Sprite sprite, Matrix4f transform) {
        this.sprite = sprite;
        this.transform = transform;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Matrix4f getTransform() {
        return transform;
    }

    public void setTransform(Matrix4f transform) {
        this.transform = transform;
    }
}
