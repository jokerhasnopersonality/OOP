package io.github.jokerhasnopersonality.snake;

import java.util.Objects;

import io.github.jokerhasnopersonality.snake.model.Direction;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * View class for drawing game components.
 */
public class View {
    private final int blockSize;
    private final int foodSize;
    private final int width;
    private final int height;
    private Text scoreText;
    private final Image head;
    private final Image body;
    private final Image tail;
    private final Image turn;

    /**
     * View constructor.

     * @param width width of the game field
     * @param height height of the game field
     * @param blockSize size of a single block of game field
     */
    public View(int width, int height, int blockSize) {
        this.width = width;
        this.height = height;
        this.blockSize = blockSize;
        foodSize = 25;

        head = new Image(
                Objects.requireNonNull(View.class.getResourceAsStream("/images/snake_head1.png")));
        body = new Image(
                Objects.requireNonNull(View.class.getResourceAsStream("/images/snake_body1.png")));
        tail = new Image(
                Objects.requireNonNull(View.class.getResourceAsStream("/images/snake_tail.png")));
        turn = new Image(
                Objects.requireNonNull(View.class.getResourceAsStream("/images/snake_turn2.png")));
    }

    /**
     * Method for drawing a circle node.
     */
    public Node drawCircle(Color color, int x, int y) {
        int r = blockSize / 2;
        Circle circle = new Circle(r, r, (int) (foodSize / 2));
        circle.setLayoutX(blockSize * x);
        circle.setLayoutY(blockSize * y);
        circle.setFill(color);
        circle.setStroke(color.darker());
        circle.setStrokeWidth(3);
        return circle;
    }

    /**
     * Method for drawing a block node.
     */
    public Node drawBlock(int x, int y, Color color) {
        Rectangle rectangle = new Rectangle(blockSize - 2, blockSize - 2);
        rectangle.setFill(color);
        rectangle.setLayoutX(x * blockSize);
        rectangle.setLayoutY(y * blockSize);
        rectangle.setStroke(color.darker());
        rectangle.setStrokeWidth(4);
        return rectangle;
    }

    /**
     * Method for drawing snake head.
     */
    public Node drawSnakeHead(int x, int y, Direction direction) {
        return drawImageBlock(head, x, y, direction);
    }

    /**
     * Method for drawing snake body block.
     */
    public Node drawSnakeBody(int x, int y, Direction direction) {
        return drawImageBlock(body, x, y, direction);
    }

    /**
     * Method for drawing snake tail.
     */
    public Node drawSnakeTail(int x, int y, Direction direction) {
        return drawImageBlock(tail, x, y, direction);
    }

    /**
     * Method for drawing body block of snake that changes direction.
     */
    public Node drawSnakeTurn(int x, int y, Direction direction) {
        return drawImageBlock(turn, x, y, direction);
    }

    /**
     * Method for drawing image block considering direction.
     */
    public Node drawImageBlock(Image image, int x, int y, Direction direction) {
        Rectangle rectangle = new Rectangle(x * blockSize, y * blockSize,
                blockSize - 2, blockSize - 2);
        rectangle.setFill(Color.PALETURQUOISE);
        rectangle.setStroke(Color.PALETURQUOISE.darker());
        rectangle.setStrokeWidth(3);
        Node node = new ImageView(image);
        switch (direction) {
            case LEFT: {
                node.setRotate(90);
                break;
            }
            case RIGHT: {
                node.setRotate(-90);
                break;
            }
            case UP: {
                node.setRotate(180);
                break;
            }
        }
        //rectangle.setLayoutX(x * blockSize);
        //rectangle.setLayoutY(y * blockSize);
        node.setLayoutX(x * blockSize);
        node.setLayoutY(y * blockSize);
        return rectangle;
        //return node;
    }

    /**
     * Initiates pause GUI component.
     */
    public Pane initPause() {
        Rectangle pauseBackground = new Rectangle(width * blockSize, height * blockSize);
        pauseBackground.setOpacity(0.60);
        pauseBackground.setFill(Color.SLATEGRAY.darker());
        Text text = new Text("PAUSED");
        text.setFont(Font.font("consolas", FontWeight.BOLD, FontPosture.REGULAR, 50));
        text.setFill(Color.RED);
        text.setWrappingWidth(width * blockSize);
        text.setTextAlignment(TextAlignment.CENTER);
        StackPane pauseContainer = new StackPane();
        pauseContainer.getChildren().addAll(pauseBackground, text);

        return pauseContainer;
    }

