package com.erasko.service;

import com.erasko.model.Game;
import com.erasko.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void saveGame(Game game) {
        gameRepository.save(game);
    }

    public Game findById(long id) {
        return gameRepository.findById(id).get();
    }

    public List<Game> findAll() {
        return gameRepository.findAll();
    }
}

