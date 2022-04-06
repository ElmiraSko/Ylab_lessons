package com.erasko.service;

import com.erasko.DTO.CurrPlayersDto;
import com.erasko.DTO.CurrentPlayerDto;
import com.erasko.DTO.FileDataDto;
import com.erasko.DTO.GameDto;
import com.erasko.exceptions.NotFoundException;
import com.erasko.model.*;
import com.erasko.utils.GameLogger;
import com.erasko.utils.JSONStreamingAPIGameLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainService {

    GameLogger logger = new JSONStreamingAPIGameLogger();

    PlayerService playerService;
    FieldService fieldService;
    CurrentPlayerService currentPlayerService;
    StepService stepService;
    Result result;
    GameService gameService;

    public MainService() {
    }

    @Autowired
    public MainService(PlayerService playerService,
                       CurrentPlayerService currentPlayerService,
                       StepService stepService,
                       FieldService fieldService,
                       Result result,
                       GameService gameService) {
        this.playerService = playerService;
        this.currentPlayerService = currentPlayerService;
        this.stepService = stepService;
        this.fieldService = fieldService;
        this.result = result;
        this.gameService = gameService;
    }

    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }

    public void savePlayers(CurrPlayersDto players) {
        CurrentPlayerDto pl1 = players.getPlayer1();
        CurrentPlayerDto pl2 = players.getPlayer2();
        // записали имена игроков в логер
        logger.writePlayers(pl1.getName(), pl2.getName());
        // внесли игроков в "общую таблицу игроков"
        playerService.savePlayer(pl1);
        playerService.savePlayer(pl2);

        // записали их в таблицу "текущих игроков"
        currentPlayerService.saveCurrentPlayer(pl1);
        currentPlayerService.saveCurrentPlayer(pl2);
        result.setResult("");
        result.setLastWentPlayer(pl1); // тот кто ходит первым
    }

    // Отдаем все игры (надо будет переделать, подумать)
    public List<Game> findAllGames() {
        return gameService.findAll();
    }

    // Отдаем текущее состояние игры
    public GameDto getField() {
        // формируем DTO
        GameDto gameDTO = new GameDto();
        gameDTO.setField(fieldService.getField());
        if (stepService.getStepsSize() > 0) {
            // установили номер хода
            gameDTO.setStepCount(stepService.getLastStep().getNum());
        }
        gameDTO.setCurrentPlayers(currPlayersFromDB());
        gameDTO.setResult(result);
        return gameDTO;
    }

    // Состояние новой игры
    public void getNewGameField() {
        System.out.println("Запрос на новую игру");
        // все хранилища почистили для новой партии
        fieldService.clear();
        stepService.clear();
        currentPlayerService.clear();
        result.setResult("");
        logger.setAllData(null);
        logger.setGameIsRecorded(false);
        playerService.setIncreasedNumberWins(false);
    }

    // Сохранияем ходы и проверяем на окончание игры
    public Message saveStep(Step step) {
        // {"num":1,"playerId":"1","symbol":"X","coords":"22"}
        Message message = new Message("No");
        String playerId = step.getPlayerId();
        boolean isRightStep = fieldService.setStepOnField(playerId, step.getCoords());

        // если ход допустимый
        if (isRightStep) {
            // записали в логер очередной ход
            logger.writeStep(step.getNum(), step.getCoords());
            // сохранили ход в БД
            stepService.addStep(step);
            // достали из "списка игроков" нужного по его id
            CurrentPlayer pl =
                    currentPlayerService.findByGameId(Integer.parseInt(playerId));
            // если есть победитель
            if(fieldService.isGameOver()) {
                if (!playerService.isIncreasedNumberWins()) {
                    // увеличиваем число побед игрока
                    int winnerCount = playerService.setWinsCount(pl);
                    pl.setWinsCount(winnerCount);
                    playerService.setIncreasedNumberWins(true);
                }
                // записываем имя игрока в результат
                result.setResult(pl.getName());
                // если игра еще не была записана logger-ром, то записываем
                if(!logger.gameIsRecorded()) {
                    logger.writeWinnerOrDraw(pl.getName());
                }
            } else { // если все клетки проставлены, ничья
                if(stepService.getStepsSize() == fieldService.getStepsCount()) {
                    result.setResult("Draw!");
                    // если игра еще не была записана logger-ром, то записываем
                    if(!logger.gameIsRecorded()) {
                        logger.writeWinnerOrDraw("Draw!");
                    }
                }
            }
            // в result сохранили игрока ходившего последним
            CurrentPlayerDto plDto = new CurrentPlayerDto(pl);
            result.setLastWentPlayer(plDto);
            message = new Message("Yes");
            // Проверка значения result, что бы записать игру в БД
            if(!result.getResult().equals("")) {
                saveGame();
            }
        }
        return message;
    }

    // Отдаем записанный файл
    public FileDataDto getLastFile() {
        logger.readFile(logger.getCurrentNewRecordedFile());
        return new FileDataDto(
                logger.getPlayerList(),
                logger.getPlayerStepArray(),
                logger.getWinnerOrDraw());
    }

    // Получить игру из БД по id
    public Game findGameById(long id) throws NotFoundException {
        return gameService.findById(id);
    }

    // Получить всех игроков
    public List<Player> findAll() {
        return playerService.findAll();
    }

    // Получить всех текущих игроков
    public List<CurrentPlayer> getAllCurrentPlayers() {
        return currentPlayerService.findAll();
    }

    // Метод сохраняет данные игры в таблицу БД
    private void saveGame() {
        Game game = new Game();
        List<CurrentPlayer> currentPlayers = currentPlayerService.findAll();
        List<Step> steps = stepService.findAllStep();
        game.setCurrentPlayers(currentPlayers);
        game.setSteps(steps);
        game.setResult(result);
        gameService.saveGame(game);
    }

    // Используем в методе, который отдает состояние игры
    private CurrentPlayerDto[] currPlayersFromDB() {
        // получили список текущих играков из БД
        List<CurrentPlayer> currentPlayerList = currentPlayerService.findAll();
        // создали массив CurrentPlayerDto
        CurrentPlayerDto[] currentPlayerArr = new CurrentPlayerDto[2];
        // сформировали CurrentPlayerDto первого игрока
        currentPlayerArr[0] = new CurrentPlayerDto(currentPlayerList.get(0));
        // сформировали CurrentPlayerDto второго игрока
        currentPlayerArr[1] = new CurrentPlayerDto(currentPlayerList.get(1));
        // вернули массив
        return  currentPlayerArr;
    }
}
