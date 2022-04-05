package com.erasko.controller;

import com.erasko.DTO.CurrPlayersDto;
import com.erasko.DTO.FileDataDto;
import com.erasko.DTO.GameDto;
import com.erasko.model.*;
import com.erasko.service.*;
import com.erasko.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gameplay")
public class MainController {

    MainService mainService;

    @Autowired
    public MainController(MainService mainService) {
        this.mainService = mainService;
    }


    @PostMapping("/addPlayers")
    public ResponseEntity<HttpStatus> setPlayers(@RequestBody CurrPlayersDto playersDto) {
        mainService.savePlayers(playersDto);
        return status(HttpStatus.OK);
    }

    // Отдаем текущее состояние игры
    @GetMapping("/get")
    public GameDto getField() {
        return mainService.getField();
    }

    // Состояние новой игры
    @GetMapping("/get-new-game")
    public void getNewGameField() {
        mainService.getNewGameField();
    }

    // Получаем ход игрока
    @PostMapping("/steps")
    public ResponseEntity<Message> setStep(@RequestBody Step step) {
        Message message = mainService.saveStep(step);
        return rightStep(message, HttpStatus.OK);
    }

    // Запрос на получение игры из БД
    @GetMapping("/get-game")
    public Game getGame() {
        return mainService.findGameById(1);
    }

    // Запрос на получение файла игры из файловой системы
    @GetMapping("/get-file")
    public FileDataDto getLastFile() {
        return mainService.getLastFile();
    }

    // Служебные методы
    public ResponseEntity<HttpStatus> status(HttpStatus status) {
        return ResponseEntity.status(status).build();
    }

    public ResponseEntity<Message> rightStep(Message message, HttpStatus status) {
        return ResponseEntity.status(status).body(message);
    }
}