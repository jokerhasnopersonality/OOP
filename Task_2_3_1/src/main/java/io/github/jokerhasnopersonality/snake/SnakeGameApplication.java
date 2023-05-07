package io.github.jokerhasnopersonality.snake;

import io.github.jokerhasnopersonality.snake.controller.GameController;
import io.github.jokerhasnopersonality.snake.controller.KeyHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Snake Game Application class for starting an application.
 */
public class SnakeGameApplication extends Application {
    @Override
    public void start(Stage stage) {
        GameController controller = new GameController(20, 20, 35, 6, 5, 500.0f);
        controller.initNewGame();
        Pane pane = controller.getPane();

        Scene scene = new Scene(pane, 950, 700, Color.DARKSEAGREEN);
        stage.setTitle("SNAKE GAME");
        stage.setScene(scene);
        Thread thread = new Thread(controller);
        scene.setOnKeyPressed(new KeyHandler(controller, thread));
        stage.setOnCloseRequest(event -> {
            controller.setStopped(true);
            if (thread.isAlive()) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        stage.show();

        thread.start();
    }

    public static void main(String[] args) {
        launch();
    }
}