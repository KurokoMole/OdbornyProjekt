package org.example.logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class Player {
    private int x;
    private int y;
    private final int width;
    private final int height;
    private Image image;

    public Player(int x, int y, String url) throws IOException {
        this.x = x;
        this.y = y;
        this.width = 50;
        this.height = 50;
        URL imageUrl = this.getClass().getResource("/" + url);
        this.image = ImageIO.read(imageUrl);
    }

    public void move(Direction direction, int steps) {
        switch (direction) {
            case RIGHT -> x += steps;
            case LEFT -> x -= steps;
            case UP -> y -= steps;
            case DOWN -> y += steps;
        }
    }

    public boolean isCollided(Rectangle other) {
        Rectangle playerRect = new Rectangle(x, y, width, height);
        return playerRect.intersects(other);
    }
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Rectangle getRectangle() {
        return new Rectangle(x, y, width, height);
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
