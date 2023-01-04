package render.resources;

import org.joml.Matrix3x2f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import render.*;
import render.camera.BasicCamera;
import render.camera.Camera2D;
import render.camera.CroppedFillScreenCamera;
import render.camera.ICamera;
import render.opengl.*;
import render.shaders.SpritesShader;
import state.GameLevel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResourceCache {
    private static final int GAME_WIDTH = 432;
    private static final int GAME_HEIGHT = 240;
    private static final int TILE_RESOLUTION = 16;

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
    private TileSpriteMapping tileMapping;
    private Texture testTexture;
    private Sprite testSprite;
    private Sprite testSprite2;
    private Sprite testSprite3;
    private Sprite testSprite4;
    private SpriteGrid levelTiles;

    private BasicCamera basicCamera = new BasicCamera();

    private GlFramebuffer screenTarget;
    private GlFramebuffer pixelatedBuffer;
    private RenderScene cropScene;
    private RenderScene tileScene;
    private Camera2D tileCamera;
    private GlRenderObjectGroup tileObjectGroup;

    private ResourceCache() throws Exception {
        // Testing
        testShader = new SpritesShader("testShader_v.glsl", "testShader_f.glsl");

        createSquareMesh();

        testTexture = new Texture("testAtlas.png");
        testSprite = new Sprite(testTexture, new Matrix3x2f().scale(0.5f).translate(0.0f, 0.0f));
        testSprite2 = new Sprite(testTexture, new Matrix3x2f().scale(0.5f).translate(1.0f, 0.0f));
        testSprite3 = new Sprite(testTexture, new Matrix3x2f().scale(0.5f).translate(0.0f, 1.0f));
        testSprite4 = new Sprite(testTexture, new Matrix3x2f().scale(0.5f).translate(1.0f, 1.0f));

        tileMapping = new TileSpriteMapping();
        tileMapping.addMapping(GameLevel.Tile.BRICK, testSprite4);
        tileMapping.addMapping(GameLevel.Tile.QBLOCK, testSprite2);
        tileMapping.addMapping(GameLevel.Tile.ROCK, testSprite3);

        levelTiles = null;
    }

    public void setupWindowRendering(IWindow window) {
        // Screen Framebuffer (id: 0)
        screenTarget = new GlScreenBuffer(window);

        // cropScene setup
        Texture pixelatedTexture = new Texture(GAME_WIDTH, GAME_HEIGHT);
        pixelatedBuffer = new GlFramebuffer(Arrays.asList(pixelatedTexture), true);
        Sprite pixelatedSprite = new Sprite(pixelatedTexture);
        GlRenderObjectGroup objectGroup = new GlRenderObjectGroup(Arrays.asList(new GlRenderObject(pixelatedSprite, new Matrix4f())), pixelatedTexture, 1);
        ICamera cropCamera = new CroppedFillScreenCamera(window, (float) GAME_WIDTH / GAME_HEIGHT);
        cropScene = new RenderScene(cropCamera, Arrays.asList(objectGroup), screenTarget);

        // tileScene setup
        tileCamera = new Camera2D(new Vector3f(0.0f, 0.0f, 0.0f), (float) GAME_WIDTH / TILE_RESOLUTION, (float) GAME_HEIGHT / TILE_RESOLUTION);
        tileObjectGroup = null;
        tileScene = new RenderScene(tileCamera, Arrays.asList(), pixelatedBuffer);
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

    public TileSpriteMapping getTileMapping() {
        return tileMapping;
    }

    public Texture getTestTexture() {
        return testTexture;
    }

    public Sprite getTestSprite() {
        return testSprite;
    }

    public void setTestSprite(Sprite testSprite) {
        this.testSprite = testSprite;
    }

    public BasicCamera getBasicCamera() {
        return basicCamera;
    }

    public SpriteGrid getLevelTiles() {
        return levelTiles;
    }

    public void setLevelTiles(SpriteGrid levelTiles) {
        this.levelTiles = levelTiles;
    }

    public GlFramebuffer getScreenTarget() {
        return screenTarget;
    }

    public GlFramebuffer getPixelatedBuffer() {
        return pixelatedBuffer;
    }

    public RenderScene getCropScene() {
        return cropScene;
    }

    public RenderScene getTileScene() {
        return tileScene;
    }

    public Camera2D getTileCamera() {
        return tileCamera;
    }

    public GlRenderObjectGroup getTileObjectGroup() {
        return tileObjectGroup;
    }

    public void setTileObjectGroup(GlRenderObjectGroup tileObjectGroup) {
        this.tileObjectGroup = tileObjectGroup;
    }
}
