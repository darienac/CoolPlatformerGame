package render.opengl;

import java.util.List;

import static org.lwjgl.opengl.GL20.*;

public class GlRenderObjectGroup {
    private final int maxSize;
    private final Texture texture;
    private final GlBuffer modelMatrices;
    private final GlBuffer texCoordMatrices;
    private final float[] modelMatricesArray;
    private final float[] texCoordMatricesArray;

    private List<GlRenderObject> glRenderObjects;

    public GlRenderObjectGroup(List<GlRenderObject> glRenderObjects, Texture texture, int maxSize) {
        this.glRenderObjects = glRenderObjects;
        this.maxSize = maxSize;
        this.texture = texture;

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
