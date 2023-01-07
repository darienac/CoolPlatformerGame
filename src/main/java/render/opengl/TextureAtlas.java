package render.opengl;

import org.joml.Matrix3x2f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import render.RenderScene;
import render.Renderer;
import render.Sprite;
import render.camera.Camera2D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextureAtlas extends Texture {
    private static final float GUESS_EFFICIENCY = 0.5f;

    private List<Matrix3x2f> texCoordTransforms;

    public TextureAtlas(List<Texture> textures, Renderer renderer) {
        this(textures, renderer, getOptimalWidth(textures));
    }

    private TextureAtlas(List<Texture> textures, Renderer renderer, int width) {
        this(textures, renderer, width, width);
    }

    private static int getOptimalWidth(List<Texture> textures) {
        int totalPixels = 0;
        for (Texture texture : textures) {
            totalPixels += texture.getWidth() * texture.getHeight();
        }
        return (int) Math.ceil(Math.sqrt(totalPixels / GUESS_EFFICIENCY));
    }

    public TextureAtlas(List<Texture> textures, Renderer renderer, int width, int height) {
        super(width, height);
        System.out.println("width: " + width + ", height: " + height);

        GlFramebuffer framebuffer = new GlFramebuffer(Arrays.asList(this), false);

        Sprite sprite = new Sprite(null, new Matrix3x2f());
        GlRenderObject renderObject = new GlRenderObject(sprite, new Matrix4f());
        GlRenderObjectSingleGroup objectGroup = new GlRenderObjectSingleGroup(renderObject);
        RenderScene scene = new RenderScene(new Camera2D(new Vector3f(0.5f, 0.5f, 0.0f), 1.0f, 1.0f), Arrays.asList(objectGroup), framebuffer);

        TextureChunk chunk = new TextureChunk(0, 0, width, height);
        texCoordTransforms = new ArrayList<>();
        for (Texture texture : textures) {
            TextureChunk chunkSpace = chunk.allocateSpace(texture.getWidth(), texture.getHeight());
            System.out.println(chunkSpace);
            if (chunkSpace == null) {
                throw new RuntimeException("Unable to allocate space for texture in atlas");
            }

            texCoordTransforms.add(getTexCoordTransform(chunkSpace));

            objectGroup.setTexture(texture);

            objectGroup.setTransform(getSpriteTransform(chunkSpace));
            renderer.renderScene(scene);
        }

        framebuffer.free();
        objectGroup.free();
    }

    private Matrix4f getSpriteTransform(TextureChunk chunk) {
        return new Matrix4f().translate((float) chunk.getX() / getWidth(), (float) chunk.getY() / getHeight(), 0.0f).scale((float) chunk.getWidth() / getWidth(), (float) chunk.getHeight() / getHeight(), 1.0f).translate(0.5f, 0.5f, 0.0f);
    }

    private Matrix3x2f getTexCoordTransform(TextureChunk chunk) {
        return new Matrix3x2f().translate((float) chunk.getX() / getWidth(), (float) chunk.getY() / getHeight()).scale((float) chunk.getWidth() / getWidth(), (float) chunk.getHeight() / getHeight());
    }

    public List<Matrix3x2f> getTexCoordTransforms() {
        return texCoordTransforms;
    }

    public void remapSprites(List<Sprite> sprites) {
        for (int i = 0; i < texCoordTransforms.size(); i++) {
            Sprite sprite = sprites.get(i);
            sprite.setTexture(this);
            Matrix3x2f texCoordTransform = new Matrix3x2f();
            texCoordTransforms.get(i).mul(sprite.getTexCoordTransform(), texCoordTransform);
            sprite.setTexCoordTransform(texCoordTransform);
        }
    }

    private static class TextureChunk {
        private final int x;
        private final int y;
        private final int width;
        private final int height;
        private boolean allocated;
        private List<TextureChunk> chunksLeft;

        public TextureChunk(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            allocated = false;
        }

        public TextureChunk allocateSpace(int width, int height) {
            if (!allocated) {
                if (width > this.width || height > this.height) {
                    return null;
                }
                allocated = true;
                chunksLeft = new ArrayList<>();
                if (width < this.width) {
                    chunksLeft.add(new TextureChunk(x + width, y, (this.width - width), height));
                }
                if (height < this.height) {
                    chunksLeft.add(new TextureChunk(x, y + height, this.width, (this.height - height)));
                }
                return new TextureChunk(x, y, width, height);
            }

            for (TextureChunk chunk : chunksLeft) {
                TextureChunk out = chunk.allocateSpace(width, height);
                if (out != null) {
                    return out;
                }
            }

            return null;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        @Override
        public String toString() {
            return "TextureChunk{" +
                    "x=" + x +
                    ", y=" + y +
                    ", width=" + width +
                    ", height=" + height +
                    '}';
        }
    }
}
