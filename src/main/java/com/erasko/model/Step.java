package com.erasko.model;

import org.springframework.stereotype.Component;

@Component
public class Step {

    int num;
    String playerId;
    String symbol;
    String coords;

    public Step() {
    }

    public Step(int num, String playerId, String symbol, String coords) {
        this.num = num;
        this.playerId = playerId;
        this.symbol = symbol;
        this.coords = coords;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCoords() {
        return coords;
    }

    public void setCoords(String coords) {
        this.coords = coords;
    }

    @Override
    public String toString() {
        return "Step{" +
                "num=" + num +
                ", coords='" + coords + '}';
    }
}
