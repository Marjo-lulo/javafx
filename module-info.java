module m11 {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.media;
    requires javafx.swing;
    requires javafx.web;
    requires javafx.base;
    requires javafx.swt;

    opens ma to javafx.fxml;
    exports ma;

}