package game;

import org.joml.Vector2f;
import org.joml.Vector2i;

public interface IControls {
    Vector2f getCameraMove();
    Vector2i getEditorSelectorMove();
    void resetEditorSelectorMove();
    int getEditorSelectedTileMove();
    void resetEditorSelectedTileMove();
}
