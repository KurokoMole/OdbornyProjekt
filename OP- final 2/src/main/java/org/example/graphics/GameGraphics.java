package org.example.graphics;

import org.example.Game;
import org.example.logic.GameLogic;

import javax.swing.*;
import java.awt.*;

public class GameGraphics extends JFrame {
    private Game game;
    public Draw draw;
    private GameLogic logic;
    private Image backgroundImage;
    private boolean gameOver = false;
    private boolean gameWinner = false;

    public GameGraphics(Game game, GameLogic logic) {
        this.game = game;
        this.logic = logic;
        this.draw = new Draw();
        this.backgroundImage = new ImageIcon(getClass().getResource("/" + logic.getBackgroundImagePath())).getImage();
        add(draw);

        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void removeButtons() {
        for (Component component : draw.getComponents()) {
            if (component instanceof JButton) {
                draw.remove(component);
            }
        }
        draw.revalidate();
        draw.repaint();
    }

    public class Draw extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }

            g.drawImage(logic.getPlayer().getImage(), logic.getPlayer().getX(), logic.getPlayer().getY(), this);

            for (var enemy : logic.getEnemies()) {
                g.drawImage(enemy.getImage(), enemy.getRectangle().x, enemy.getRectangle().y, this);
            }

            for (var item : logic.getItems()) {
                if (item.isActive()) {
                    g.drawImage(item.getImage(), item.getRectangle().x, item.getRectangle().y, this);
                }
            }

            if (logic.isGameOver()) {
                showGameOver(g);
            } else if (logic.isWinner()) {
                showWinner(g);
            }
        }

        private void showGameOver(Graphics g) {
            String message = "Game Over";
            drawMessage(g, message, Color.RED);
            showActionButtons("Restart", "Exit");
        }

        private void showWinner(Graphics g) {
            String message = "Winner!";
            drawMessage(g, message, Color.GREEN);
            showActionButtons("Next Level", "Exit");
        }

        private void drawMessage(Graphics g, String message, Color color) {
            Font font = new Font("Helvetica", Font.BOLD, 50);
            FontMetrics metrics = g.getFontMetrics(font);

            int x = (getWidth() - metrics.stringWidth(message)) / 2;
            int y = (getHeight() - metrics.getHeight()) / 2 - 50;

            g.setColor(color);
            g.setFont(font);
            g.drawString(message, x, y);
        }

        private void showActionButtons(String primaryAction, String secondaryAction) {
            JButton primaryButton = new JButton(primaryAction);
            JButton secondaryButton = new JButton(secondaryAction);

            primaryButton.setBounds((getWidth() - 200) / 2, (getHeight() - 50) / 2, 200, 50);
            secondaryButton.setBounds((getWidth() - 200) / 2, (getHeight() + 50) / 2, 200, 50);

            primaryButton.addActionListener(e -> game.handlePrimaryAction(primaryAction));
            secondaryButton.addActionListener(e -> System.exit(0));

            setLayout(null);
            add(primaryButton);
            add(secondaryButton);
            revalidate();
            repaint();
        }
    }

    public void triggerGameOver() {
        this.gameOver = true;
        repaint();
    }

    public void triggerWinner() {
        this.gameWinner = true;
        repaint();
    }
}
