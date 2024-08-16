package org.example.logic;

public class Item extends Entity {
    private boolean active;

    public Item(int x, int y, String url) {
        super(x, y, url);
        this.active = true;
    }
    public boolean isActive() {
        return active;
    }

    public void inactivate() {
        this.active = false;
    }
}
