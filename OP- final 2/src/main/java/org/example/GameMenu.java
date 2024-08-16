package org.example;

import javax.swing.*;
import java.awt.*;

public class GameMenu {
    private JFrame menuFrame;
    private final Game game;

    public GameMenu(Game game) {
        this.game = game;
        createMenu();
    }

    private void createMenu() {
        menuFrame = new JFrame("Game Menu");
        menuFrame.setSize(400, 300);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        menuFrame.add(panel);

        JButton startButton = new JButton("Start New Game");
        JButton helpButton = new JButton("Help");
        JButton exitButton = new JButton("Exit");
        JButton levelSelectButton = new JButton("Select Level");

        panel.add(startButton);
        panel.add(helpButton);
        panel.add(levelSelectButton);
        panel.add(exitButton);

        startButton.addActionListener(e -> {
            menuFrame.setVisible(false);
            game.startNewGame(1);
        });

        helpButton.addActionListener(e -> showHelp());

        levelSelectButton.addActionListener(e -> showLevelSelection());

        exitButton.addActionListener(e -> System.exit(0));

        menuFrame.setVisible(true);
    }

    private void showHelp() {
        JOptionPane.showMessageDialog(menuFrame, "\n" +
                "Use arrows to move.\n" +
                "Collect items, avoid the enemy.");
    }

    private void showLevelSelection() {
        String[] options = new String[3];
        for (int i = 0; i < options.length; i++) {
            options[i] = "Level " + (i + 1);
        }

        int selectedLevel = JOptionPane.showOptionDialog(menuFrame, "Select a level to start:", "Select Level",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (selectedLevel >= 0) {
            int level = selectedLevel + 1;
            menuFrame.setVisible(false);
            game.startNewGame(level);
        }
    }
    public void showMenu() {
        menuFrame.setVisible(true);
    }
}