    /**
     * Initiates menu GUI component.
     */
    public Pane initMenu(int goal) {
        StackPane menuContainer = new StackPane();
        Rectangle menuBackground = new Rectangle(250, height * blockSize);
        menuBackground.setFill(Color.SILVER);
        menuContainer.setLayoutX(width * blockSize);
        menuBackground.setStroke(Color.SILVER.darker());
        menuBackground.setStrokeWidth(2);
        menuBackground.setManaged(false);

        Text header = new Text("YOUR PROGRESS:");
        header.setFont(Font.font("consolas", FontWeight.BOLD, FontPosture.REGULAR, 20));
        header.setWrappingWidth(menuBackground.getWidth());
        header.setTextAlignment(TextAlignment.CENTER);

        Text foodText = new Text("Food eaten:");
        foodText.setFont(Font.font("consolas", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        foodText.setWrappingWidth(menuBackground.getWidth());

        scoreText = new Text(String.format("0/%d", goal));
        scoreText.setFont(Font.font("consolas", FontWeight.NORMAL, FontPosture.REGULAR, 17));
        scoreText.setWrappingWidth(menuBackground.getWidth());

        Text helpText = new Text("New Game:      ENTER\nPause:           ESC"
                + "\nHelp:            ALT");
        helpText.setFont(Font.font("consolas", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        helpText.setWrappingWidth(menuBackground.getWidth());

        VBox menu = new VBox();
        menu.setPrefSize(250, height * blockSize);
        menu.setAlignment(Pos.TOP_LEFT);
        menu.setSpacing(10);
        menu.getChildren().addAll(header, foodText, scoreText, helpText);
        menu.setPadding(new Insets(15));
        menuContainer.getChildren().addAll(menuBackground, menu);
        return menuContainer;
    }

    public Text getScoreText() {
        return scoreText;
    }

    /**
     * Initiates gameOver GUI component.
     */
    public Pane initGameOver() {
        Rectangle gameOverBackground = new Rectangle(width * blockSize, height * blockSize);
        gameOverBackground.setOpacity(0.80);
        gameOverBackground.setFill(Color.SLATEGRAY.darker());
        Text text = new Text("GAME OVER");
        text.setFont(Font.font("consolas", FontWeight.BOLD, FontPosture.REGULAR, 50));
        text.setFill(Color.RED);
        text.setWrappingWidth(width * blockSize);
        text.setTextAlignment(TextAlignment.CENTER);
        Pane gameOverContainer = new StackPane();
        gameOverContainer.getChildren().addAll(gameOverBackground, text);

        return gameOverContainer;
    }

    /**
     * Initiates win scenario GUI component.
     */
    public Pane initGameWon() {
        Rectangle gameWonBackground = new Rectangle(width * blockSize, height * blockSize);
        gameWonBackground.setOpacity(0.40);
        gameWonBackground.setFill(Color.GOLD);
        Text text = new Text("YOU WON!");
        text.setFill(Color.LIMEGREEN);
        text.setFont(Font.font("consolas", FontWeight.BOLD, FontPosture.REGULAR, 50));
        text.setWrappingWidth(width * blockSize);
        text.setTextAlignment(TextAlignment.CENTER);
        Pane gameWonContainer = new StackPane();
        gameWonContainer.getChildren().addAll(gameWonBackground, text);

        return gameWonContainer;
    }

    /**
     * Initiates help instructions GUI component.
     */
    public Pane initHelp() {
        StackPane helpContainer = new StackPane();
        Rectangle background = new Rectangle(600, 250);
        background.setFill(Color.SILVER);
        helpContainer.setLayoutX((int) (width * blockSize / 2) - 300);
        helpContainer.setLayoutY((int) (height * blockSize / 2) - 125);
        background.setStroke(Color.SILVER.darker());
        background.setStrokeWidth(5);
        background.setManaged(false);

        Text header = new Text("GAME CONTROLS:");
        header.setFont(Font.font("consolas", FontWeight.BOLD, FontPosture.REGULAR, 20));
        header.setWrappingWidth(background.getWidth());
        header.setTextAlignment(TextAlignment.CENTER);

        Text moveText = new Text("Move snake:");
        moveText.setFont(Font.font("consolas", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        moveText.setWrappingWidth(background.getWidth());

        Text closeText = new Text("Close Help:                                     ALT");
        closeText.setFont(Font.font("consolas", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        closeText.setWrappingWidth(background.getWidth());

        ImageView imageView = new ImageView(new Image(
                Objects.requireNonNull(View.class.getResourceAsStream("/images/help_image1.png"))));
        imageView.setScaleX(1.5);
        imageView.setScaleY(1.5);
        ((Node) imageView).setTranslateX(
                (background.getWidth() - imageView.getImage().getWidth()) / 2);

        VBox menu = new VBox();
        menu.setPrefSize(600, 250);
        menu.setAlignment(Pos.TOP_LEFT);
        menu.setSpacing(20);
        menu.getChildren().addAll(header, moveText, imageView, closeText);
        menu.setPadding(new Insets(15));

        helpContainer.getChildren().addAll(background, menu);

        return helpContainer;
    }
}
