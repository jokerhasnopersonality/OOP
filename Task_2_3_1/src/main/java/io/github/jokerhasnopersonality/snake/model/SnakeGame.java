package io.github.jokerhasnopersonality.snake.model;

/**
 * Class representing snake game. Holds general game logic.
 */
public class SnakeGame {
    private final int width;
    private final int height;
    private final int goal;
    private final Field field;
    private int score;
    private boolean gameOver;
    private boolean gameWon;

    /**
     * Field controller constructor.

     * @param fieldWidth width of the game field
     * @param fieldHeight height of the game field
     * @param maxFoodCount maximum number of food points that can be
     *                     on the field at the same time
     * @param goal number of food points that a player needs to
     *             collect in order to win
     */
    public SnakeGame(int fieldWidth, int fieldHeight,
                     int maxFoodCount, int goal)
            throws IllegalArgumentException, NullPointerException {
        if (goal <= 0) {
            throw new IllegalArgumentException();
        }
        this.width = fieldWidth;
        this.height = fieldHeight;
        this.goal = goal;
        field = new Field(fieldWidth, fieldHeight, maxFoodCount);
    }

    /**
     * Clears the field for new game. Adds a snake, specified number
     * of food points and relevant number of barriers to field.
     */
    public void restart() {
        gameOver = false;
        gameWon = false;

        score = 0;
        field.initField();
    }

    public Field getField() {
        return field;
    }

    public int getScore() {
        return score;
    }

    public int getGoal() {
        return goal;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    /**
     * Method for controller to shift the game to the next state.

     * @param nextDirection direction that a snake should hold before shifting to next state
     */
    public boolean makeStep(Direction nextDirection)
            throws IllegalArgumentException {
        if (gameOver || gameWon) {
            return false;
        }
        if (nextDirection == null) {
            throw new IllegalArgumentException();
        }
        boolean scoreUpdated = false;
        Snake player = field.getPlayer();
        player.changeDirection(nextDirection);
        player.grow();

        //check if snake has crossed the borders
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
        if (field.checkSnakeCollision() || field.checkBarrierField(headX, headY)) {
            gameOver = true;
            return false;
        }

        // check if food point has been eaten
        if (field.checkFoodField(headX, headY)) {
            field.removeFood(headX, headY);
            field.generateFood();

            score++;
            scoreUpdated = true;
            if (score == goal) {
                gameWon = true;
            }
        } else {
            player.getSnake().remove(player.getTail());
        }
        return scoreUpdated;
    }
}
