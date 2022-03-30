package com.erasko.model;

import org.springframework.stereotype.Component;

@Component
public class CurrentPlayers {
    Player player1;
    Player player2;
    Player lastWentPlayer;

    public CurrentPlayers() {
    }

    public CurrentPlayers(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Player getLastWentPlayer() {
        return lastWentPlayer;
    }

    public void setLastWentPlayer(Player lastWentPlayer) {
        this.lastWentPlayer = lastWentPlayer;
    }

    @Override
    public String toString() {
        return "CurrentPlayers{" +
                "player1=" + player1 +
                ", player2=" + player2 +
                '}';
    }
}
