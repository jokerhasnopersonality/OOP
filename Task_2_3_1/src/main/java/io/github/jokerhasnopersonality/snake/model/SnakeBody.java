package io.github.jokerhasnopersonality.snake.model;

/**
 * Class representing a single snake body block
 * with coordinates and direction.
 */
public class SnakeBody {
    private final Point point;
    private Direction direction;

    /**
     * Snake body constructor. Creates a snake body block
     * at the specified position with the specified direction.
     */
    public SnakeBody(int x, int y, Direction direction) throws NullPointerException {
        if (direction == null) {
            throw new NullPointerException("Direction must be specified.");
        }
        point = new Point(x, y);
        this.direction = direction;
    }

    public Point getPoint() {
        return point;
    }

    public Direction getDirection() {
        return direction;
    }

    /**
     * Method to change direction of a single snake body block.
     */
    public void setDirection(Direction direction) throws NullPointerException {
        if (direction == null) {
            throw new NullPointerException("Direction must be specified.");
        }
        this.direction = direction;
    }
}
