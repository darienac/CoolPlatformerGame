package render.opengl;

import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.MemoryStack.stackPush;

public class Texture implements AutoCloseable {
    private static final String TEXTURE_PATH = "textures/";

    private final int textureId;
    private final int width;
    private final int height;

    public Texture(String fileName) {
        try (MemoryStack stack = stackPush()) {
            stbi_set_flip_vertically_on_load(true);

            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer avChannels = stack.mallocInt(1);
            ByteBuffer decodedImage = stbi_load(TEXTURE_PATH + fileName, w, h, avChannels, 4);

            width = w.get(0);
            height = h.get(0);

            textureId = glGenTextures();
            if (textureId < 0) {
                throw new RuntimeException("Unable to create texture");
            }

            bind();
            glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, decodedImage);
            glGenerateMipmap(GL_TEXTURE_2D);

            stbi_image_free(decodedImage);
        }
    }

    public Texture(int width, int height) {
        this.width = width;
        this.height = height;

        textureId = glGenTextures();
        if (textureId < 0) {
            throw new RuntimeException("Unable to create texture");
        }

        bind();
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, 0);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, textureId);
    }

    public int getTextureId() {
        return textureId;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void close() {
        glDeleteTextures(textureId);
    }
}
