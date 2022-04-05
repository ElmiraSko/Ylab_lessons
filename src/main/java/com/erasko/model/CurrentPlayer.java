package com.erasko.model;

import java.io.Serializable;

public class CurrentPlayer implements Serializable {

    private String name;
    private int gameId;
    private String symbol;
    private int winsCount;

    public CurrentPlayer() {
    }

    public CurrentPlayer(String name, int gameId, String symbol, int winsCount) {
        this.name = name;
        this.gameId = gameId;
        this.symbol = symbol;
        this.winsCount = winsCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
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

    public void setWinsCount(int winsCount) {
        this.winsCount = winsCount;
    }
}
