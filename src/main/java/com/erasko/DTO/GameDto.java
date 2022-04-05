package com.erasko.DTO;

import com.erasko.model.*;

// Используем при чтении из таблицы
public class GameDto {
    // массив двух текущих играков
    CurrentPlayerDto[] currentPlayers;
    // результат игры
    Result result = new Result("");
    // игровое поле
    int[][] field;
    /**
     * stepCount - счетчик ходов в контексте одной игры.
     * Это поле пока только в этом классе, нужно подумать!
     */
    int stepCount;

    public GameDto() {
    }

    public GameDto(CurrentPlayerDto[] currentPlayers, Result result, int[][] field) {
        this.currentPlayers = currentPlayers;
        this.result = result;
        this.field = field;
    }

    public CurrentPlayerDto[] getCurrentPlayers() {
        return currentPlayers;
    }

    public void setCurrentPlayers(CurrentPlayerDto[] currentPlayers) {
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

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }
}