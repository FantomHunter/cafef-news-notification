package com.codehunter.cafefnewsnotification;

import com.codehunter.cafefnewsnotification.model.NewsSDO;
import com.codehunter.cafefnewsnotification.util.JsoupUtil;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.Notifications;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

public class HelloController {
    private static final Logger log = LogManager.getLogger(HelloController.class);
    public static final String CAFEF_DOMAIN = "https://cafef.vn";
    private final HostServices hostServices;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    public HelloController(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    @FXML
    protected void onNotifyButtonClick() {
        log.info("onNotifyButtonClick");
        pushNotification();
    }

    private void pushNotification() {
        log.info("pushNotification");
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPannable(true);
        scrollPane.setPrefViewportHeight(400);
        VBox container = new VBox();

        List<NewsSDO> newsSDOList = JsoupUtil.getHotNewsUrl("https://cafef.vn/doc-nhanh.chn");
        newsSDOList.forEach(news -> {
            Hyperlink hyperlink = new Hyperlink(news.time() + ": " + news.title());
            hyperlink.setOnAction(event -> {
                hostServices.showDocument(CAFEF_DOMAIN + news.url());
            });
            Tooltip tooltip = new Tooltip(news.detail());
            tooltip.setShowDelay(Duration.ZERO);
            hyperlink.setTooltip(tooltip);
            container.getChildren().add(hyperlink);
        });
        scrollPane.setContent(container);

        Notifications notificationBuilder = Notifications.create()
                .title("News")
                .graphic(scrollPane)
                    .hideAfter(Duration.seconds(120))
                .onAction(event -> {
                    log.info("Notification clicked on!");
                });
        notificationBuilder.show();
    }
}