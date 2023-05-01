package io.github.jokerhasnopersonality.snake.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.github.jokerhasnopersonality.snake.View;
import io.github.jokerhasnopersonality.snake.model.Direction;
import io.github.jokerhasnopersonality.snake.model.Snake;
import io.github.jokerhasnopersonality.snake.model.SnakeBody;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Class representing game field controller. Holds general game logic.
 */
public class FieldController {
    private final int width;
    private final int height;
    private final int blockSize;
    private final View view;
    private final Pane field;
    private Snake player;
    private List<Node> playerNode;
    private boolean[][] foodField;
    private boolean[][] barrierField;
    private final int foodCount;
    private int score;
    private final int goal;
    private boolean gameOver;
    private boolean gameWon;

    /**
     * Field controller constructor.

     * @param fieldWidth width of the game field
     * @param fieldHeight height of the game field
     * @param blockSize size of a block in game field
     * @param maxFoodCount maximum number of food points that can be
     *                     on the field at the same time
     * @param goal number of food points that a player needs to
     *             collect in order to win
     * @param view View instance to draw elements on the field
     */
    public FieldController(int fieldWidth, int fieldHeight, int blockSize,
                           int maxFoodCount, int goal, View view) throws IllegalArgumentException {
        if (fieldWidth <= 0 || fieldHeight <= 0 || blockSize <= 0
                || maxFoodCount <= 0 || goal <= 0 || view == null) {
            throw new IllegalArgumentException();
        }
        field = new AnchorPane();
        this.width = fieldWidth;
        this.height = fieldHeight;
        field.setMaxWidth(width * blockSize);
        field.setMaxHeight(height * blockSize);
        field.setManaged(false);
        this.foodCount = maxFoodCount;
        this.blockSize = blockSize;
        this.view = view;
        this.goal = goal;
    }

    /**
     * Clears the field for new game. Adds a snake, specified number
     * of food points and relevant number of barriers to field.
     */
    public void initField() {
        gameOver = false;
        gameWon = false;

        field.getChildren().clear();

        player = new Snake(10, 10, Direction.LEFT);
        score = 0;

        foodField = new boolean[width][height];
        barrierField = new boolean[width][height];

        for (int i = 0; i < foodCount; i++) {
            field.getChildren().add(generateFood());
        }
        int barrierCount = (int) (foodCount * 1.5);
        for (int i = 0; i < barrierCount; i++) {
            field.getChildren().addAll(generateBarrier());
        }

        playerNode = drawSnake(player);
        field.getChildren().addAll(playerNode);
    }

    public Pane getField() {
        return field;
    }

