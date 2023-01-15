package render.opengl;

import org.joml.Matrix3x2f;
import org.joml.Matrix4f;
import render.Sprite;

import java.util.Collections;

public class GlRenderObjectSingleGroup extends GlRenderObjectGroup {
    private GlRenderObject renderObject;

    public GlRenderObjectSingleGroup(String textureName) {
        this(new Texture(textureName));
    }

    public GlRenderObjectSingleGroup(Texture texture) {
        this(new Sprite(texture));
    }

    public GlRenderObjectSingleGroup(Sprite sprite) {
        this(new GlRenderObject(sprite));
    }

    public GlRenderObjectSingleGroup(GlRenderObject renderObject) {
        super(Collections.singletonList(renderObject), renderObject.getSprite().getTexture(), 1);
        this.renderObject = renderObject;
    }

    public void setTexture(Texture texture) {
        renderObject.getSprite().setTexture(texture);
        super.setTexture(texture);
    }

    public Matrix4f getTransform() {
        return renderObject.getTransform();
    }

    public void setTransform(Matrix4f transform) {
        renderObject.setTransform(transform);
        updateArrays();
    }

    public Matrix3x2f getTexCoordTransform() {
        return renderObject.getSprite().getTexCoordTransform();
    }

    public void setTexCoordTransform(Matrix3x2f transform) {
        renderObject.getSprite().setTexCoordTransform(transform);
        updateArrays();
    }

    public GlRenderObject getRenderObject() {
        return renderObject;
    }

    public Sprite getSprite() {
        return renderObject.getSprite();
    }

    public void setSprite(Sprite sprite) {
        renderObject.setSprite(sprite);
        setTexture(sprite.getTexture());
        updateArrays();
    }
}
