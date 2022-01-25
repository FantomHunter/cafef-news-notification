package com.codehunter.cafefnewsnotification.service;

import com.codehunter.cafefnewsnotification.model.NewsSDO;
import com.codehunter.cafefnewsnotification.util.JsoupUtil;
import javafx.application.HostServices;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.Notifications;

import java.util.List;
import java.util.Objects;

public class NewsService {
    private static final Logger log = LogManager.getLogger(NewsService.class);
    public static final String CAFEF_DOMAIN = "https://cafef.vn";

    private final HostServices hostServices;
    private List<NewsSDO> currentNewsList;

    public NewsService(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    public void pushNotification() {
        log.info("pushNotification");
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPannable(true);
        scrollPane.setPrefViewportHeight(400);
        VBox container = new VBox();

        List<NewsSDO> newsSDOList = JsoupUtil.getHotNewsUrl("https://cafef.vn/doc-nhanh.chn");
        if (currentNewsList == null || !Objects.equals(newsSDOList.get(0).id(), currentNewsList.get(0).id())) {
            newsSDOList.forEach(news -> {
                Hyperlink hyperlink = new Hyperlink(news.time() + ": " + news.title());
                Tooltip tooltip = new Tooltip(news.detail());
                tooltip.setShowDelay(Duration.ZERO);
                hyperlink.setTooltip(tooltip);
                hyperlink.setOnAction(event -> {
                    hostServices.showDocument(CAFEF_DOMAIN + news.url());
                });
                container.getChildren().add(hyperlink);
            });
            scrollPane.setContent(container);

            Notifications notificationBuilder = Notifications.create()
                    .title("News")
                    .graphic(scrollPane)
                    .hideAfter(Duration.seconds(30))
                    .onAction(event -> {
                        log.info("Notification clicked on!");
                    });
            notificationBuilder.show();
            currentNewsList = newsSDOList;
        } else {
            log.info("Nothing news");
        }
    }
}

