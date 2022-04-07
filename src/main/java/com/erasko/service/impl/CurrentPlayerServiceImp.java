package com.erasko.service.impl;

import com.erasko.DTO.CurrentPlayerDto;
import com.erasko.model.CurrentPlayer;
import com.erasko.model.Player;
import com.erasko.repository.CurrentPlayerRepository;
import com.erasko.repository.PlayerRepository;
import com.erasko.service.CurrentPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrentPlayerServiceImp implements CurrentPlayerService {

    private final CurrentPlayerRepository currentPlayerRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public CurrentPlayerServiceImp(CurrentPlayerRepository currentPlayerRepository,
                                   PlayerRepository playerRepository) {
        this.currentPlayerRepository = currentPlayerRepository;
        this.playerRepository = playerRepository;
    }

    public List<CurrentPlayer> findAll() {
        return currentPlayerRepository.findAll();
    }

    @Override
    public void saveCurrentPlayer(CurrentPlayerDto player) {
        // формируем текущего игрока
        CurrentPlayer crPlayer = new CurrentPlayer();
        // достали игрока из БД
        Player pl = playerRepository.findByName(player.getName()).get();
        crPlayer.setGameId(player.getId());
        crPlayer.setSymbol(player.getSymbol());
        crPlayer.setName(pl.getName());
        crPlayer.setWinsCount(pl.getWinsCount());
        // сохранили текущего игрока
        currentPlayerRepository.save(crPlayer);
    }

    @Override
    public CurrentPlayer findByGameId(int id) {
        return currentPlayerRepository.findByGameId(id);
    }

    @Override
    public void removeAllCurrentPlayers() {
        currentPlayerRepository.deleteAll();
    }

    @Override
    public void clear() {
        currentPlayerRepository.deleteAll();
    }
}

