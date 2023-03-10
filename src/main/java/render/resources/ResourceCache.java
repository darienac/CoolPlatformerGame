package render.resources;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import render.*;
import render.camera.*;
import render.opengl.*;
import render.shaders.SpritesShader;
import state.GameLevel;

import java.util.Arrays;

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

    private SpritesShader levelShader;
    private Mesh squareMesh;
    private SpriteGrid levelTiles;
    private SpriteGridSettings levelRenderSettings;

    private BasicCamera basicCamera = new BasicCamera();

    private GlFramebuffer screenTarget;
    private GlFramebuffer pixelatedBuffer;
    private RenderScene cropScene;
    private RenderScene tileScene;
    private Camera2D tileCamera;
    private GlRenderObjectGroup tileObjectGroup;

    private final GlRenderObjectSingleGroup levelEditorSelector;

    private ResourceCache() throws Exception {
        createSquareMesh();

        levelTiles = null;

        levelEditorSelector = new GlRenderObjectSingleGroup("selector.png");
    }

    public void setupWindowRendering(Renderer renderer) throws Exception {
        levelShader = new SpritesShader("levelShader_v.glsl", "levelShader_f.glsl");

        IWindow window = renderer.getWindow();

        // Screen Framebuffer (id: 0)
        screenTarget = new GlScreenBuffer(window);

        // cropScene setup
        Texture pixelatedTexture = new Texture(GAME_WIDTH, GAME_HEIGHT);
        pixelatedBuffer = new GlFramebuffer(Arrays.asList(pixelatedTexture), true);
        Sprite pixelatedSprite = new Sprite(pixelatedTexture);
        GlRenderObjectGroup objectGroup = new GlRenderObjectGroup(Arrays.asList(new GlRenderObject(pixelatedSprite, new Matrix4f())), pixelatedTexture, 1);
        // ICamera cropCamera = new CroppedFillScreenCamera(window, (float) GAME_WIDTH / GAME_HEIGHT);
        ICamera cropCamera = new CroppedIntegerScaleCamera(window, GAME_WIDTH, GAME_HEIGHT);
        cropScene = new RenderScene(cropCamera, Arrays.asList(objectGroup), screenTarget);

        // tileScene setup
        tileCamera = new Camera2D(new Vector3f(0.0f, 0.0f, 0.0f), (float) GAME_WIDTH / TILE_RESOLUTION, (float) GAME_HEIGHT / TILE_RESOLUTION);
        tileObjectGroup = null;
        tileScene = new RenderScene(tileCamera, Arrays.asList(), pixelatedBuffer);

        levelRenderSettings = new SpriteGridSettings(Arrays.asList(
                new SpriteGridSettings.SpriteTemplate("hitBlock.png", GameLevel.Tile.HIT_BLOCK),
                new SpriteGridSettings.SpriteTemplate("qblock.png", GameLevel.Tile.QBLOCK),
                new SpriteGridSettings.SpriteTemplate("rock.png", GameLevel.Tile.ROCK),
                new SpriteGridSettings.SpriteTemplate("rockgrass.png", GameLevel.Tile.ROCK_GRASS),
                new SpriteGridSettings.SpriteTemplate("testTile.png", GameLevel.Tile.DIRT_ROCK)
        ), renderer, new Matrix4f());
    }

    private void createSquareMesh() {
        squareMesh = new Mesh();
        float halfPixel = 1.0f / 32.0f;
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

    public SpritesShader getLevelShader() {
        return levelShader;
    }

    public Mesh getSquareMesh() {
        return squareMesh;
    }

    public SpriteGrid getLevelTiles() {
        return levelTiles;
    }

    public void setLevelTiles(SpriteGrid levelTiles) {
        this.levelTiles = levelTiles;
    }

    public SpriteGridSettings getLevelRenderSettings() {
        return levelRenderSettings;
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

    public GlRenderObjectSingleGroup getLevelEditorSelector() {
        return levelEditorSelector;
    }
}
