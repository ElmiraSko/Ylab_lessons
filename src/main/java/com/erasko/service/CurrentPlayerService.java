package com.erasko.service;

import com.erasko.DTO.CurrentPlayerDto;
import com.erasko.model.CurrentPlayer;

public interface CurrentPlayerService {

    void saveCurrentPlayer(CurrentPlayerDto player);
    CurrentPlayer findByGameId(int id);
    void removeAllCurrentPlayers();
    void clear();
}
