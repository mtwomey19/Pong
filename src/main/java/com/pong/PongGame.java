package com.pong;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import java.util.HashSet;

public class PongGame {

    @FXML
    public Pane mainPane;
    @FXML
    public Button newGame;
    @FXML
    public Button pause;
    @FXML
    public Label p1Score;
    @FXML
    public Label p2Score;

    private Paddle paddle1;
    private Paddle paddle2;
    private Ball ball;
    private AnimationTimer animationTimer;
    private long lastRenderedAt;
    private HashSet<KeyCode> keysPressed;
    private boolean isPaused;

    private void makeSprites() {
        // Values chosen based on window height=400 and width=600
        paddle1 = new Paddle(mainPane, 2, 100);
        paddle2 = new Paddle(mainPane, 594, 200);
        ball = new Ball(mainPane);
    }

    public void setUpGame() {
        cleanScreen();
        makeSprites();
        keysPressed = new HashSet<>();
    }

    // when New Game button pressed...
    public void newGame(ActionEvent actionEvent) {
        setUpGame();
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastRenderedAt == 0) lastRenderedAt = now - 1;

                long timePassed = now - lastRenderedAt;
                lastRenderedAt = now;

                update(timePassed);
                getPlayerInput(paddle1, KeyCode.F, KeyCode.V);
                getPlayerInput(paddle2, KeyCode.J, KeyCode.N);
            }
        };
        animationTimer.start();
    }

    private void update(long timePassed) {
        ball.move(timePassed);
        ball.horizontalWallCheck(400);
        ball.verticalWallCheck(600);
        paddle1.move(timePassed);
        paddle2.move(timePassed);
        paddle1.checkWalls(400);
        paddle2.checkWalls(400);
        paddle1.checkContactWithBall(ball);
        paddle2.checkContactWithBall(ball);
        calculateScore();
    }

    public void monitorKeys() {
        mainPane.getScene().setOnKeyPressed((KeyEvent pressEvent) -> {
            keysPressed.add(pressEvent.getCode());
        });
        mainPane.getScene().setOnKeyReleased((KeyEvent releaseEvent) -> {
            keysPressed.remove(releaseEvent.getCode());
        });
    }

    private void getPlayerInput(Paddle paddle, Object up, Object down) {
        if (keysPressed.contains(up))
            paddle.moveUp();
        else if (keysPressed.contains(down))
            paddle.moveDown();
        else
            paddle.slowDown();
    }

    private void calculateScore() {
        int countP1Score = ball.getRightWallCounter();
        int countP2Score = ball.getLeftWallCounter();
        p1Score.setText(String.valueOf(countP1Score));
        p2Score.setText(String.valueOf(countP2Score));
    }

    public void pauseGame(ActionEvent actionEvent) {
        if (!isPaused) {
            animationTimer.stop();
            pause.setText("Resume");
            isPaused = true;
        }
        else {
            lastRenderedAt = System.nanoTime();
            animationTimer.start();
            pause.setText("Pause");
            isPaused = false;
        }
    }

    private void cleanScreen() {
        if (animationTimer != null)
            animationTimer.stop();

        if (ball != null)
            ball.removeOldBall();

        if (paddle1 != null)
            paddle1.removeOldPaddle();

        if (paddle2 != null)
            paddle2.removeOldPaddle();
    }
}
