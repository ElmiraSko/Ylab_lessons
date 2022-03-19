package com.erasko.lns2.DTO;

public class GameProcessDTO {

    private GamePlayDTO Gameplay;

    public GameProcessDTO() {
    }

    public GameProcessDTO(GamePlayDTO gameplay) {
        Gameplay = gameplay;
    }

    public GamePlayDTO getGameplay() {
        return Gameplay;
    }

    public void setGameplay(GamePlayDTO gameplay) {
        Gameplay = gameplay;
    }

    @Override
    public String toString() {
        return "GameProcessDTO{" +
                "Gameplay=" + Gameplay +
                '}';
    }
}
