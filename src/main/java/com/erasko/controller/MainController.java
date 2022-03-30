package com.erasko.controller;

import com.erasko.DTO.FileDataDTO;
import com.erasko.DTO.GameDTO;
import com.erasko.model.*;
import com.erasko.service.*;
import com.erasko.utils.JSONStreamingAPIGameLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(("/gameplay"))
public class MainController {

    JSONStreamingAPIGameLogger logger = new JSONStreamingAPIGameLogger();

    PlayerServiceImp playerService;
    FieldServiceImp fieldService;
    CurrentPlayersService currentPlayersService;
    StepServiceImp stepService;

    @Autowired
    public MainController(PlayerServiceImp playerService,
                          FieldServiceImp fieldService,
                          CurrentPlayersService currentPlayersService,
                          StepServiceImp stepService) {
        this.playerService = playerService;
        this.fieldService = fieldService;
        this.currentPlayersService = currentPlayersService;
        this.stepService = stepService;
    }

    // Отдаем текущее состояние игры
    @GetMapping("/get")
    public GameDTO getField() {
        // Формируем DTO
        GameDTO gameDTO = new GameDTO();
        gameDTO.setCurrentPlayers(currentPlayersService.getCurrentPlayers());
        gameDTO.setField(fieldService.getField());
        Player curPlayer = currentPlayersService.getLastPlayer();
        gameDTO.setLastWentPlayer(curPlayer);

        if (stepService.getStepsSize() > 0) {
            // установили номер хода
            gameDTO.setStepCount(stepService.getLastStep().getNum());
        }
        // если есть победитель
        if(fieldService.isGameOver()) {
            if (!playerService.isIncreasedNumberWins()) {
                // увеличиваем число побед игрока
                playerService.setWinsCount(curPlayer);
                playerService.setIncreasedNumberWins(true);
            }

            // записываем имя игрока в результат gameDTO
            gameDTO.setResult(new Result(curPlayer.getName()));

            // если игра еще не была записана logger-ром, то записываем
            if(!logger.gameIsRecorded()) {
                logger.writeWinnerOrDraw(curPlayer.getName());
            }
        } else { // если все клетки проставлены, ничья
            if(stepService.getStepsSize() == fieldService.getStepsCount()) {
            gameDTO.setResult(new Result("Draw!"));
                // если игра еще не была записана logger-ром, то записываем
                if(!logger.gameIsRecorded()) {
                    logger.writeWinnerOrDraw("Draw!");
                }
            }
        }
        return gameDTO;
    }

    // Состояние новой игры
    @GetMapping("/getNewGame")
    public GameDTO getNewGameField() {
        System.out.println("Запрос на новую игру");
        // Все хранилища почистили для новой партии
        fieldService.clear();
        stepService.clear();
        currentPlayersService.clear();
        logger.setAllData(null);
        logger.setGameIsRecorded(false);
        playerService.setIncreasedNumberWins(false);

        // Формируем DTO
        GameDTO gameDTO = new GameDTO();
        gameDTO.setCurrentPlayers(currentPlayersService.getCurrentPlayers());
        gameDTO.setField(fieldService.getField());
        Player curPlayer = currentPlayersService.getLastPlayer();
        gameDTO.setLastWentPlayer(curPlayer);
        gameDTO.setStepCount(0);
        gameDTO.setResult(new Result(""));
        return gameDTO;
    }

    /**
     * Метод получает игроков и записывает их в коллекции
     * @param players
     * возвращаем статус
     */
    @PostMapping("/addPlayers")
    public ResponseEntity<HttpStatus> setPlayers(@RequestBody CurrentPlayers players) {

        Player pl1 = players.getPlayer1();
        Player pl2 = players.getPlayer2();
        // записали имена игроков в логер
        logger.writePlayers(pl1.getName(), pl2.getName());
        // внесли игроков в "общий список играков"
        playerService.addPlayer(pl1);
        playerService.addPlayer(pl2);
        // из общего списка достали нужных игроков
        Player cpl1 = playerService.checkPlayer(pl1);
        Player cpl2 = playerService.checkPlayer(pl2);
        // записали их "список текущих"
        currentPlayersService.setCurrentPlayers(cpl1, cpl2);
        currentPlayersService.setLastPlayer(cpl1); // тот кто ходит первым

        return status(HttpStatus.OK);
    }

    // Получаем ход игрока
    @PostMapping("/steps")
    public ResponseEntity<Message> setStep(@RequestBody Step step) {
    // {"num":1,"playerId":"1","symbol":"X","coords":"22"}
        boolean isRightStep = fieldService.setStepOnField(step.getPlayerId(), step.getCoords());
        Message message = new Message("No");
        // если ход допустимый
        if (isRightStep) {
            // записали в логер очередной ход
            logger.writeStep(step.getNum(), step.getCoords());

            stepService.addStep(step);
            // достали из "списка игроков" нужного по его id
            Player pl = currentPlayersService.getPlayerById(step.getPlayerId());
            // в currentPlayers передали игрока ходившего последним
            currentPlayersService.setLastPlayer(pl);

            message = new Message("Yes");
        }
        return rightStep(message, HttpStatus.OK);
    }

    // Запрос на запись последней игры
    @GetMapping("/getFile")
    public FileDataDTO getLastFile() {
        logger.readFile(logger.getCurrentNewRecordedFile());

        FileDataDTO fileDataDTO = new FileDataDTO(
                logger.getPlayerList(),
                logger.getPlayerStepArray(),
                logger.getWinnerOrDraw());
        return fileDataDTO;
    }

    //=============================================
    public ResponseEntity<HttpStatus> status(HttpStatus status) {
        return ResponseEntity.status(status).build();
    }

    public ResponseEntity<Message> rightStep(Message message, HttpStatus status) {
        return ResponseEntity.status(status).body(message);
    }

}