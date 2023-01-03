package game.state;

public class GameLevel {
    public enum Tile {
        BRICK,
        QBLOCK,
        ROCK
    }

    private Tile[][] tiles;
    public GameLevel(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public GameLevel(int width, int height) {
        tiles = new Tile[width][height];
    }
}
