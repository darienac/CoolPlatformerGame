package render.resources;

import render.Sprite;
import state.GameLevel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileSpriteMapping {
    private Map<GameLevel.Tile, Sprite> tileSpriteMap;

    public TileSpriteMapping() {
        tileSpriteMap = new HashMap<>();
    }

    public Sprite getSprite(GameLevel.Tile tile) {
        return tileSpriteMap.get(tile);
    }

    public void addMapping(GameLevel.Tile tile, Sprite sprite) {
        tileSpriteMap.put(tile, sprite);
    }
}
