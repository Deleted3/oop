module com.example.messengerclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.google.auth;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires google.cloud.core;
    requires google.cloud.firestore;
    requires firebase.admin;
    requires com.google.auth.oauth2;
    requires com.google.api.apicommon;
    requires annotations;

    opens com.example.messengerclient to javafx.fxml;
    exports com.example.messengerclient;
}