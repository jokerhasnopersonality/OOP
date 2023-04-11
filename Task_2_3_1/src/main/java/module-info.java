module io.github.jokerhasnopersonality {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    exports io.github.jokerhasnopersonality.snake_app;
    opens io.github.jokerhasnopersonality.snake_app to javafx.fxml;
}