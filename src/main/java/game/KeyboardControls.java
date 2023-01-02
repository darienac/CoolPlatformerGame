package game;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;

public class KeyboardControls implements IControls {
    private boolean cameraMoveLeft = false;
    private boolean cameraMoveRight = false;
    private boolean cameraMoveUp = false;
    private boolean cameraMoveDown = false;

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
                cameraMoveLeft = value;
                break;
            case GLFW_KEY_RIGHT:
                cameraMoveRight = value;
                break;
            case GLFW_KEY_UP:
                cameraMoveUp = value;
                break;
            case GLFW_KEY_DOWN:
                cameraMoveDown = value;
                break;
        }
    }

    @Override
    public boolean isCameraMoveLeft() {
        return cameraMoveLeft;
    }

    @Override
    public boolean isCameraMoveRight() {
        return cameraMoveRight;
    }

    @Override
    public boolean isCameraMoveUp() {
        return cameraMoveUp;
    }

    @Override
    public boolean isCameraMoveDown() {
        return cameraMoveDown;
    }
}
