package com.codehunter.cafefnewsnotification;

import com.codehunter.cafefnewsnotification.service.NewsService;
import com.codehunter.cafefnewsnotification.util.HostServicesControllerFactory;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class HelloApplication extends Application {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        fxmlLoader.setControllerFactory(new HostServicesControllerFactory(getHostServices()));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

//        FXTrayIcon icon = new FXTrayIcon(stage, getClass().getResource("cafef_favicon.ico"));
//        icon.show();

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        NewsService newsService = new NewsService(getHostServices());
//        newsService.pushNotification();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(180), event -> newsService.pushNotification()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args) {
        launch();
    }

}