package com.erasko.lns2.loggers;

import java.util.ArrayList;

public abstract class GameLogger {

    // вспомогательный массив для хранения ходов
    protected int[][] helpField;
    // Список игроков
    protected ArrayList<String> playerList;
    // Список снимков игрового поля по ходам
    protected ArrayList<int[][]> playerStepArray;
    // Храним результат
    protected StringBuilder winnerOrDraw;

    // Счетчик игр, соответсвует счетчику класса GameService
    // учавствует в формировании имени xml-файла
    protected int count;

    protected String fileName = "play";
    protected String pl1Name;
    protected String pl2Name;

    public ArrayList<String> getPlayerList() {
        return playerList;
    }

    public ArrayList<int[][]> getPlayerStepArray() {
        return playerStepArray;
    }

    public String getWinnerOrDraw() {
        return winnerOrDraw.toString();
    }

    public abstract void writePlayers(String name1, String name2);

    public abstract void writeStep(int num, String coords);

    public abstract void writeWinnerOrDraw(String winner);

    public abstract void readXMLFile();

    // Служебный метод, создает кадры состояния игрового поля
    protected void writeCoordsInHelpField(String coords, int playerId) {
        int x = Integer.parseInt(coords.substring(0,1)) - 1;
        int y = Integer.parseInt(coords.substring(1)) - 1;
        if (playerId == 1) {
            helpField[x][y] = 7;
        }
        if (playerId == 2) {
            helpField[x][y] = 2;
        }
        int[][] currentField = new int[3][3];
        for(int i=0; i<currentField.length; i++)
            System.arraycopy(helpField[i], 0, currentField[i], 0, currentField[i].length);
        // Сохранили в список очередное состояние поля
        playerStepArray.add(currentField);
    }
}
