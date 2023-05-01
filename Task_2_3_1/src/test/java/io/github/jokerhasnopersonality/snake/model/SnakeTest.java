package io.github.jokerhasnopersonality.snake.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SnakeTest {
    @Test
    public void testIllegalArguments() {
        Assertions.assertThrows(NullPointerException.class, () -> new Snake(0, 2, null));
        Assertions.assertThrows(NullPointerException.class, () -> {
            new Snake(0, 0, Direction.LEFT).changeDirection(null);
        });
    }

    @Test
    public void testModel() {
        Snake snake = new Snake(10, 0, Direction.LEFT);
        SnakeBody next = snake.getNextBlock(snake.getHead());
        Assertions.assertEquals(7, next.getPoint().getX());
        Assertions.assertEquals(0, next.getPoint().getY());
        Assertions.assertEquals(Direction.LEFT, next.getDirection());
        snake.changeDirection(Direction.DOWN);
        for (int i = 0; i < 9; i++) {
            snake.grow();
        }
        Assertions.assertEquals(11, snake.getSnakeLength());
        Assertions.assertEquals(10, snake.getHead().getPoint().getY());
        Assertions.assertEquals(0, snake.getTail().getPoint().getY());
    }
}