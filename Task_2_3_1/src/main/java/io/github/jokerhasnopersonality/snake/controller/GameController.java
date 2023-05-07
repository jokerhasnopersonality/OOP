package io.github.jokerhasnopersonality.snake.controller;

import io.github.jokerhasnopersonality.snake.View;
import io.github.jokerhasnopersonality.snake.model.Direction;
import io.github.jokerhasnopersonality.snake.model.Field;
import io.github.jokerhasnopersonality.snake.model.SnakeGame;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * Game controller class for managing the game process and signals from player.
 */
public class GameController implements Runnable {
    private final SnakeGame game;
    private final FieldController fieldController;
    private final AnchorPane pane;
    private final Pane helpContainer;
    private final Pane pauseContainer;
    private final Pane gameOverContainer;
    private final Pane gameWonContainer;
    private final Text scoreText;
    private boolean stopped;
    private boolean busy;
    private Direction nextDirection;
    private final float stepTime;

    /**
     * Game controller constructor. Creates game components and
     * adds them to the main pane.

     * @param width width of the game field
     * @param height height of the game field
     * @param blockSize size of a block in game field
     * @param maxFoodCount maximum number of food points that can be
     *                     on the field at the same time
     * @param goal number of food points that a player needs to
     *             collect in order to win
     */
    public GameController(int width, int height, int blockSize,
                          int maxFoodCount, int goal, float stepTime)
            throws IllegalArgumentException {
        if (width < 0 || height < 0 || maxFoodCount <= 0 || goal <= 0) {
            throw new IllegalArgumentException();
        }
        this.stepTime = stepTime;
        game = new SnakeGame(width, height, maxFoodCount, goal);

        pane = new AnchorPane();
        pane.setMaxWidth(width * blockSize);
        pane.setMaxHeight(height * blockSize);
        pane.setManaged(false);

        View view = new View(width, height, blockSize);

        pauseContainer = view.initPause();
        gameOverContainer = view.initGameOver();
        gameWonContainer = view.initGameWon();
        helpContainer = view.initHelp();
        Pane menuContainer = view.initMenu(goal);
        scoreText = view.getScoreText();
        fieldController = new FieldController(game.getField(), blockSize, view);
        pane.getChildren().addAll(
                fieldController.getFieldContainer(), pauseContainer, menuContainer,
                gameOverContainer, gameWonContainer, helpContainer);
    }

    /**
     * Sets up a new game.
     */
    public void initNewGame() {
        game.restart();
        nextDirection = Direction.LEFT;
        stopped = false;
        busy = false;
        game.restart();
        fieldController.drawField();

        pauseContainer.setVisible(false);
        gameOverContainer.setVisible(false);
        gameWonContainer.setVisible(false);
        helpContainer.setVisible(false);
    }

    /**
     * Processes pause signal.
     */
    public void callPause() {
        if (busy == pauseContainer.isVisible() && !stopped) {
            busy = !busy;
            pauseContainer.setVisible(!pauseContainer.isVisible());
        }
    }

    private void showGameOver() {
        stopped = true;
        gameOverContainer.setVisible(true);
    }

    private void showGameWon() {
        stopped = true;
        gameWonContainer.setVisible(true);
    }

    /**
     * Processes help call.
     */
    public void callHelp() {
        if (busy == helpContainer.isVisible() && !stopped) {
            busy = !busy;
            helpContainer.setVisible(!helpContainer.isVisible());
        }
    }

    public Pane getPane() {
        return pane;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    /**
     * Processes call to change snake direction.
     */
    public void changeSnakeDirection(Direction direction) throws NullPointerException {
        if (direction == null) {
            throw new NullPointerException();
        }
        Field field = game.getField();
        if (!busy) {
            switch (direction) {
                case LEFT: {
                    if (field.getPlayer().getHead().getDirection() != Direction.RIGHT) {
                        nextDirection = direction;
                    }
                    break;
                }
                case RIGHT: {
                    if (field.getPlayer().getHead().getDirection() != Direction.LEFT) {
                        nextDirection = direction;
                    }
                    break;
                }
                case UP: {
                    if (field.getPlayer().getHead().getDirection() != Direction.DOWN) {
                        nextDirection = direction;
                    }
                    break;
                }
                case DOWN: {
                    if (field.getPlayer().getHead().getDirection() != Direction.UP) {
                        nextDirection = direction;
                    }
                    break;
                }
            }
        }
    }

    /**
     * Shifts game to next state if game is in running state.
     */
    public void callNextStep() {
        if (stopped || busy) {
            return;
        }
        // if score has been updated, update food points on the field
        if (game.makeStep(nextDirection)) {
            fieldController.eraseFoodPoint(game.getField().getLastRemoved());
            fieldController.drawFoodPoint(game.getField().getLastAdded());
        }

        // update score view
        scoreText.setText(String.format("%d/%d", game.getScore(), game.getGoal()));

        if (game.isGameOver()) {
            showGameOver();
            return;
        } else if (game.isGameWon()) {
            showGameWon();
        }
        fieldController.updatePlayer();
    }

    @Override
    public void run() {
        float time;
        while (!stopped) {
            time = System.currentTimeMillis();

            Platform.runLater(this::callNextStep);

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
}