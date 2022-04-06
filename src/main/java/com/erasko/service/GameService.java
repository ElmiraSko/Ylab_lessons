package com.erasko.service;

import com.erasko.exceptions.NotFoundException;
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

    public Game findById(long id) throws NotFoundException {
        if(gameRepository.findById(id).isPresent()) {
            return gameRepository.findById(id).get();
        } else {
            throw new NotFoundException("По данному запросу ничего не найдено.");
        }
    }

    public List<Game> findAll() {
        return gameRepository.findAll();
    }
}

