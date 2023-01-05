package render.opengl;

import org.joml.Matrix3x2f;
import render.Renderer;
import render.Sprite;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL20.*;

public class GlRenderObjectGroup {
    private final int maxSize;
    private final Texture texture;
    private GlBuffer modelMatrices;
    private GlBuffer texCoordMatrices;
    private float[] modelMatricesArray;
    private float[] texCoordMatricesArray;

    private List<GlRenderObject> glRenderObjects;

    public GlRenderObjectGroup(List<GlRenderObject> glRenderObjects, Renderer renderer, int maxSize) {
        List<Texture> textures = new ArrayList<>();
        for (GlRenderObject renderObject : glRenderObjects) {
            textures.add(renderObject.getSprite().getTexture());
        }

        TextureAtlas atlas = new TextureAtlas(textures, renderer);
        texture = atlas;
        List<Matrix3x2f> texCoordTransforms = atlas.getTexCoordTransforms();

        for (int i = 0; i < texCoordTransforms.size(); i++) {
            Sprite sprite = glRenderObjects.get(i).getSprite();
            sprite.setTexture(texture);
            Matrix3x2f texCoordTransform = new Matrix3x2f();
            texCoordTransforms.get(i).mul(sprite.getTexCoordTransform(), texCoordTransform);
            sprite.setTexCoordTransform(texCoordTransform);
        }

        this.glRenderObjects = glRenderObjects;
        this.maxSize = maxSize;

        initBuffers(maxSize);
    }

    public GlRenderObjectGroup(List<GlRenderObject> glRenderObjects, Texture texture, int maxSize) {
        this.glRenderObjects = glRenderObjects;
        this.maxSize = maxSize;
        this.texture = texture;

        initBuffers(maxSize);
    }

    private void initBuffers(int maxSize) {
        modelMatricesArray = new float[maxSize * 16];
        texCoordMatricesArray = new float[maxSize * 6];

        modelMatrices = new GlBuffer(GL_DYNAMIC_DRAW);
        texCoordMatrices = new GlBuffer(GL_DYNAMIC_DRAW);

        updateArrays();
        modelMatrices.setData(modelMatricesArray);
        texCoordMatrices.setData(texCoordMatricesArray);
    }

    public void updateArrays(int index) {
        GlRenderObject glRenderObject = glRenderObjects.get(index);
        glRenderObject.getTransform().get(modelMatricesArray, index * 16);
        glRenderObject.getSprite().getTexCoordTransform().get(texCoordMatricesArray, index * 6);
    }

    public void updateArrays() {
        for (int i = 0; i < glRenderObjects.size(); i++) {
            updateArrays(i);
        }
    }

    public void update() {
        modelMatrices.updateData(modelMatricesArray);
        texCoordMatrices.updateData(texCoordMatricesArray);
    }

    public GlBuffer getModelMatrices() {
        return modelMatrices;
    }

    public GlBuffer getTexCoordMatrices() {
        return texCoordMatrices;
    }

    public List<GlRenderObject> getRenderObjects() {
        return glRenderObjects;
    }

    public Texture getTexture() {
        return texture;
    }

    public void free() {
        modelMatrices.free();
        texCoordMatrices.free();
    }
}
