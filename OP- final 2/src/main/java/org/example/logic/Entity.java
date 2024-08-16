package org.example.logic;

import javax.swing.*;
import java.awt.*;

public class Entity {
    protected Rectangle bounds;
    protected Image image;

    public Entity(int x, int y, String url) {
        ImageIcon ii = new ImageIcon(getClass().getResource("/" + url));

        if (ii.getImageLoadStatus() != MediaTracker.COMPLETE) {
            throw new IllegalArgumentException("Image not found: " + url);
        }

        this.image = ii.getImage();
        this.bounds = new Rectangle(x, y, ii.getIconWidth(), ii.getIconHeight());
    }

    public void move(int steps, Direction direction) {
        switch (direction) {
            case LEFT -> bounds.x -= steps;
            case RIGHT -> bounds.x += steps;
            case UP -> bounds.y -= steps;
            case DOWN -> bounds.y += steps;
        }
    }

    public Rectangle getRectangle() {
        return bounds;
    }

    public Image getImage() {
        return image;
    }
}

