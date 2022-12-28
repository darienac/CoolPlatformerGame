package game;

public class GameEngine implements Runnable {
    public GameEngine(Window window) {

    }

    public interface Window {
        void toggleFullscreen();
    }

    @Override
    public synchronized void run() {

    }
}
