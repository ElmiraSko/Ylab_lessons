package com.erasko.service.impl;

import com.erasko.model.Field;
import com.erasko.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FieldServiceImp implements FieldService {

    Field gameField;

    @Autowired
    public FieldServiceImp(Field field) {
        this.gameField = field;
    }

    @Override
    public int[][] getField() {
        return gameField.getField();
    }

    @Override
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

    @Override
    public boolean isGameOver() {
         return gameField.gameOver;
    }

    @Override
    public void clear() {
        gameField.clear();
        gameField.gameOver = false;
    }

    @Override
    public int getStepsCount() {
        return gameField.GAME_STEP_NUMBERS;
    }
}
