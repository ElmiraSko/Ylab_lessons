package com.erasko.service;

public interface FieldService {

    int[][] getField();
    boolean setStepOnField(String playerId, String coords);
    boolean isGameOver();
    int getStepsCount();
    void clear();
}