    public Snake getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }

    /**
     * Method for checking if it is safe to add a food point or a barrier
     * at the specified position of the field.
     */
    public boolean checkPointCollision(int pointX, int pointY) {
        SnakeBody safePosition1 = player.getNextBlock(player.getHead());
        SnakeBody safePosition2 = player.getNextBlock(safePosition1);
        return (safePosition1.getPoint().getX() == pointX
                && safePosition1.getPoint().getY() == pointY)
                || (safePosition2.getPoint().getX() == pointX
                && safePosition2.getPoint().getY() == pointY)
                || player.getSnake().stream().anyMatch(n ->
                n.getPoint().getX() == pointX && n.getPoint().getY() == pointY);
    }

    /**
     * Method for checking if snake has eaten itself.
     */
    public boolean checkSnakeCollision() {
        SnakeBody head = player.getHead();
        return player.getSnake().subList(1, player.getSnakeLength()).stream().anyMatch(n ->
                n.getPoint().getX() == head.getPoint().getX()
                        && n.getPoint().getY() == head.getPoint().getY());
    }

    /**
     * Adds a single food point to the field.
     */
    public Node generateFood() {
        int x;
        int y;
        do {
            x = (int) (Math.random() * (width - 2)) + 1;
            y = (int) (Math.random() * (height - 2)) + 1;
        } while (checkPointCollision(x, y) || foodField[x][y] || barrierField[x][y]);
        foodField[x][y] = true;
        return view.drawCircle(Color.GOLD, x, y);
    }

    /**
     * Adds a sequence of barrier blocks to the field.
     */
    public List<Node> generateBarrier() {
        int x;
        int y;
        Random random = new Random();
        do {
            x = (int) (Math.random() * (width - 2)) + 1;
            y = (int) (Math.random() * (height - 2)) + 1;
        } while (checkPointCollision(x, y) || foodField[x][y] || barrierField[x][y]);
        int length = random.nextInt(Math.min(10, Math.min(width - x, height - y))) + 1;
        List<Node> barrier = new ArrayList<>();
        if (random.nextBoolean()) {
            for (int i = 0; (i < length) && !checkPointCollision(x + i, y)
                    && !foodField[x + i][y] && !barrierField[x + i][y]; i++) {
                barrier.add(view.drawBlock(x + i, y, Color.ROSYBROWN));
                barrierField[x + i][y] = true;
            }
        } else {
            for (int i = 0; (i < length) && !checkPointCollision(x, y + i)
                    && !foodField[x][y + i] && !barrierField[x][y + i]; i++) {
                barrier.add(view.drawBlock(x, y + i, Color.ROSYBROWN));
                barrierField[x][y + i] = true;
            }
        }
        return barrier;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    /**
     * Method for drawing snake on the field using view.
     */
    public List<Node> drawSnake(Snake snake) {
        List<Node> nodes = new ArrayList<>();
        SnakeBody block = snake.getHead();
        nodes.add(view.drawSnakeHead(block.getPoint().getX(),
                block.getPoint().getY(), block.getDirection()));
        Direction previousDirection = block.getDirection();
        for (int i = 1; i < snake.getSnakeLength() - 1; i++) {
            block = snake.getSnake().get(i);
            if (block.getDirection() == previousDirection) {
                nodes.add(view.drawSnakeBody(block.getPoint().getX(),
                        block.getPoint().getY(), block.getDirection()));
            } else {
                nodes.add(view.drawSnakeTurn(block.getPoint().getX(),
                        block.getPoint().getY(), block.getDirection()));
            }
            previousDirection = block.getDirection();
        }
        block = snake.getTail();
        nodes.add(view.drawSnakeTail(block.getPoint().getX(),
                block.getPoint().getY(), block.getDirection()));
        return nodes;
    }

    /**
     * Method for controller to shift the field to the next state.

     * @param nextDirection direction that a snake should hold before shifting to next state
     */
    public void goToNextState(Direction nextDirection) {
        player.changeDirection(nextDirection);
        player.grow();

        //check if snake has moved crossed the borders
        int headX = player.getHead().getPoint().getX();
        int headY = player.getHead().getPoint().getY();
        if (headX >= width) {
            headX = 0;
            player.getHead().getPoint().setX(headX);
        } else if (headX < 0) {
            headX = width - 1;
            player.getHead().getPoint().setX(headX);
        }
        if (headY >= height) {
            headY = 0;
            player.getHead().getPoint().setY(headY);
        } else if (headY < 0) {
            headY = height - 1;
            player.getHead().getPoint().setY(headY);
        }

        // check if snake has eaten itself
        if (checkSnakeCollision() || barrierField[headX][headY]) {
            gameOver = true;
            return;
        }

        int finalHeadX;
        int finalHeadY;
        // check if food point has been eaten
        if (foodField[headX][headY]) {
            finalHeadX = headX;
            finalHeadY = headY;
                field.getChildren().removeIf(n -> (int) (n.getLayoutX() / blockSize) == finalHeadX
                        && (int) (n.getLayoutY() / blockSize) == finalHeadY);
                field.getChildren().add(generateFood());
            foodField[headX][headY] = false;

            score++;
            if (score == goal) {
                gameWon = true;
            }
        } else {
            player.getSnake().remove(player.getTail());
        }
    }

    /**
     * Updates player's snake on the field.
     */
    public void updatePlayer() {
        field.getChildren().removeAll(playerNode);
        playerNode = drawSnake(player);
        field.getChildren().addAll(playerNode);
    }
}
