package com.erasko.lns2;

import java.util.*;

public class GameTest {

    // Список игроков
    static ArrayList<Player> players = new ArrayList<>();
    // Компаратор для сортировки списка игроков
    static RatingComparator ratingComp = new RatingComparator();

    // Счетчик сыгранных партий
    static int gameCount;

    // Флаг - игра продолжается или нет
    static boolean wantToPlayMore = true;

    // Проверяем, новый ли игрок
    public static boolean isNewPlayer(Player player) {
        if (players.size() > 0) {
            for (Player pl : players) {
                if (pl.equals(player)) {
                    return false;
                }
            }
        }
        return true;
    }
    // Если игрок есть в списке, возвращаем его
    public static Player checkPlayer(Player player) {
        if (players.size() > 0) {
            for (Player pl : players) {
                if (pl.equals(player)) {
                    return pl;
                }
            }
        }
        return player;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        GameView view = new GameView();
        GameField field = new GameField();
        GameController controller = new GameController(field, view);

        // цикл игры
        while (wantToPlayMore) {
            ++gameCount;
            int count = 1; // счетчик ходов, начинаем с хода номер 1
            controller.write("Начало игры " + gameCount);
            System.out.println("Начинаем новую игру №" + gameCount);

            System.out.println("Введите имя первого игрока:");
            Player player1 = checkPlayer(new Player(sc.nextLine()));
            System.out.println("Введите имя второго игрока:");
            Player player2 = checkPlayer(new Player(sc.nextLine()));

            // Если игрок новый, то добавлям в список игроков
            if(isNewPlayer(player1)) {
                players.add(player1);
            }
            if(isNewPlayer(player2)) {
                players.add(player2);
            }

            // Цикл новой партии.
            // Партия выполняется для 9 ничейных ходов или пока не получим выигрыш
            while (count <= GameField.GAME_STEP_NUMBERS && !GameField.gameOver) {
                System.out.println("Введите координаты хода ХY:");
                String coordinates = sc.nextLine();
                // Проверяем координаты по граничным значениям
                if (!controller.checkCoordinate(coordinates)) {
                    System.out.println("Координаты заданы некорректно!");
                    continue;
                }
                // координата по х и у:
                int x = Integer.parseInt(coordinates.substring(0,1)) - 1;
                int y = Integer.parseInt(coordinates.substring(1)) - 1;

                // Выпоняем ход в порядке очереди и записываем в файл
                if (count % 2 == 1) {
                    controller.gameMove(x, y, 1);
                    controller.write("Ход " + player1 + " " + (x+1) + (y+1));
                } else {
                    controller.gameMove(x, y, 2);
                    controller.write( "Ход " + player2 + " " + (x+1) + (y+1));
                }
                // Проверяем ячейки на выигрыш
                controller.checkSum();
                // Если есть выигрыш
                if (GameField.gameOver) {
                    Player winner;
                    if(count % 2 == 1) {
                        winner = player1;
                    } else {
                        winner = player2;
                    }
                    controller.write("Выиграл " + winner);
                    System.out.println("Выиграл " + winner);
                    winner.addWins(); // увеличили количество побед игрока
                }
                // Расспечатываем игровое поле
                controller.printField();
                count++;
            }
            // Если партия закончилась ничьей
            if (!GameField.gameOver) {
                controller.write("Ничья");
                System.out.println("Ничья");
            }

            players.sort(ratingComp);  // отсортировали игроков
            controller.write(players);  // записали рейтинг в файл
            System.out.println(players);

            System.out.println("Хотите сыграть еще раз? Введите ответ да или нет");
            // цикл получения ответа на вопрос
            while (true) {
                String answer = sc.nextLine();
                if(answer.toLowerCase().equals("да")) {
                    GameField.gameOver = false;
                    controller.clear(); // очищаем игровое поле
                    System.out.println("Отлично!");
                    break; // выходим из цикла опроса
                } else if(answer.toLowerCase().equals("нет")){
                    System.out.println("Спасибо, что были с нами. До встречи!");
                    wantToPlayMore = false;
                    controller.close(); // закрыли поток для записи в файл
                    break;
                } else {
                    System.out.println("Пожалуйста, ответьте точнее.");
                }
            }
        }
    }
}

