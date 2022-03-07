package com.erasko.lns2;

public class GameView {

    // вывод игрового поля в консоль
    public void printField(int[][] field) {
        for (int[] rowLine : field) {
            for (int cell : rowLine) {
                if (cell == 0) {
                    System.out.print("| - ");
                } else if (cell == 7) {
                    System.out.print("| X ");
                } else if (cell == 2) {
                    System.out.print("| O ");
                }
            }
            System.out.println("|");
        }
        System.out.println();
    }
}

