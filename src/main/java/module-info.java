module com.ukma.yehor.cs_goodsstorage {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires jdk.compiler;

    opens com.ukma.yehor.cs_goodsstorage.controller to javafx.fxml;
    exports com.ukma.yehor.cs_goodsstorage.controller to javafx.graphics;
}