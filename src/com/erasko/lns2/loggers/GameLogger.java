package com.erasko.lns2.loggers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public abstract class GameLogger {

    // Вспомогательный массив для хранения ходов
    protected int[][] helpField;
    // Вспомогательный список игроков
    protected ArrayList<String> playerList;
    // Список снимков игрового поля по ходам
    protected ArrayList<int[][]> playerStepArray;
    // Храним результат
    protected StringBuilder winnerOrDraw;

    // Для временного хранения основных данных
    // получаемых по ходу игры, для дальнейшей извлечения и записи в xml-файл
    ArrayList<String> allData;

    // Используем в формировании имени файла
    Date date;
    DateFormat dateFormat = new SimpleDateFormat("_dd_MM_yyyy(HHmmss)");

    protected String firstPartOfFile = "play";
    protected String pl1Name;
    protected String pl2Name;
    protected String currentNewRecordedFile;

    public abstract void writeWinnerOrDraw(String winner);

    public abstract void readXMLFile(String fileName);

    public ArrayList<String> getPlayerList() {
        return playerList;
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

    // В allData сохраняем имена играков
    public void writePlayers(String name1, String name2) {
        allData = new ArrayList<>();
        pl1Name = name1;
        pl2Name = name2;
        allData.add(name1);
        allData.add(name2);
    }

    // В allData сохранили ходы играков
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
