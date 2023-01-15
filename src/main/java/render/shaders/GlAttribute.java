package render.shaders;

import render.opengl.GlBuffer;

import static org.lwjgl.opengl.GL33.*;

public class GlAttribute {
    private final int attribLocation;
    private final boolean instanced;
    private boolean enabled = false;

    public GlAttribute(int programId, String name, boolean instanced) {
        attribLocation = glGetAttribLocation(programId, name);
        this.instanced = instanced;
    }

    private void enableArrays(int size) {
        if (enabled) {
            return;
        }
        for (int i = 0; i < size; i++) {
            glEnableVertexAttribArray(attribLocation + i);
            if (instanced) {
                glVertexAttribDivisor(attribLocation + i, 1);
            }
        }
        enabled = true;
    }

    private void loadBuffer(GlBuffer buffer, int glType, int width, int height) {
        enableArrays(height);

        int typeSize;
        if (glType == GL_FLOAT) {
            typeSize = 4;
        } else {
            throw new RuntimeException("Unsupported glType used");
        }

        buffer.bind();

        int byteWidthSize = width * typeSize;
        int stride = height * byteWidthSize;
        for (int i = 0; i < height; i++) {
            glVertexAttribPointer(attribLocation + i, width, glType, false, stride, (long) byteWidthSize * i);
        }
    }

    public void loadMatrix4f(GlBuffer buffer) {
        loadBuffer(buffer, GL_FLOAT, 4, 4);
    }

    public void loadMatrix3x2f(GlBuffer buffer) {
        loadBuffer(buffer, GL_FLOAT, 2, 3);
    }

    public void loadFloat(GlBuffer buffer) {
        loadBuffer(buffer, GL_FLOAT, 1, 1);
    }

    public void loadVector3f(GlBuffer buffer) {
        loadBuffer(buffer, GL_FLOAT, 3, 1);
    }

    public void loadVector2f(GlBuffer buffer) {
        loadBuffer(buffer, GL_FLOAT, 2, 1);
    }
}
