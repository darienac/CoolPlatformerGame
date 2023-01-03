package game.resources;

import com.google.gson.Gson;
import state.GameLevel;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class LevelLoader {
    private static final String LEVEL_PATH = "levels/";

    private Gson gson = new Gson();

    public GameLevel loadTestLevel() {
        return loadLevel("testLevel.level");
    }

    private GameLevel loadLevel(String name) {
        try {
            return gson.fromJson(new FileReader(LEVEL_PATH + name), GameLevel.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
