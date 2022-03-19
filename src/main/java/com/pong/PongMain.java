package com.pong;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

/**
 * To control Player 1 (left paddle) use 'F' to move up and 'V' to move down
 * To control Player 2 (right paddle) use 'J' to move up and 'N' to move down
 * Enjoy :)
 */

public class PongMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL fxmlLocation = getClass().getResource("/fxml/MainPane.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Parent root = loader.load();
        primaryStage.setTitle("Pong");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        PongGame pong = loader.getController();
        pong.setUpGame();
        pong.monitorKeys();
    }
}
