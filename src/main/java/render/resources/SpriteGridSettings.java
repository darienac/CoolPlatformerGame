package render.resources;

import org.joml.Matrix4f;
import render.Renderer;
import render.Sprite;
import render.opengl.Texture;
import render.opengl.TextureAtlas;
import state.GameLevel;

import java.util.ArrayList;
import java.util.List;

public class SpriteGridSettings {
    private final TextureAtlas atlas;
    private final TileSpriteMapping tileMapping;
    private final Matrix4f transform;

    public SpriteGridSettings(List<SpriteTemplate> templates, Renderer renderer, Matrix4f transform) {
        this.transform = transform;

        List<Texture> textures = new ArrayList<>();
        List<Sprite> sprites = new ArrayList<>();
        for (SpriteTemplate template : templates) {
            Texture texture = new Texture(template.getTextureName());
            textures.add(texture);
            sprites.add(new Sprite(texture));
        }

        atlas = new TextureAtlas(textures, renderer);
        atlas.remapSprites(sprites);

        for (Texture texture : textures) {
            texture.close();
        }

        tileMapping = new TileSpriteMapping();
        for (int i = 0; i < templates.size(); i++) {
            tileMapping.addMapping(templates.get(i).getTileName(), sprites.get(i));
        }
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public TileSpriteMapping getTileMapping() {
        return tileMapping;
    }

    public Matrix4f getTransform() {
        return transform;
    }

    public static class SpriteTemplate {
        private String textureName;
        private GameLevel.Tile tileName;

        public SpriteTemplate() {
        }

        public SpriteTemplate(String textureName, GameLevel.Tile tileName) {
            this.textureName = textureName;
            this.tileName = tileName;
        }

        public String getTextureName() {
            return textureName;
        }

        public void setTextureName(String textureName) {
            this.textureName = textureName;
        }

        public GameLevel.Tile getTileName() {
            return tileName;
        }

        public void setTileName(GameLevel.Tile tileName) {
            this.tileName = tileName;
        }
    }
}
