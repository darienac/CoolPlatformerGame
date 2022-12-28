package render;

import org.joml.Matrix3x2f;
import render.opengl.Texture;

public class Sprite {
    private Texture texture;
    private Matrix3x2f texCoordTransform;

    public Sprite(Texture texture) {
        this.texture = texture;
        this.texCoordTransform = new Matrix3x2f();
    }

    public Sprite(Texture texture, Matrix3x2f texCoordTransform) {
        this.texture = texture;
        this.texCoordTransform = texCoordTransform;

    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Matrix3x2f getTexCoordTransform() {
        return texCoordTransform;
    }

    public void setTexCoordTransform(Matrix3x2f texCoordTransform) {
        this.texCoordTransform = texCoordTransform;
    }
}
