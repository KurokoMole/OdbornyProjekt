package org.example.logic;

import javax.swing.*;
import java.awt.*;

public class Wall {
    private Rectangle bounds;
    private Image image;

    public Wall(int x, int y, String imagePath) {
        this.bounds = new Rectangle(x, y, 50, 50);
        this.image = new ImageIcon(imagePath).getImage();
    }

    public Rectangle getRectangle() {
        return bounds;
    }
}

