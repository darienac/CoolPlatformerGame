package render.opengl;

import static org.lwjgl.opengl.GL20.*;

public class GlBuffer {
    private final int bufferId;
    private final int drawType;

    public GlBuffer(int drawType) {
        bufferId = glGenBuffers();
        this.drawType = drawType;
    }

    public GlBuffer() {
        bufferId = glGenBuffers();
        drawType = GL_STATIC_DRAW;
    }

    public void setData(float[] data) {
        bind();
        glBufferData(GL_ARRAY_BUFFER, data, drawType);
    }

    public void setData(int[] data) {
        bind();
        glBufferData(GL_ARRAY_BUFFER, data, drawType);
    }

    public void updateData(float[] data) {
        bind();
        glBufferSubData(GL_ARRAY_BUFFER, 0, data);
    }

    public void bind() {
        bind(GL_ARRAY_BUFFER);
    }

    public void bind(int target) {
        glBindBuffer(target, bufferId);
    }

    public void free() {
        glDeleteBuffers(bufferId);
    }
}
