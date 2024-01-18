package com.example.mangareader;

import controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import utils.MainControllerUtils;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        HBox root = fxmlLoader.load();

        // Set the primary stage in MainControllerUtils
        MainControllerUtils.setPrimaryStage(stage);

        // Set the reference to the MainController instance
        MainController mainController = fxmlLoader.getController();
        MainControllerUtils.setMainController(mainController);

        Scene search = new Scene(root, 320, 240);
        stage.setTitle("Search");
        stage.setScene(search);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}
