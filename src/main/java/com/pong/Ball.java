package com.pong;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Ball {
    private Circle sprite;
    private int size;

    private double xPos;
    private double yPos;
    private double xSpeed;
    private double ySpeed;

    private int touchRightWallCount;
    private int touchLeftWallCount;

    public Ball(Pane mainPane) {
        this.sprite = new Circle();
        this.size = 4;
        this.sprite.setRadius(this.size);
        this.sprite.setFill(Paint.valueOf("black"));
        this.xPos = 100;
        this.yPos = 100;
        this.xSpeed = 150;
        this.ySpeed = 150;

        this.render();
        mainPane.getChildren().add(this.sprite);
    }

    public void render() {
        sprite.setCenterX(xPos);
        sprite.setCenterY(yPos);
    }

    public void move(long nanosecondsPassed) {
        double seconds = nanosecondsPassed / 1e9;
        xPos = xPos + (xSpeed * seconds);
        yPos = yPos + (ySpeed * seconds);
        render();
    }

    public void horizontalWallCheck(double height) {
        if (yPos <= 0) {
            ySpeed *= -1;
            yPos = 5; // provides buffer to prevent ball from getting trapped along wall
        }
        else if (yPos >= height) {
            ySpeed *= -1;
            yPos = 400 - 5; // provides buffer to prevent ball from getting trapped along wall
        }
    }
    public void verticalWallCheck(int width) {
        if (xPos < 0 ){
            xPos = 200;
            xSpeed *= -1;
            touchLeftWallCount += 1;
        }
        else if (xPos > width){
            xPos = 400;
            xSpeed *= -1;
            touchRightWallCount += 1;
        }
    }

    public void removeOldBall() {
        if (sprite != null) {
            Pane parent = (Pane) sprite.getParent();
            parent.getChildren().remove(sprite);
            sprite = null;
        }
    }

    public Shape getSprite() {
        return sprite;
    }
    public double getXSpeed() {
        return xSpeed;
    }
    public void setXSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }
    public int getRightWallCounter() {
        return touchRightWallCount;
    }
    public int getLeftWallCounter() {
        return touchLeftWallCount;
    }
    public double getXPos() {
        return xPos;
    }
    public void setXPos(double xPos) {
        xPos = xPos;
    }
}