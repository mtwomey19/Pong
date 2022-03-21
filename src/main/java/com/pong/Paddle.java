package com.pong;

import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Paddle {
    private Rectangle sprite;
    private double yPos;
    private double ySpeed;

    public Paddle(Pane mainPane, double xPos, double yPos) {
        this.sprite = new Rectangle();
        this.sprite.setHeight(48);
        this.sprite.setWidth(4);
        this.sprite.setFill(Paint.valueOf("black"));
        this.sprite.setX(xPos);
        this.sprite.setY(yPos);
        this.yPos = yPos;
        this.ySpeed = 200;
        mainPane.getChildren().add(this.sprite);
    }

    // updates paddle position
    public void render() {
        sprite.setY(yPos);
    }

    public void move(long nanosecondsPassed) {
        double seconds = nanosecondsPassed / 1e9; // convert nanoseconds to seconds for displacement calculation
        yPos = yPos + (ySpeed * seconds);
        render();
    }

    public void checkWalls(double height) {
        // keeps paddle from moving below screen
        if (yPos <= 0){
            ySpeed = 0;
            yPos = 3;
        }
        // keeps paddle from moving above screen
        else if (yPos >= (height - 48)) { // 48 represents the length of paddle
            ySpeed = 0;
            yPos = height - 51; // 51 represents length of paddle + 3
        }
    }

    public void moveUp() {
        ySpeed = -200;
    }
    public void moveDown() {
        ySpeed = 200;
    }
    public void slowDown() {
        ySpeed = 0;
    }

    public void checkContactWithBall(Ball ball) {
        Shape ballHitPaddle = Shape.intersect(sprite, ball.getSprite());
        Bounds collision = ballHitPaddle.getBoundsInParent();

        if (collision.getWidth() > 0 && collision.getHeight() > 0) { // represents a collision between ball and paddle
            ball.setXSpeed(ball.getXSpeed() * -1); // ball is sent in opposite direction after collision
            if (ball.getXPos() > 300)
                ball.setXPos(ball.getXPos() - 3); // buffer to prevent ball from sticking to right paddle
            else if (ball.getXPos() < 300)
                ball.setXPos(ball.getXPos() + 3); // buffer to prevent ball from sticking to left paddle
        }
    }

    public void removeOldPaddle() {
        if (sprite != null){
            Pane parent = (Pane) sprite.getParent();
            parent.getChildren().remove(sprite);
            sprite = null;
        }
    }
}
