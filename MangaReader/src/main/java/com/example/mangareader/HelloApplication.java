package com.example.mangareader;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene search = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Search");
        stage.setScene(search);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}