package io.github.jokerhasnopersonality.snake.model;

/**
 * Point class for game field coordinates.
 */
public class Point {
    private int pointX;
    private int pointY;

    public Point(int pointX, int pointY) {
        this.pointX = pointX;
        this.pointY = pointY;
    }

    public int getX() {
        return pointX;
    }

    public int getY() {
        return pointY;
    }

    public void setX(int pointX) {
        this.pointX = pointX;
    }

    public void setY(int pointY) {
        this.pointY = pointY;
    }
}
