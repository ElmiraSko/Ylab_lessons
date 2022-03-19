package com.erasko.lns2.DTO;

public class GameResultDTO {

    private PlayerDTO player;

    public GameResultDTO() {
    }

    public GameResultDTO(PlayerDTO player) {
        this.player = player;
    }

    public PlayerDTO getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDTO player) {
        this.player = player;
    }
}
