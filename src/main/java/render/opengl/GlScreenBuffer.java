package render.opengl;

import render.IWindow;

import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;

public class GlScreenBuffer extends GlFramebuffer {
    private IWindow window;

    public GlScreenBuffer(IWindow window) {
        super(window.getWidth(), window.getHeight());

        this.window = window;
    }

    public void bind() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        glViewport(0, 0, window.getWidth(), window.getHeight());
    }
}
