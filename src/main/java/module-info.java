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

    opens project.drivefx to javafx.fxml;
    exports project.drivefx;
    exports project.drivefx.backend;
    opens project.drivefx.backend to javafx.fxml;
    exports project.drivefx.backend.directoryNavigator;
    opens project.drivefx.backend.directoryNavigator to javafx.fxml;
    exports project.drivefx.backend.login;
    opens project.drivefx.backend.login to javafx.fxml;
    exports project.drivefx.backend.apiHandler;
    opens project.drivefx.backend.apiHandler to javafx.fxml;
}