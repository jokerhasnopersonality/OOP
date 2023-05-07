package io.github.jokerhasnopersonality.snake.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class representing game field that holds player's snake, food points and barrier blocks.
 */
public class Field {
    private Snake player;
    private final int width;
    private final int height;
    private final int maxFoodCount;
    private boolean[][] foodField;
    private final List<Point> foodPoints;
    private boolean[][] barrierField;
    private final List<Point> barrierPoints;
    private Point lastAdded;
    private Point lastRemoved;

    /**
     * Field controller constructor.

     * @param fieldWidth width of the game field
     * @param fieldHeight height of the game field
     * @param maxFoodCount maximum number of food points that can be
     *                     on the field at the same time
     */
    public Field(int fieldWidth, int fieldHeight,
                 int maxFoodCount)
            throws IllegalArgumentException {
        if (fieldWidth <= 0 || fieldHeight <= 0 || maxFoodCount <= 0) {
            throw new IllegalArgumentException();
        } else if (maxFoodCount > 100) {
            throw new IllegalArgumentException(""
                    + "The maximum number of food points must be no more than 100.");
        }
        this.width = fieldWidth;
        this.height = fieldHeight;
        this.maxFoodCount = maxFoodCount;

        foodPoints = new ArrayList<>();
        barrierPoints = new ArrayList<>();
    }

    /**
     * Clears the field for new game. Adds a snake, specified number
     * of food points and relevant number of barriers to field.
     */
    public void initField() {
        player = new Snake(width / 2, height / 2, Direction.LEFT);
        foodField = new boolean[width][height];
        barrierField = new boolean[width][height];

        foodPoints.clear();
        barrierPoints.clear();
        for (int i = 0; i < maxFoodCount; i++) {
            generateFood();
        }
        int barrierCount = (int) Math.min(maxFoodCount * 1.5,
                width * height - maxFoodCount - player.getSnakeLength() - 2);
        for (int i = 0; i < barrierCount; i++) {
            generateBarrier();
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Snake getPlayer() {
        return player;
    }

    public boolean[][] getFoodField() {
        return foodField;
    }

    public boolean[][] getBarrierField() {
        return barrierField;
    }

    /**
     * Checks if there is a food point at the specified position.
     */
    public boolean checkFoodField(int x, int y)
            throws IllegalArgumentException, IllegalStateException {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IllegalArgumentException();
        }
        if (foodField == null) {
            throw new IllegalStateException();
        }
        return foodField[x][y];
    }

    /**
     * Checks if there is a barrier block at the specified position.
     */
    public boolean checkBarrierField(int x, int y) throws IllegalArgumentException {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IllegalArgumentException();
        }
        return barrierField[x][y];
    }

    public List<Point> getFoodPoints() {
        return foodPoints;
    }

    public List<Point> getBarrierPoints() {
        return barrierPoints;
    }

    public Point getLastAdded() {
        return lastAdded;
    }

    public Point getLastRemoved() {
        return lastRemoved;
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
    public void generateFood() {
        int x;
        int y;
        Random random = new Random();
        do {
            x = (int) (random.nextInt(width - 2)) + 1;
            y = (int) (random.nextInt(height - 2)) + 1;
        } while (checkPointCollision(x, y) || foodField[x][y] || barrierField[x][y]);
        foodField[x][y] = true;
        lastAdded = new Point(x, y);
        foodPoints.add(lastAdded);
    }

    /**
     * Removes food point at the specified position.
     */
    public void removeFood(int x, int y) {
        foodField[x][y] = false;
        for (Point p : foodPoints) {
            if (p.getX() == x && p.getY() == y) {
                lastRemoved = p;
                foodPoints.remove(lastRemoved);
                return;
            }
        }
    }

    /**
     * Adds a sequence of barrier blocks to the field.
     */
    public void generateBarrier() {
        int x;
        int y;
        Random random = new Random();
        List<Point> barrier = new ArrayList<>();
        do {
            x = (int) (random.nextInt(width - 2)) + 1;
            y = (int) (random.nextInt(height - 2)) + 1;
        } while (checkPointCollision(x, y) || foodField[x][y] || barrierField[x][y]);
        int length = random.nextInt(Math.min(10, Math.min(width - x, height - y))) + 1;
        if (random.nextBoolean()) {
            for (int i = 0; (i < length) && !checkPointCollision(x + i, y)
                    && !foodField[x + i][y] && !barrierField[x + i][y]; i++) {
                barrierField[x + i][y] = true;
                barrier.add(new Point(x + i, y));
            }
        } else {
            for (int i = 0; (i < length) && !checkPointCollision(x, y + i)
                    && !foodField[x][y + i] && !barrierField[x][y + i]; i++) {
                barrierField[x][y + i] = true;
                barrier.add(new Point(x, y + i));
            }
        }
        barrierPoints.addAll(barrier);
    }
}
