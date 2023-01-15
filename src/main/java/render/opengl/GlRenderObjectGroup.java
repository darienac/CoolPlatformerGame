package render.opengl;

import org.joml.Matrix3x2f;
import render.Renderer;
import render.Sprite;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL20.*;

public class GlRenderObjectGroup {
    private final int maxSize;
    private Texture texture;
    private GlBuffer modelMatrices;
    private GlBuffer texCoordMatrices;
    private GlBuffer opacities;
    private float[] modelMatricesArray;
    private float[] texCoordMatricesArray;
    private float[] opacitiesArray;

    private List<GlRenderObject> glRenderObjects;

    public GlRenderObjectGroup(List<GlRenderObject> glRenderObjects, Texture texture, int maxSize) {
        this.glRenderObjects = glRenderObjects;
        this.maxSize = maxSize;
        this.texture = texture;

        initBuffers(maxSize);
    }

    private void initBuffers(int maxSize) {
        modelMatricesArray = new float[maxSize * 16];
        texCoordMatricesArray = new float[maxSize * 6];
        opacitiesArray = new float[maxSize];

        modelMatrices = new GlBuffer(GL_DYNAMIC_DRAW);
        texCoordMatrices = new GlBuffer(GL_DYNAMIC_DRAW);
        opacities = new GlBuffer(GL_DYNAMIC_DRAW);

        updateArrays();
        modelMatrices.setData(modelMatricesArray);
        texCoordMatrices.setData(texCoordMatricesArray);
        opacities.setData(opacitiesArray);
    }

    public void updateArrays(int index) {
        GlRenderObject glRenderObject = glRenderObjects.get(index);
        glRenderObject.getTransform().get(modelMatricesArray, index * 16);
        glRenderObject.getSprite().getTexCoordTransform().get(texCoordMatricesArray, index * 6);
        opacitiesArray[index] = glRenderObject.getOpacity();
    }

    public void updateArrays() {
        for (int i = 0; i < glRenderObjects.size(); i++) {
            updateArrays(i);
        }
    }

    public void update() {
        modelMatrices.updateData(modelMatricesArray);
        texCoordMatrices.updateData(texCoordMatricesArray);
        opacities.updateData(opacitiesArray);
    }

    public GlBuffer getModelMatrices() {
        return modelMatrices;
    }

    public GlBuffer getTexCoordMatrices() {
        return texCoordMatrices;
    }

    public GlBuffer getOpacities() {
        return opacities;
    }

    public List<GlRenderObject> getRenderObjects() {
        return glRenderObjects;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void free() {
        modelMatrices.free();
        texCoordMatrices.free();
        opacities.free();
    }
}
