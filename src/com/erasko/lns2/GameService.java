package com.erasko.lns2;

import com.erasko.lns2.loggers.DOMGameLogger;
import com.erasko.lns2.loggers.GameLogger;
import com.erasko.lns2.loggers.StaxStreamLogger;

import java.util.*;

public class GameService {

    // Можно воспользоватья одним из логеров: DOMGameLogger() или StaxStreamLogger()
//    static GameLogger logger = new DOMGameLogger();
    static GameLogger logger = new StaxStreamLogger();

    // Список игроков
    static ArrayList<Player> players = new ArrayList<>();

    // Компаратор для сортировки списка игроков
    static RatingComparator ratingComp = new RatingComparator();

    // Счетчик сыгранных партий
    static int gameCount;

    // Флаг - игра продолжается или нет
    static boolean wantToPlayMore = true;

    static String draw = "Draw!";

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

            // Записываем игроков в лог
            logger.wrightPlayers(player1.toString(), player2.toString());

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
                // записываем шаги игры в лог
                logger.wrightStep(count, coordinates);

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

                    // Записываем результат игры - имя победителя
                    logger.wrightWinnerOrDraw(winner.toString());
                }
                // Расспечатываем игровое поле
                controller.printField();
                count++;
            }
            // Если партия закончилась ничьей
            if (!GameField.gameOver) {
                controller.write(draw);
                System.out.println(draw);
                // Записываем результат игры - Ничья!
                logger.wrightWinnerOrDraw(draw);
            }

            players.sort(ratingComp);  // отсортировали игроков
            controller.write(players);  // записали рейтинг в файл
            System.out.println(players); // вывели на консоль

            // Пока реализована возможность прочитать
            // последнюю сыгранную игру, Возможность выбрать предыдущий записанный файл еще не реализована
            System.out.println("Игра была записана в файл, хотите проиграть эту игру?");
            // цикл получения ответа на вопрос
            while (true) {
                String answer = sc.nextLine();
                if(answer.toLowerCase().equals("да")) {
                   logger.readXMLFile(); // читаем xml-файл и отображаем на консоль
                   ArrayList<String> playerList = logger.getPlayerList();
                    for (String player : playerList) {
                        System.out.println(player);
                    }
                   ArrayList<int[][]> fieldArr = logger.getPlayerStepArray();
                    for (int[][] stepField: fieldArr) {
                        controller.printField(stepField);
                    }
                    System.out.println( logger.getWinnerOrDraw());
                    break; // выходим из цикла опроса
                } else if(answer.toLowerCase().equals("нет")){
                    System.out.println("Хорошо.");
                    break;
                } else {
                    System.out.println("Пожалуйста, ответьте точнее.");
                }
            }
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

