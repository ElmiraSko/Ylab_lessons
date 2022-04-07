package com.erasko.service.impl;

import com.erasko.exceptions.NotFoundException;
import com.erasko.model.Game;
import com.erasko.repository.GameRepository;
import com.erasko.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImp implements GameService {

    private final GameRepository gameRepository;

    @Autowired
    public GameServiceImp(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public void saveGame(Game game) {
        gameRepository.save(game);
    }

    @Override
    public Game findById(long id) throws NotFoundException {
        if(gameRepository.findById(id).isPresent()) {
            return gameRepository.findById(id).get();
        } else {
            throw new NotFoundException("По данному запросу ничего не найдено.");
        }
    }

    @Override
    public List<Game> findAll() {
        return gameRepository.findAll();
    }
}

