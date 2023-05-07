package io.github.jokerhasnopersonality.snake.controller;

import io.github.jokerhasnopersonality.snake.View;
import io.github.jokerhasnopersonality.snake.model.Field;
import io.github.jokerhasnopersonality.snake.model.Point;
import io.github.jokerhasnopersonality.snake.model.Snake;
import io.github.jokerhasnopersonality.snake.model.SnakeBody;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Class representing game field controller. Controls game field view.
 */
public class FieldController {
    private final View view;
    private final Field field;
    private final Pane fieldContainer;
    private List<Node> playerNode;

    /**
     * Field controller constructor.

     * @param blockSize size of a block in game field
     * @param view View instance to draw elements on the field
     */
    public FieldController(Field field, int blockSize, View view)
            throws IllegalArgumentException, NullPointerException {
        if (blockSize <= 0) {
            throw new IllegalArgumentException();
        } else if (view == null) {
            throw new NullPointerException();
        }
        this.field = field;
        fieldContainer = new AnchorPane();
        fieldContainer.setMaxWidth(field.getWidth() * blockSize);
        fieldContainer.setMaxHeight(field.getHeight() * blockSize);
        fieldContainer.setManaged(false);
        this.view = view;
    }

    /**
     * Clears the field for new game. Adds a snake, specified number
     * of food points and relevant number of barriers to field.
     */
    public void drawField() {
        fieldContainer.getChildren().clear();

        fieldContainer.getChildren().addAll(field.getFoodPoints().stream()
                .map(point -> view.drawCircle(point.getX(), point.getY(), Color.GOLD))
                .collect(Collectors.toList()));
        fieldContainer.getChildren().addAll(field.getBarrierPoints().stream()
                .map(point -> view.drawBlock(point.getX(), point.getY(), Color.ROSYBROWN))
                .collect(Collectors.toList()));

        playerNode = drawSnake(field.getPlayer());
        fieldContainer.getChildren().addAll(playerNode);
    }

    public Pane getFieldContainer() {
        return fieldContainer;
    }

    /**
     * Method for drawing snake on the field using view.
     */
    public List<Node> drawSnake(Snake snake) throws NullPointerException {
        if (snake == null) {
            throw new NullPointerException();
        }
        List<Node> nodes = new ArrayList<>();
        SnakeBody block = snake.getHead();
        nodes.add(view.drawSnakeHead(block.getPoint().getX(),
                block.getPoint().getY(),
                DirectionManager.calculateRotateAngle(block.getDirection())));
        SnakeBody nextBlock;
        int rotate;
        for (int i = 1; i < snake.getSnakeLength() - 1; i++) {
            block = snake.getSnake().get(i);
            nextBlock = snake.getSnake().get(i + 1);
            if (block.getDirection() == nextBlock.getDirection()) {
                rotate = DirectionManager.calculateRotateAngle(block.getDirection());
                nodes.add(view.drawSnakeBody(block.getPoint().getX(),
                        block.getPoint().getY(), rotate));
            } else {
                rotate = DirectionManager.calculateRotateAngle(DirectionManager.getTurnDirection(
                        nextBlock.getDirection(), block.getDirection()));
                nodes.add(view.drawSnakeTurn(block.getPoint().getX(),
                        block.getPoint().getY(), rotate));
            }
        }
        block = snake.getTail();
        nodes.add(view.drawSnakeTail(block.getPoint().getX(),
                block.getPoint().getY(),
                DirectionManager.calculateRotateAngle(block.getDirection())));
        return nodes;
    }

    /**
     * Updates player's snake position on game field.
     */
    public void updatePlayer() {
        fieldContainer.getChildren().removeAll(playerNode);
        playerNode = drawSnake(field.getPlayer());
        fieldContainer.getChildren().addAll(playerNode);
    }

    public void drawFoodPoint(Point point) {
        fieldContainer.getChildren().add(view.drawCircle(point.getX(), point.getY(), Color.GOLD));
    }

    /**
     * Erases the specified food point from game field.
     */
    public void eraseFoodPoint(Point point) {
        fieldContainer.getChildren().removeIf(node ->
                view.getModelCoordinates((int) node.getLayoutX()) == point.getX()
                        && view.getModelCoordinates((int) node.getLayoutY()) == point.getY());
    }
}
