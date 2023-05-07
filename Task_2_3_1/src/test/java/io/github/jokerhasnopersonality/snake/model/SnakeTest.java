package io.github.jokerhasnopersonality.snake.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SnakeTest {
    @Test
    public void testBadArguments() {
        Assertions.assertThrows(NullPointerException.class,
                () -> new Snake(0, 2, null));
        Assertions.assertThrows(NullPointerException.class, () -> {
            new Snake(0, 0, Direction.LEFT).changeDirection(null);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Field(0, 0, 3);
        });
    }

    @Test
    public void testSnakeModel() {
        Snake snake = new Snake(10, 0, Direction.LEFT);
        SnakeBody next = snake.getNextBlock(snake.getHead());
        Assertions.assertEquals(7, next.getPoint().getX());
        Assertions.assertEquals(0, next.getPoint().getY());
        Assertions.assertEquals(Direction.LEFT, next.getDirection());
        snake.changeDirection(Direction.DOWN);
        for (int i = 0; i < 9; i++) {
            snake.grow();
        }
        Assertions.assertEquals(12, snake.getSnakeLength());
        Assertions.assertEquals(9, snake.getHead().getPoint().getY());
        Assertions.assertEquals(0, snake.getTail().getPoint().getY());
    }

    @Test
    public void testGameModel() {
        SnakeGame game = new SnakeGame(20, 20, 2, 1);
        game.restart();
        Assertions.assertFalse(game.isGameWon());
        Assertions.assertFalse(game.isGameOver());

        Point point = game.getField().getLastAdded();
        Assertions.assertTrue(game.getField().checkFoodField(point.getX(), point.getY()));
        game.getField().removeFood(point.getX(), point.getY());
        point = game.getField().getLastAdded();
        Assertions.assertFalse(game.getField().checkFoodField(point.getX(), point.getY()));

        game.getField().getFoodField()[7][10] = true;
        game.makeStep(Direction.LEFT);
        Assertions.assertTrue(game.isGameWon());
        Assertions.assertEquals(1, game.getScore());

        game.restart();
        game.getField().getBarrierField()[7][10] = true;
        game.getField().checkBarrierField(7, 10);
        game.makeStep(Direction.LEFT);
        Assertions.assertTrue(game.isGameOver());
        Assertions.assertEquals(0, game.getScore());
    }
}