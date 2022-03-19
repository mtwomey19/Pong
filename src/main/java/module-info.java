module main {
    requires javafx.fxml;
    requires javafx.controls;

    opens com.pong;
    exports com.pong;
}