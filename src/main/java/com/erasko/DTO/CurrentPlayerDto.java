package com.erasko.DTO;

import com.erasko.model.CurrentPlayer;
import java.io.Serializable;

public class CurrentPlayerDto implements Serializable {
    private String name;
    private int id;
    private String symbol;
    private int winsCount;

    public CurrentPlayerDto() {
    }

    public CurrentPlayerDto(String name, int id, String symbol, int winsCount) {
        this.name = name;
        this.id = id;
        this.symbol = symbol;
        this.winsCount = winsCount;
    }
    public CurrentPlayerDto(CurrentPlayer currPlayer) {
        name = currPlayer.getName();
        id = currPlayer.getGameId();
        symbol = currPlayer.getSymbol();
        winsCount = currPlayer.getWinsCount();
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

    public void setWinsCount(int winsCount) {
        this.winsCount = winsCount;
    }
}

