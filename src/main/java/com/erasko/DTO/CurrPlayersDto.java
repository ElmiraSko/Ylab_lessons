package com.erasko.DTO;

public class CurrPlayersDto {
    CurrentPlayerDto player1;
    CurrentPlayerDto player2;
    CurrentPlayerDto lastWentPlayer;

    public CurrPlayersDto() {
    }

    public CurrentPlayerDto getPlayer1() {
        return player1;
    }

    public void setPlayer1(CurrentPlayerDto player1) {
        this.player1 = player1;
    }

    public CurrentPlayerDto getPlayer2() {
        return player2;
    }

    public void setPlayer2(CurrentPlayerDto player2) {
        this.player2 = player2;
    }

    public CurrentPlayerDto getLastWentPlayer() {
        return lastWentPlayer;
    }

    public void setLastWentPlayer(CurrentPlayerDto lastWentPlayer) {
        this.lastWentPlayer = lastWentPlayer;
    }
}

