package com.erasko.model;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Player {

    private String name;
    private int id;
    private String symbol;
    private int winsCount;

    public Player() {
    }

    public Player(String name, int id, String symbol) {
        this.name = name;
        this.id = id;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getWinsCount() {
        return winsCount;
    }

    public void setWinsCount() {
        winsCount++;
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
