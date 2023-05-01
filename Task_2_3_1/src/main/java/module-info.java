module io.github.jokerhasnopersonality {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    exports io.github.jokerhasnopersonality.snake;
    opens io.github.jokerhasnopersonality.snake to javafx.fxml;
    exports io.github.jokerhasnopersonality.snake.model;
    opens io.github.jokerhasnopersonality.snake.model to javafx.fxml;
    exports io.github.jokerhasnopersonality.snake.controller;
    opens io.github.jokerhasnopersonality.snake.controller to javafx.fxml;
}