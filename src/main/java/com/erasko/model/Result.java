package com.erasko.model;

import org.springframework.stereotype.Component;
import com.erasko.DTO.CurrentPlayerDto;

import java.io.Serializable;

@Component
public class Result implements Serializable {
    // Здесь еще нужно подумать
    String result;
    // игрок сделавший ход последним
    CurrentPlayerDto lastWentPlayer;

    public Result() {
    }

    public Result(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public CurrentPlayerDto getLastWentPlayer() {
        return lastWentPlayer;
    }

    public void setLastWentPlayer(CurrentPlayerDto lastWentPlayer) {
        this.lastWentPlayer = lastWentPlayer;
    }
}