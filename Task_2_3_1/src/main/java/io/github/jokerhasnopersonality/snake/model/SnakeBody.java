package io.github.jokerhasnopersonality.snake.model;

import io.github.jokerhasnopersonality.snake.model.Direction;
import io.github.jokerhasnopersonality.snake.model.Point;

/**
 * Class representing a single snake body block
 * with coordinates and direction.
 */
public class SnakeBody {
    private Point point;
    private Direction direction;

    public SnakeBody(int x, int y, Direction direction) {
        point = new Point(x, y);
        this.direction = direction;
    }

    public Point getPoint() {
        return point;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
