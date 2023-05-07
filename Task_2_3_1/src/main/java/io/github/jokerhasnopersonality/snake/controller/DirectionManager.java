package io.github.jokerhasnopersonality.snake.controller;

import io.github.jokerhasnopersonality.snake.model.Direction;
import io.github.jokerhasnopersonality.snake.model.Turn;

/**
 * Class for managing snake movement and calculating rotation angles for view.
 */
public class DirectionManager {
    /**
     * Method for calculating rotation angle according to specified direction of a snake.
     */
    public static int calculateRotateAngle(Direction direction) {
        int rotate = 0;
        switch (direction) {
            case LEFT: {
                rotate = 90;
                break;
            }
            case RIGHT: {
                rotate = -90;
                break;
            }
            case UP: {
                rotate = 180;
                break;
            }
        }
        return rotate;
    }

    /**
     * Method for calculating rotation angle according to specified turn of a snake.
     */
    public static int calculateRotateAngle(Turn turn) {
        int rotate = 0;
        switch (turn) {
            case TOP_RIGHT: {
                rotate = -90;
                break;
            }
            case TOP_LEFT: {
                rotate = 180;
                break;
            }
            case BOTTOM_LEFT: {
                rotate = 90;
                break;
            }
        }
        return rotate;
    }

    /**
     * Translates two directions to a turn.
     */
    public static Turn getTurnDirection(Direction start, Direction end) {
        Turn result = null;
        switch (start) {
            case LEFT: {
                if (end == Direction.UP) {
                    result = Turn.TOP_RIGHT;
                } else if (end == Direction.DOWN) {
                    result = Turn.BOTTOM_RIGHT;
                }
                break;
            }
            case RIGHT: {
                if (end == Direction.UP) {
                    result = Turn.TOP_LEFT;
                } else if (end == Direction.DOWN) {
                    result = Turn.BOTTOM_LEFT;
                }
                break;
            }
            case UP: {
                if (end == Direction.LEFT) {
                    result = Turn.BOTTOM_LEFT;
                } else if (end == Direction.RIGHT) {
                    result = Turn.BOTTOM_RIGHT;
                }
                break;
            }
            case DOWN: {
                if (end == Direction.LEFT) {
                    result = Turn.TOP_LEFT;
                } else if (end == Direction.RIGHT) {
                    result = Turn.TOP_RIGHT;
                }
                break;
            }
            default: {
                throw new IllegalArgumentException("Can't calculate direction to turn.");
            }
        }
        return result;
    }
}
