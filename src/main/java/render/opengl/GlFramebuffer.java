package render.opengl;

import render.IWindow;

import java.util.List;

import static org.lwjgl.opengl.GL32.*;

public class GlFramebuffer {
    private List<Texture> textures;
    private boolean useDepthBuffer;
    private int depthBufferId;
    private int framebufferId;
    private int width;
    private int height;

    public GlFramebuffer(List<Texture> textures, boolean useDepthBuffer) {
        this.textures = textures;
        this.useDepthBuffer = useDepthBuffer;

        width = textures.get(0).getWidth();
        height = textures.get(0).getHeight();

        framebufferId = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, framebufferId);

        if (useDepthBuffer) {
            depthBufferId = glGenRenderbuffers();
            glBindRenderbuffer(GL_RENDERBUFFER, depthBufferId);
            glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, width, height);
            glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthBufferId);
        }

        int[] drawBuffers = new int[textures.size()];
        for (int i = 0; i < textures.size(); i++) {
            glFramebufferTexture(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 + i, textures.get(i).getTextureId(), 0);
            drawBuffers[i] = GL_COLOR_ATTACHMENT0 + i;
        }
        glDrawBuffers(drawBuffers);

        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
            throw new RuntimeException("Unable to create framebuffer");
        }
    }

    public GlFramebuffer(int width, int height) {
        framebufferId = 0;
        this.width = width;
        this.height = height;
    }

    public void bind() {
        glBindFramebuffer(GL_FRAMEBUFFER, framebufferId);
        glViewport(0, 0, width, height);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void free() {
        glDeleteFramebuffers(framebufferId);
        if (useDepthBuffer) {
            glDeleteRenderbuffers(depthBufferId);
        }
    }
}
