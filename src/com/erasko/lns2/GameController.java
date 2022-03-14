package com.erasko.lns2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GameController {

    private final GameField field;
    private final GameView view;
    BufferedWriter buff;

    public GameController(GameField field, GameView view) {
        this.field = field;
        this.view = view;

        try  {
            buff = new BufferedWriter(new FileWriter("info.txt", true));
        } catch (IOException ex) {
            System.out.println("Ошибка при работе с файлом");
        }
    }


    // метод записывает информацию в файл
    public void write(String info) {
        try {
            buff.write(info);
            buff.write("\n");
            buff.flush();
        } catch (IOException ex) {
            System.out.println("Ошибка при записи в файл");
        }
    }

    // метод записывает рейтинг игроков в файл
    public void write(ArrayList<Player> rating) {
        try {
            buff.write("----------------\n");
            buff.write("Рейтинг\n");
            for (Player player : rating) {
                buff.write(player.getPlayerRating());
                buff.write("\n");
            }
            buff.write("----------------\n");
            buff.flush();
        } catch (IOException ex) {
            System.out.println("Ошибка при записи в файл");
        }

    }

    // закрываем поток ввода-вывода
    public void close() {
        try {
            if(buff!=null)
                buff.close();
        } catch (IOException e) {
            System.out.println("Ошибка при закрытии файла");
            e.printStackTrace();
        }
    }

    // метод присваивает значения ячейкам игрового поля
    public void gameMove(int x, int y, int playerNumber) {
        field.gameMove(x, y, playerNumber);
    }

    // очищаем игровое поле для новой игры
    public void clear() {
        field.clear();
    }

    // метод проверяет валидность координат
    public boolean checkCoordinate(String coords){
        return field.checkCoordinate(coords);
    }
    public void checkSum() {
        field.checkSum();
    }

    // метод печатает поле в консоль
    public void printField() {
        view.printField(field.getField());
    }

    // метод печатает поле в консоль
    public void printField(int[][] otherField) {
        view.printField(otherField);
    }
}

