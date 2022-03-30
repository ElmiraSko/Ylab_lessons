package com.erasko.utils;

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

    // game_files - папка в корне проекта, для хранения файлов
    protected String firstPartOfFile = "game_files\\play";

    protected String currentNewRecordedFile;
    /**
     * добавила поле gameIsRecorded (при
     * выполнении урока 5, в коде урока 4 его нет).
     * Необходимо проверять, была ли запись конкретной игры.
     * Изначально, игра не записана, gameIsRecorded = false.
     * Переменная используется в методе MainController-a
     */
    protected boolean gameIsRecorded = false;

    protected abstract void saveDataInFile();

    public abstract void readFile(String fileName);

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

    public boolean gameIsRecorded() {
        return gameIsRecorded;
    }

    public void setGameIsRecorded(boolean gameIsRecorded) {
        this.gameIsRecorded = gameIsRecorded;
    }

    public void setAllData(ArrayList<String> allData) {
        this.allData = allData;
    }

    // В allData сохраняем имена играков, (для записи в файл)
    public void writePlayers(String name1, String name2) {
        allData = new ArrayList<>();
        allData.add(name1);
        allData.add(name2);
        System.out.println("Записали играков:");
        System.out.println(allData);
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
        System.out.println("Записали шаг:");
        System.out.println(allData);
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
        System.out.println("Записали результат:");
        System.out.println(allData);

        saveDataInFile();
        gameIsRecorded = true; // игра была записана
    }

    // Служебный метод, создает кадры состояния игрового поля (при чтении из файла)
    protected void writeCoordsInHelpField(String coordinates, int playerId) {
        String coords = convertCoords(coordinates);
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

    private String convertCoords(String coords) {
        switch (coords.trim().length()){
            case 1: { return convertSingle(coords); }
            case 2: {}
            case 3: { return convertTriple(coords); }
            default: return coords;
        }
    }

    private String convertSingle(String coords) {
        String[] myCoords = {"11", "12", "13", "21", "22", "23", "31", "32", "33"};
        return myCoords[Integer.parseInt(coords)-1];
    }

    private String convertTriple(String coords) {
        String getCoords = coords.trim();
        return coords.charAt(0) + coords.substring(getCoords.length()-1);
    }
}
