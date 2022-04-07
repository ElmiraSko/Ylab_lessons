package com.erasko.service;

import com.erasko.exceptions.NotFoundException;
import com.erasko.model.Game;

import java.util.List;

public interface GameService {
    void saveGame(Game game);
    Game findById(long id) throws NotFoundException;
    List<Game> findAll();
}
