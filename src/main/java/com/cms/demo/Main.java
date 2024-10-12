package com.cms.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Courses Management System");
        Image appIcon = new Image(Objects.requireNonNull(getClass().getResource("/icons/Learning.png")).toExternalForm());
        stage.getIcons().add(appIcon);
        stage.setScene(scene);
        stage.setMinHeight(550);
        stage.setMinWidth(840);
        stage.show();
        System.out.println("Test");
    }

    public static void main(String[] args) {
        launch();
    }
}