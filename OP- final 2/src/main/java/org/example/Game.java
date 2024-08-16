package org.example;

import org.example.graphics.GameGraphics;
import org.example.logic.Direction;
import org.example.logic.GameLogic;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class Game {
    private final GameLogic logic;
    private final GameGraphics graphics;
    public static boolean isPaused;

    public Game(int levels) throws IOException {
        this.logic = new GameLogic(levels, null);
        this.graphics = new GameGraphics(this, logic);
        logic.setGraphics(graphics);
        logic.setGame(this);
        setupEventListeners();
        isPaused = false;
    }

    public void handlePrimaryAction(String action) {
        if (action.equals("Restart")) {
            logic.setLevel(logic.getLevel());
        } else if (action.equals("Next Level")) {
            logic.setLevel(logic.getLevel() + 1);
        }

        graphics.removeButtons();
        logic.update();
        isPaused = false;
        graphics.requestFocusInWindow();
        startGameLoop();
        graphics.repaint();
    }

    public void startNewGame(int level) {
        logic.setLevel(level);
        graphics.setVisible(true);
        startGameLoop();
    }

    private void setupEventListeners() {
        graphics.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (isPaused) return;

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT -> movePlayer(Direction.LEFT);
                    case KeyEvent.VK_RIGHT -> movePlayer(Direction.RIGHT);
                    case KeyEvent.VK_UP -> movePlayer(Direction.UP);
                    case KeyEvent.VK_DOWN -> movePlayer(Direction.DOWN);
                    case KeyEvent.VK_SPACE -> handleItemPickup();
                    case KeyEvent.VK_ESCAPE -> togglePause();
                }
            }
        });

        graphics.setFocusable(true);
        graphics.requestFocusInWindow();
    }

    private void movePlayer(Direction direction) {
        logic.movePlayer(direction);
    }

    private void handleItemPickup() {
    }

    public void togglePause() {
        isPaused = !isPaused;
        graphics.repaint();
    }

    public void updateGame() {
        if (!isPaused && !logic.isGameOver()) {
            logic.update();
            graphics.repaint();
        }

        if (logic.isGameOver()) {
            graphics.triggerGameOver();
        } else if (logic.isWinner()) {
            graphics.triggerWinner();
        }
    }

    private void startGameLoop() {
        new Timer(50, e -> {
            if (!isPaused && !logic.isGameOver()) {
                updateGame();
            }
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Game game = new Game(3);
                GameMenu menu = new GameMenu(game);
                menu.showMenu();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
