package render;

import render.opengl.GlBuffer;

public class Mesh {
    private final GlBuffer vertices;
    private final GlBuffer texCoords;
    private final GlBuffer faces;

    private int elementCount;

    public Mesh() {
        vertices = new GlBuffer();
        texCoords = new GlBuffer();
        faces = new GlBuffer();

        elementCount = 0;
    }

    public GlBuffer getVertices() {
        return vertices;
    }

    public GlBuffer getTexCoords() {
        return texCoords;
    }

    public GlBuffer getFaces() {
        return faces;
    }

    public int getElementCount() {
        return elementCount;
    }

    public void setElementCount(int elementCount) {
        this.elementCount = elementCount;
    }
}
