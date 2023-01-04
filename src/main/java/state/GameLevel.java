package state;

public class GameLevel {
    public enum Tile {
        BRICK,
        QBLOCK,
        ROCK
    }

    private Tile[][] tiles;
    private int width;
    private int height;

    public GameLevel() {
    }

    public GameLevel(Tile[][] tiles, int width, int height) {
        this.tiles = tiles;
        this.width = width;
        this.height = height;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
