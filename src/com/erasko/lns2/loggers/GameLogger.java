package com.erasko.lns2.loggers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public abstract class GameLogger {

    // Вспомогательный массив для проставления
    // выполненных ходов (при чтении из файла)
    protected int[][] helpField;
    // Вспомогательный список игроков (при чтении из файла)
    protected ArrayList<String> helpPayerList;
    // Список снимков игрового поля по ходам (при чтении из файла)
    protected ArrayList<int[][]> playerStepArray;
    // Храним результат (при чтении из файла)
    protected StringBuilder winnerOrDraw;

    // Для временного хранения основных данных
    // получаемых по ходу игры, для дальнейшей записи в xml-файл
    protected ArrayList<String> allData;

    // Используем в формировании имени файла
    Date date;
    DateFormat dateFormat = new SimpleDateFormat("_dd_MM_yyyy(HHmmss)");

    protected String firstPartOfFile = "play";

    protected String currentNewRecordedFile;

    protected abstract void saveDataInFile();

    public abstract void readXMLFile(String fileName);

    public ArrayList<String> getPlayerList() {
        return helpPayerList;
    }

    public ArrayList<int[][]> getPlayerStepArray() {
        return playerStepArray;
    }

    public String getWinnerOrDraw() {
        return winnerOrDraw.toString();
    }

    public String getCurrentNewRecordedFile() {
        return currentNewRecordedFile;
    }

    // В allData сохраняем имена играков, (для записи в файл)
    public void writePlayers(String name1, String name2) {
        allData = new ArrayList<>();
        allData.add(name1);
        allData.add(name2);
    }

    // В allData сохранили ходы играков, (для записи в файл)
    public void writeStep(int num, String coords) {
        // определяем playerId
        String playerId = num % 2 == 1 ? "1" : "2";
        StringBuilder step = new StringBuilder()
                .append(num)
                .append(" ")
                .append(playerId)
                .append(" ")
                .append(coords);
        allData.add(step.toString());
    }

    // В allData сохранили результат игры, (для записи в файл)
    public void writeWinnerOrDraw(String result) {
        if (result.equals(allData.get(0))) {
            allData.add(result + " 1 X");
        } else if (result.equals(allData.get(1))) {
            allData.add(result + " 2 O");
        } else {
            allData.add(result);
        }
        saveDataInFile();
    }

    // Служебный метод, создает кадры состояния игрового поля (при чтении из файла)
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
