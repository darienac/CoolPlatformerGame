package render;

import org.joml.Matrix3x2f;
import org.joml.Matrix4f;
import render.camera.BasicCamera;
import render.opengl.GlFramebuffer;
import render.opengl.GlRenderObject;
import render.opengl.GlRenderObjectGroup;
import render.opengl.Texture;
import render.shaders.SpritesShader;

import java.util.ArrayList;
import java.util.List;

public class ResourceCache {
    private static ResourceCache instance = null;

    public static ResourceCache getInstance() {
        if (instance == null) {
            try {
                instance = new ResourceCache();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return instance;
    }

    private SpritesShader testShader;
    private Mesh squareMesh;
    private Sprite testSprite;
    private Sprite testSprite2;
    private Sprite testSprite3;
    private Sprite testSprite4;
    private GlRenderObject glRenderObject;
    private GlRenderObject glRenderObject2;
    private GlRenderObjectGroup renderGroup;

    private BasicCamera basicCamera = new BasicCamera();

    private ResourceCache() throws Exception {
        testShader = new SpritesShader("testShader_v.glsl", "testShader_f.glsl");

        createSquareMesh();

        Texture testTexture = new Texture("testAtlas.png");
        testSprite = new Sprite(testTexture, new Matrix3x2f().scale(0.5f).translate(0.0f, 0.0f));
        testSprite2 = new Sprite(testTexture, new Matrix3x2f().scale(0.5f).translate(1.0f, 0.0f));
        testSprite3 = new Sprite(testTexture, new Matrix3x2f().scale(0.5f).translate(0.0f, 1.0f));
        testSprite4 = new Sprite(testTexture, new Matrix3x2f().scale(0.5f).translate(1.0f, 1.0f));

        renderGroup = createLargeRenderGroup(1, 5_000);
    }

    private Sprite chooseRandomSprite() {
        switch ((int) Math.floor(Math.random() * 4.0)) {
            case 0:
                return testSprite;
            case 1:
                return testSprite2;
            case 2:
                return testSprite3;
            case 3:
                return testSprite4;
        }

        return testSprite;
    }

    private GlRenderObjectGroup createLargeRenderGroup(int tileWidth, int numTiles) {
        List<GlRenderObject> glRenderObjects = new ArrayList<>();
        for (int i = 0; i < numTiles; i++) {
            glRenderObjects.add(new GlRenderObject(chooseRandomSprite(), new Matrix4f().translate((float) Math.random() * 2.0f - 1.0f, (float) Math.random() * 2.0f - 1.0f, 0.0f).scale((float) 1.0 / tileWidth)));
        }

        return new GlRenderObjectGroup(glRenderObjects, testSprite.getTexture(), numTiles);
    }

    private void createSquareMesh() {
        squareMesh = new Mesh();
        squareMesh.getVertices().setData(new float[] {
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                -0.5f, 0.5f, 0.0f,
                0.5f, 0.5f, 0.0f
        });
        squareMesh.getTexCoords().setData(new float[] {
                0.0f, 0.0f,
                1.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f
        });
        squareMesh.getFaces().setData(new int[] {
                0, 1, 2,
                2, 1, 3
        });
        squareMesh.setElementCount(12);
    }

    public SpritesShader getTestShader() {
        return testShader;
    }

    public void setTestShader(SpritesShader testShader) {
        this.testShader = testShader;
    }

    public Mesh getSquareMesh() {
        return squareMesh;
    }

    public void setSquareMesh(Mesh squareMesh) {
        this.squareMesh = squareMesh;
    }

    public Sprite getTestSprite() {
        return testSprite;
    }

    public void setTestSprite(Sprite testSprite) {
        this.testSprite = testSprite;
    }

    public GlRenderObjectGroup getRenderGroup() {
        return renderGroup;
    }

    public void setRenderGroup(GlRenderObjectGroup renderGroup) {
        this.renderGroup = renderGroup;
    }

    public BasicCamera getBasicCamera() {
        return basicCamera;
    }
}
