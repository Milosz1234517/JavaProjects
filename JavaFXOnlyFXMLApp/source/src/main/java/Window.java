import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.Objects;

public class Window extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    boolean isANext = false;

    @Override
    public void start(Stage primaryStage) throws Exception {

        URL fxmlDocPath = Objects.requireNonNull(getClass().getClassLoader().getResource("mainScene.fxml"));

        VBox vbox = FXMLLoader.load(fxmlDocPath);

        Button button = new Button("Change CSS");
        vbox.getChildren().add(button);
        Scene scene = new Scene(vbox);
        scene.getStylesheets().add("style1.css");

        button.setOnAction(ev -> {
            String css = isANext ? "style1.css" : "style2.css";
            isANext = !isANext;
            scene.getStylesheets().clear();
            scene.getStylesheets().add(css);
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}