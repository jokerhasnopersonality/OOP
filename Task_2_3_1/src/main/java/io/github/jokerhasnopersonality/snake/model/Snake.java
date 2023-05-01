package io.github.jokerhasnopersonality.snake.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing snake for snake game.
 */
public class Snake {
    private List<SnakeBody> snake;

    /**
     * Snake constructor. Creates a snake composed of a head, body and a tail.

     * @param x coordinate x of a snake head
     * @param y coordinate y of a snake head
     * @param direction initial direction of a snake head
     */
    public Snake(int x, int y, Direction direction) {
        snake = new ArrayList<>();
        snake.add(new SnakeBody(x, y, direction));
        grow();
        grow();
    }

    public int getSnakeLength() {
        return snake.size();
    }

    public SnakeBody getHead() {
        return snake.get(0);
    }

    public SnakeBody getTail() {
        return snake.get(snake.size() - 1);
    }

    public List<SnakeBody> getSnake() {
        return snake;
    }

    public void changeDirection(Direction direction) {
        getHead().setDirection(direction);
    }

    /**
     * Predicts the next state of a snake block.
     */
    public SnakeBody getNextBlock(SnakeBody block) {
        int x = block.getPoint().getX();
        int y = block.getPoint().getY();
        SnakeBody nextHead = getHead();
        switch (block.getDirection()) {
            case LEFT: {
                nextHead = new SnakeBody(x - 1, y, Direction.LEFT);
                break;
            }
            case RIGHT: {
                nextHead = new SnakeBody(x + 1, y, Direction.RIGHT);
                break;
            }
            case UP: {
                nextHead = new SnakeBody(x, y - 1, Direction.UP);
                break;
            }
            case DOWN: {
                nextHead = new SnakeBody(x, y + 1, Direction.DOWN);
                break;
            }
        }
        return nextHead;
    }

    /**
     * Adds a new body block (head) to the snake.
     */
    public void grow() {
        snake.add(0, getNextBlock(getHead()));
    }
}
