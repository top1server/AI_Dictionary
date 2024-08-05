module app.ai_dictionary {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires jsapi;
    requires java.desktop;

    opens app.SourceCode.GUI.Controller to javafx.fxml;
    exports app.SourceCode.GUI.Controller;
    exports app.SourceCode.Fundamental;
    opens app.SourceCode.Fundamental to javafx.fxml;
}