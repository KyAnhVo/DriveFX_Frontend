module project.drivefx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.compiler;

    requires org.json;
    requires javafx.graphics;
    requires java.desktop;

    opens com.drivefx to javafx.fxml;
    exports com.drivefx;
    exports com.drivefx.storage;
    opens com.drivefx.storage to javafx.fxml;
    exports com.drivefx.authentication;
    opens com.drivefx.authentication to javafx.fxml;
    exports com.drivefx.network;
    opens com.drivefx.network to javafx.fxml;
}