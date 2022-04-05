package com.erasko.service;

import com.erasko.model.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FieldService {

    Field gameField;

    @Autowired
    public FieldService(Field field) {
        this.gameField = field;
    }

    public int[][] getField() {
        return gameField.getField();
    }


    public boolean setStepOnField(String playerId, String coords) {
        System.out.println(coords);
        if(gameField.checkCoordinate(coords)) // если координаты корректны, то
        {
            String[] coordsArray = coords.split("");
            int x = Integer.parseInt(coordsArray[0]) - 1;
            int y = Integer.parseInt(coordsArray[1]) - 1; // записываем в поле
            gameField.gameMove(x, y, Integer.parseInt(playerId));
            return true;
        }
        return false;
    }

    public boolean isGameOver() {
         return gameField.gameOver;
    }

    public void clear() {
        gameField.clear();
        gameField.gameOver = false;
    }

    public int getStepsCount() {
        return gameField.GAME_STEP_NUMBERS;
    }
}
