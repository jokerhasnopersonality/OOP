package io.github.jokerhasnopersonality.snake.controller;

import io.github.jokerhasnopersonality.snake.controller.GameController;
import javafx.application.Platform;

/**
 * Game thread class to handle a new game start.
 */
public class GameThread implements Runnable {
    private final float stepTime;
    private final GameController controller;

    public GameThread(GameController controller) {
        stepTime = 600.0f;
        this.controller = controller;
    }

    @Override
    public void run() {
        float time;
        while (!controller.isStopped()) {
            time = System.currentTimeMillis();

            Platform.runLater(controller::gameStep);

            time = System.currentTimeMillis() - time;
            if (time < stepTime) {
                try {
                    Thread.sleep((long) (stepTime - time));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public GameController getController() {
        return controller;
    }
}
