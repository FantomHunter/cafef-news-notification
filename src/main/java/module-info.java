module com.codehunter.cafefnewsnotification {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    requires org.apache.logging.log4j;
    requires org.jsoup;
//    requires FXTrayIcon;

    opens com.codehunter.cafefnewsnotification to javafx.fxml;
//    opens com.codehunter.cafefnewsnotification;
    exports com.codehunter.cafefnewsnotification;
}