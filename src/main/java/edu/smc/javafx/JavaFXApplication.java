package edu.smc.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFXApplication extends Application {

    public static final String TITLE = "Demo";
    public static final int V = 320;
    public static final int V_1 = 240;
    public static final String VIEW_FXML = "initial-view.fxml";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(JavaFXApplication.class.getResource(VIEW_FXML));
        Scene scene = new Scene(loader.load(), V, V_1);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}