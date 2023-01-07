package game;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;

public class KeyboardControls implements IControls {
    private final Vector2f cameraMove = new Vector2f();
    private final Vector2i editorSelectorMove = new Vector2i();

    private Input4Dir cameraMoveInput = new Input4Dir();
    private Input4Dir editorSelectorMoveInput = new Input4Dir();

    public KeyboardControls(IWindow window) {
        GLFW.glfwSetKeyCallback(window.getId(), new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                getKeyInput(key, action);
            }
        });
    }

    public void getKeyInput(int key, int action) {
        boolean value = (action == GLFW_PRESS);
        if (action == GLFW_REPEAT) {
            return;
        }
        switch (key) {
            case GLFW_KEY_LEFT:
                cameraMoveInput.left(value);
                break;
            case GLFW_KEY_RIGHT:
                cameraMoveInput.right(value);
                break;
            case GLFW_KEY_UP:
                cameraMoveInput.up(value);
                break;
            case GLFW_KEY_DOWN:
                cameraMoveInput.down(value);
                break;
            case GLFW_KEY_A:
                editorSelectorMoveInput.left(value);
                break;
            case GLFW_KEY_D:
                editorSelectorMoveInput.right(value);
                break;
            case GLFW_KEY_W:
                editorSelectorMoveInput.up(value);
                break;
            case GLFW_KEY_S:
                editorSelectorMoveInput.down(value);
                break;
        }

        cameraMove.zero();
        if (cameraMoveInput.left()) {
            cameraMove.x -= 1.0f;
        }
        if (cameraMoveInput.right()) {
            cameraMove.x += 1.0f;
        }
        if (cameraMoveInput.up()) {
            cameraMove.y += 1.0f;
        }
        if (cameraMoveInput.down()) {
            cameraMove.y -= 1.0f;
        }
        if (cameraMove.length() > 1.0f) {
            cameraMove.normalize();
        }

        editorSelectorMove.zero();
        if (editorSelectorMoveInput.left()) {
            editorSelectorMove.x -= 1;
        }
        if (editorSelectorMoveInput.right()) {
            editorSelectorMove.x += 1;
        }
        if (editorSelectorMoveInput.up()) {
            editorSelectorMove.y += 1;
        }
        if (editorSelectorMoveInput.down()) {
            editorSelectorMove.y -= 1;
        }
    }

    @Override
    public Vector2f getCameraMove() {
        return cameraMove;
    }

    @Override
    public Vector2i getEditorSelectorMove() {
        return editorSelectorMove;
    }

    @Override
    public void resetEditorSelectorMove() {
        editorSelectorMove.zero();
        editorSelectorMoveInput.reset();
    }

    private static class Input4Dir {
        private boolean left = false;
        private boolean right = false;
        private boolean up = false;
        private boolean down = false;

        public boolean left() {
            return left;
        }

        public void left(boolean in) {
            left = in;
        }

        public boolean right() {
            return right;
        }

        public void right(boolean in) {
            right = in;
        }

        public boolean up() {
            return up;
        }

        public void up(boolean in) {
            up = in;
        }

        public boolean down() {
            return down;
        }

        public void down(boolean in) {
            down = in;
        }

        public void reset() {
            up = false;
            down = false;
            left = false;
            right = false;
        }

        public boolean isReset() {
            return !(up && down && left && right);
        }
    }
}
