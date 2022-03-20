package com.erasko.lns2;

import com.erasko.lns2.loggers.DOMGameLogger;
import com.erasko.lns2.loggers.GameLogger;
import com.erasko.lns2.loggers.JSONGameLogger;
import com.erasko.lns2.loggers.StaxStreamGameLogger;

import java.io.File;
import java.util.*;

public class GameService {

    /** Можно воспользоватья одним из логеров:
     *  DOMGameLogger(), StaxStreamLogger() или JSONGameLogger()
     */
//    static GameLogger logger = new DOMGameLogger();
//    static GameLogger logger = new StaxStreamGameLogger();
    static JSONGameLogger logger = new JSONGameLogger();

    static Scanner sc = new Scanner(System.in);
    static GameView view = new GameView();
    static GameField field = new GameField();
    static GameController controller = new GameController(field, view);

    // Список игроков, которые учавствуют в игре
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

    // Проигрование указанной игры
    public static void playRecordedGame(String filePath) {
        logger.readFile(filePath); // читаем xml-файл и отображаем на консоль
        ArrayList<String> playerList = logger.getPlayerList();
        for (String player : playerList) {
            System.out.println(player);
        }
        ArrayList<int[][]> fieldArr = logger.getPlayerStepArray();
        for (int[][] stepField: fieldArr) {
            controller.printField(stepField);
        }
        System.out.println(logger.getWinnerOrDraw());
    }

    // Цикл получения положительного или отрицательного ответа
    public static boolean getPositiveAnswer() {
        boolean isPositive = false;
        while (true) {
            String answer = sc.nextLine();
            if(answer.toLowerCase().equals("да")) {
                isPositive = true;
                break;
            } else if(answer.toLowerCase().equals("нет")){
                break;
            } else {
                System.out.println("Пожалуйста, ответьте точнее.");
            }
        }
        return isPositive;
    }

    // Получение xml (json) файлов из корневого каталога
    public static ArrayList<String> getFiles(String regex) {
        ArrayList<String> filesList = new ArrayList<>();
        String userDirectory = new File("").getAbsolutePath();
        File dir = new File(userDirectory);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.getName().matches(regex))
                    filesList.add(f.getName());
            }
        }
        return filesList;
    }
    private static String getRegEx(GameLogger logger) {
        if (logger instanceof JSONGameLogger) {
            return ".+.json";
        }
        return ".+.xml";
    }

    public static void main(String[] args) {

        // цикл игры
        while (wantToPlayMore) {

            // Предлагаем проиграть игру
            System.out.println("Перед началом игры хотите проиграть какую-нибудь игру, да или нет?");
            if(getPositiveAnswer()) {
                System.out.println("Укажите путь к файлу (имя файла)");
                if(getFiles(getRegEx(logger)).size() > 0) {
                    System.out.println("Можно выбрать из представленных ниже:\n");
                    for (String existingFile : getFiles(getRegEx(logger))) {
                        System.out.println(existingFile);
                    }
                    System.out.println("--------------------------------");
                }
                String recordedFileName = sc.nextLine();
                if (new File(recordedFileName).exists()) {
                    playRecordedGame(recordedFileName);
                } else {
                    System.out.println("К сожалению, указанный файл не найден. Пожалуйста, уточните имя файла.");
                }
            } else {
                System.out.println("Хорошо. Тогда перейдем к игре.");
            }

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
            logger.writePlayers(player1.toString(), player2.toString());

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
                logger.writeStep(count, coordinates);

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
                    logger.writeWinnerOrDraw(winner.toString());
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
                logger.writeWinnerOrDraw(draw);
            }

            players.sort(ratingComp);  // отсортировали игроков
            controller.write(players);  // записали рейтинг в файл
            System.out.println(players); // вывели на консоль

            System.out.println("Игра была записана в файл, хотите проиграть эту игру?");
            if (getPositiveAnswer()) {
                String recordedFileName = logger.getCurrentNewRecordedFile();
                if (new File(recordedFileName).exists()) {
                    playRecordedGame(recordedFileName);
                } else {
                    System.out.println("К сожалению, указанный файл не найден. Пожалуйста, уточните имя файла.");
                }
            } else {
                System.out.println("Хорошо, тогда следующий вопрос.");
            }

            System.out.println("Хотите сыграть еще раз? Введите ответ да или нет");
            if (getPositiveAnswer()) {
                GameField.gameOver = false;
                controller.clear(); // очищаем игровое поле
                System.out.println("Отлично!");
            } else {
                System.out.println("Спасибо, что были с нами. До встречи!");
                wantToPlayMore = false;
                controller.close(); // закрыли поток для записи в файл
            }
        }
    }
}

