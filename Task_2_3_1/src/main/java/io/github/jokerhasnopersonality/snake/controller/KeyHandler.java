package io.github.jokerhasnopersonality.snake.controller;

import io.github.jokerhasnopersonality.snake.model.Direction;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 * Handler class for processing keyboard signals.
 */
public class KeyHandler implements EventHandler<KeyEvent> {
    private final GameThread gameThread;
    private final GameController controller;
    private Thread thread;

    /**
     * Key handler constructor.

     * @param gameThread Game Thread instance
     * @param thread current thread
     */
    public KeyHandler(GameThread gameThread, Thread thread) throws NullPointerException {
        if (gameThread == null || thread == null) {
            throw new NullPointerException();
        }
        this.gameThread = gameThread;
        this.controller = gameThread.getController();
        this.thread = thread;
    }

    /**
     * Invoked when a specific event of the type for which this handler is
     * registered happens.
     *
     * @param event the event which occurred
     */
    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
            case A: {
                controller.changeSnakeDirection(Direction.LEFT);
                break;
            }
            case RIGHT:
            case D: {
                controller.changeSnakeDirection(Direction.RIGHT);
                break;
            }
            case UP:
            case W: {
                controller.changeSnakeDirection(Direction.UP);
                break;
            }
            case DOWN:
            case S: {
                controller.changeSnakeDirection(Direction.DOWN);
                break;
            }
            case ESCAPE: {
                controller.callPause();
                break;
            }
            case ALT: {
                controller.callHelp();
                break;
            }
            case ENTER: {
                controller.setStopped(true);
                if (thread.isAlive()) {
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                controller.initNewGame();
                thread = new Thread(gameThread);
                thread.start();
                break;
            }
        }
    }
}
