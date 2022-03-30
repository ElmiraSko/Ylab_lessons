package com.erasko.DTO;

import com.erasko.model.*;

public class GameDTO {

    // массив двух текущих играков
    Player[] currentPlayers;
    // результат игры
    Result result = new Result("");
    // игровое поле
    int[][] field;
    // игрок сделавший ход последним
    Player lastWentPlayer;
    /**
     * stepCount - счетчик ходов в контексте одной игры.
     * Это поле пока только в этом классе, нужно подумать!
     */
    int stepCount;

    public GameDTO() {
    }

    public GameDTO(Player[] currentPlayers, Result result, int[][] field) {
        this.currentPlayers = currentPlayers;
        this.result = result;
        this.field = field;
    }

    public Player[] getCurrentPlayers() {
        return currentPlayers;
    }

    public void setCurrentPlayers(Player[] currentPlayers) {
        this.currentPlayers = currentPlayers;
    }

    public int[][] getField() {
        return field;
    }

    public void setField(int[][] field) {
        this.field = field;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Player getLastWentPlayer() {
        return lastWentPlayer;
    }

    public void setLastWentPlayer(Player lastWentPlayer) {
        this.lastWentPlayer = lastWentPlayer;
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }
}

