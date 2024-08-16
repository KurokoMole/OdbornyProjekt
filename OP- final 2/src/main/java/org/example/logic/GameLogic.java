package org.example.logic;

import org.example.graphics.GameGraphics;
import org.example.Game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameLogic {
    private Player player;
    private List<Enemy> enemies;
    private List<Item> items;
    private boolean gameOver;
    private boolean winner;
    private int level;
    private String backgroundImagePath;
    private GameGraphics graphics;
    private Game game;
    private final int ENEMY_STEPS = 5;
    private int playerSteps = 20;

    public GameLogic(int levels, GameGraphics graphics) throws IOException {
        this.graphics = graphics;
        this.level = 1;
        this.backgroundImagePath = "background1.png";
        this.player = new Player(20, 20, "penelope.png");
        this.enemies = new ArrayList<>();
        this.items = new ArrayList<>();
        this.gameOver = false;
        this.winner = false;
    }


    public void setGame(Game game) {
        this.game = game;
    }
    public Game getGame() {
        return game;
    }
    public void setGraphics(GameGraphics graphics) {
        this.graphics = graphics;
    }

    public void setLevel(int level) {
        this.level = level;
        this.items.clear();
        this.enemies.clear();
        this.playerSteps = 20 + (level - 1) * 5;
        this.winner = false;
        this.gameOver = false;

        this.player.setX(20);
        this.player.setY(20);

        switch (level) {
            case 1 -> {
                this.backgroundImagePath = "background1.png";
                enemies.add(new Enemy(350, 350, "colin.png", 5));
                items.add(new Item(100, 100, "item1.png"));
                items.add(new Item(200, 200, "item1.png"));
                items.add(new Item(300, 300, "item1.png"));
            }
            case 2 -> {
                this.backgroundImagePath = "background2.png";
                enemies.add(new Enemy(350, 350, "colin.png", 5));
                enemies.add(new Enemy(450, 450, "colin.png", 5));
                items.add(new Item(150, 150, "item2.png"));
                items.add(new Item(250, 250, "item2.png"));
                items.add(new Item(130, 150, "item2.png"));
            }
            case 3 -> {
                this.backgroundImagePath = "background3.png";
                enemies.add(new Enemy(350, 350, "colin.png", 10));
                enemies.add(new Enemy(450, 450, "colin.png", 10));
                enemies.add(new Enemy(550, 550, "colin.png", 10));
                items.add(new Item(200, 200, "item3.png"));
                items.add(new Item(300, 300, "item3.png"));
                items.add(new Item(380, 100, "item3.png"));
                items.add(new Item(400, 400, "item3.png"));
            }
        }

        graphics.requestFocusInWindow();
    }

    public void update() {
        if (gameOver || winner) return;

        for (Enemy enemy : enemies) {
            moveEnemyTowardsPlayer(enemy);
            if (player.isCollided(enemy.getRectangle())) {
                gameOver = true;
                graphics.triggerGameOver();
                return;
            }
        }

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (player.isCollided(item.getRectangle())) {
                item.inactivate();
                items.remove(i);
                i--;
            }
        }

        if (items.isEmpty()) {
            winner = true;
            graphics.triggerWinner();
        }
    }


    private void moveEnemyTowardsPlayer(Enemy enemy) {
        int deltaX = player.getX() - enemy.getRectangle().x;
        int deltaY = player.getY() - enemy.getRectangle().y;

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            if (deltaX > 0) {
                enemy.move(ENEMY_STEPS, Direction.RIGHT);
            } else {
                enemy.move(ENEMY_STEPS, Direction.LEFT);
            }
        } else {
            if (deltaY > 0) {
                enemy.move(ENEMY_STEPS, Direction.DOWN);
            } else {
                enemy.move(ENEMY_STEPS, Direction.UP);
            }
        }
    }

    public void movePlayer(Direction direction) {
        if (!gameOver && !winner) {
            player.move(direction, playerSteps);

            if (player.getX() < 0) {
                player.setX(graphics.getWidth());
            } else if (player.getX() > graphics.getWidth()) {
                player.setX(0);
            }

            if (player.getY() < 0) {
                player.setY(graphics.getHeight());
            } else if (player.getY() > graphics.getHeight()) {
                player.setY(0);
            }
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }
    public int getLevel() {
        return this.level;
    }
    public boolean isWinner() {
        return winner;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Item> getItems() {
        return items;
    }

    public String getBackgroundImagePath() {
        return backgroundImagePath;
    }
}
