package render;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import render.camera.ICamera;
import render.opengl.GlRenderObject;
import render.opengl.GlRenderObjectGroup;
import render.opengl.Texture;

import java.util.ArrayList;
import java.util.List;

public class SpriteGrid {
    private static final float SPRITE_CULL_WIDTH = 1.0f;
    private static final float SPRITE_CULL_HEIGHT = 1.0f;

    private Texture atlas;
    private final int width;
    private final int height;
    private final Matrix4f transform;
    private final Sprite[][] grid;

    public SpriteGrid(Texture atlas, int width, int height, Matrix4f transform) {
        this.atlas = atlas;
        this.width = width;
        this.height = height;
        this.transform = transform;
        this.grid = new Sprite[width][height];
    }

    public void setSprite(int x, int y, Sprite sprite) {
        grid[x][y] = sprite;
    }

    public Sprite getSprite(int x, int y) {
        return grid[x][y];
    }

    public GlRenderObjectGroup createObjectGroup(ICamera view) {
        Matrix4f normalizeMatrix = new Matrix4f();
        transform.invert(normalizeMatrix);
        normalizeMatrix.mul(view.getViewProjectionMatrix().invert());

        Vector4f bottomLeftCorner = new Vector4f(-1.0f, -1.0f, 0.0f, 1.0f);
        normalizeMatrix.transform(bottomLeftCorner);
        Vector4f topRightCorner = new Vector4f(1.0f, 1.0f, 0.0f, 1.0f);
        normalizeMatrix.transform(topRightCorner);

        int x0 = (int) Math.floor(bottomLeftCorner.x - SPRITE_CULL_WIDTH);
        int x1 = (int) Math.floor(topRightCorner.x + SPRITE_CULL_WIDTH) + 1;
        int y0 = (int) Math.floor(bottomLeftCorner.y - SPRITE_CULL_HEIGHT);
        int y1 = (int) Math.floor(topRightCorner.y + SPRITE_CULL_HEIGHT);

        List<GlRenderObject> objects = new ArrayList<>();

        for (int x = x0; x <= x1; x++) {
            if (x < 0) {
                continue;
            }
            if (x >= width) {
                break;
            }
            for (int y = y0; y <= y1; y++) {
                if (y < 0) {
                    continue;
                }
                if (y >= height) {
                    break;
                }
                Sprite sprite = grid[x][y];
                if (sprite == null) {
                    continue;
                }

                Matrix4f objectTransform = new Matrix4f(transform);
                objectTransform.mul(new Matrix4f().translate(x, y, 0.0f));
                objects.add(new GlRenderObject(sprite, objectTransform));
            }
        }

        return new GlRenderObjectGroup(objects, atlas, objects.size());
    }
}
