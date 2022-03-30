package com.erasko.model;

import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Field {
    // координата х - строка, координата у - столбец
    int[][] field = new int[3][3];

    public final int GAME_STEP_NUMBERS = 9; // количество значимых ходов в игре
    final int WINNING_X = 21; // выигрышная сумма для крестика (Х)
    final int WINNING_Y = 6; // выигрышная сумма для нолика (О)
    public boolean gameOver = false;

    public Field() {}

    public int[][] getField() {
        return field;
    }

    public void setField(int[][] field) {
        this.field = field;
    }

    // очищаем игровое поле для новой игры
    public void clear() {
        for (int[] cell : field) {
            Arrays.fill(cell, 0);
        }
    }

    // метод проверяет валидность координат
    public boolean checkCoordinate(String str){
        boolean valid = false;
        if(str.matches("\\d{2}")) {
            String[] coordsArray = str.split("");
            int x = Integer.parseInt(coordsArray[0]) - 1;
            int y = Integer.parseInt(coordsArray[1]) - 1;
            if ((x >= 0 && x < 3) && (y >= 0 && y < 3) && field[x][y] == 0) {
                valid = true;
            }
        }
        return valid;
    }

    // метод присваивает значения ячейкам игрового поля
    // из рассчета 1, 2, 3 - координаты начинаются c 1
    public void gameMove(int x, int y, int playerNumber) {
        if (playerNumber == 1) {
            field[x][y] = 7;
        }
        if (playerNumber == 2) {
            field[x][y] = 2;
        }
        // проверка
        checkSum();
    }


    // проверяем суммы ячеек на выигрыш (21 - для Х, 6 - для O)
    // и меняем флаг gameOver на true
    private void checkSum() {
        int sumX; // для суммы значений по строкам
        int sumY; // для суммы значений по столбцам
        for (int i = 0; i < field.length; i++) {
            sumX = 0; // для каждой новой итерации
            sumY = 0;  // сумма сбрасывается
            for (int j = 0; j < field[i].length; j++) {
                sumX = sumX + field[i][j];
                sumY = sumY + field[j][i];
            }
            // если по горизонтали набралась выигрышная сумма, то заканчиваем игру
            if (sumX == WINNING_X | sumX == WINNING_Y
                    | sumY == WINNING_X | sumY == WINNING_Y) {
                gameOver = true;
                break;
            }
        }
        // проверим сумму ячеек по диагоналям
        int sumD1 = 0; // для суммы
        int sumD2 = 0;
        for (int i = 0; i < field.length; i++) {
            sumD1 = sumD1 + field[i][i];
            sumD2 = sumD2 + field[i][2 - i];
            if (sumD1 == WINNING_X | sumD1 == WINNING_Y
                    | sumD2 == WINNING_X | sumD2 == WINNING_Y) {
                gameOver = true;
                break;
            }
        }
    }
}
