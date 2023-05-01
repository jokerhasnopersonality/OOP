package io.github.jokerhasnopersonality.snake.controller;

import io.github.jokerhasnopersonality.snake.View;
import io.github.jokerhasnopersonality.snake.model.Direction;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * Game controller class for managing the game process and signals from player.
 */
public class GameController {
    private final AnchorPane pane;
    private final Pane helpContainer;
    private final Pane pauseContainer;
    private final Pane gameOverContainer;
    private final Pane gameWonContainer;
    private final FieldController fieldController;
    private final Text scoreText;
    private boolean stopped;
    private boolean busy;
    private final int goal;
    private Direction nextDirection;

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
    public GameController(int width, int height, int blockSize, int maxFoodCount, int goal)
            throws IllegalArgumentException {
        if (width < 0 || height < 0 || maxFoodCount <= 0 || goal <= 0) {
            throw new IllegalArgumentException();
        }
        pane = new AnchorPane();
        pane.setMaxWidth(width * blockSize);
        pane.setMaxHeight(height * blockSize);
        this.goal = goal;
        View view = new View(width, height, blockSize);
        this.pane.setManaged(false);

        fieldController = new FieldController(width, height, blockSize, maxFoodCount, goal, view);

        pauseContainer = view.initPause();
        gameOverContainer = view.initGameOver();
        gameWonContainer = view.initGameWon();
        helpContainer = view.initHelp();
        Pane menuContainer = view.initMenu(goal);
        scoreText = view.getScoreText();
        pane.getChildren().addAll(fieldController.getField(), pauseContainer, menuContainer,
                gameOverContainer, gameWonContainer, helpContainer);
        initNewGame();
    }

    /**
     * Sets up a new game.
     */
    public void initNewGame() {
        nextDirection = Direction.LEFT;
        stopped = false;
        busy = false;
        fieldController.initField();

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

    public boolean isStopped() {
        return stopped;
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
        if (!busy) {
            switch (direction) {
                case LEFT: {
                    if (fieldController.getPlayer().getHead().getDirection() != Direction.RIGHT) {
                        nextDirection = direction;
                    }
                    break;
                }
                case RIGHT: {
                    if (fieldController.getPlayer().getHead().getDirection() != Direction.LEFT) {
                        nextDirection = direction;
                    }
                    break;
                }
                case UP: {
                    if (fieldController.getPlayer().getHead().getDirection() != Direction.DOWN) {
                        nextDirection = direction;
                    }
                    break;
                }
                case DOWN: {
                    if (fieldController.getPlayer().getHead().getDirection() != Direction.UP) {
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
    public void gameStep() {
        if (stopped || busy) {
            return;
        }
        fieldController.goToNextState(nextDirection);
        fieldController.updatePlayer();

        // update view
        scoreText.setText(String.format("%d/%d", fieldController.getScore(), goal));

        if (fieldController.isGameOver()) {
            showGameOver();
        } else if (fieldController.isGameWon()) {
            showGameWon();
        }
    }
}