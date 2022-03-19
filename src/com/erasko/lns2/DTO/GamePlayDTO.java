package com.erasko.lns2.DTO;

import java.util.ArrayList;

public class GamePlayDTO {

    private ArrayList<PlayerDTO> players;

   private GameDTO game;

    private GameResultDTO gameResult;

    public GamePlayDTO() {}

    public GamePlayDTO(ArrayList<PlayerDTO> players, GameDTO game, GameResultDTO gameResult) {
        this.players = players;
        this.game = game;
        this.gameResult = gameResult;
    }

    public ArrayList<PlayerDTO> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<PlayerDTO> players) {
        this.players = players;
    }

    public GameDTO getGame() {
        return game;
    }

    public void setGame(GameDTO game) {
        this.game = game;
    }

    public GameResultDTO getGameResult() {
        return gameResult;
    }

    public void setGameResult(GameResultDTO gameResult) {
        this.gameResult = gameResult;
    }
}
