package com.erasko.lns2;

import java.util.Objects;

public class Player {

    private String name;

    private int winsCount;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getWinsCount() {
        return winsCount;
    }

    public void addWins() {
        winsCount++;
    }

    public String getPlayerRating() {
        return name + ": " + winsCount;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

