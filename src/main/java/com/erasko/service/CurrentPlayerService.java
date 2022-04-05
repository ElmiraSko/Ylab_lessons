package com.erasko.service;

import com.erasko.DTO.CurrentPlayerDto;
import com.erasko.model.CurrentPlayer;
import com.erasko.model.Player;
import com.erasko.repository.CurrentPlayerRepository;
import com.erasko.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrentPlayerService {

    private final CurrentPlayerRepository currentPlayerRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public CurrentPlayerService(CurrentPlayerRepository currentPlayerRepository,
                                PlayerRepository playerRepository) {
        this.currentPlayerRepository = currentPlayerRepository;
        this.playerRepository = playerRepository;
    }

    public List<CurrentPlayer> findAll() {
        return currentPlayerRepository.findAll();
    }

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

    public CurrentPlayer findByGameId(int id) {
        return currentPlayerRepository.findByGameId(id);
    }

    public void removeAllCurrentPlayers() {
        currentPlayerRepository.deleteAll();
    }

    public void clear() {
        currentPlayerRepository.deleteAll();
    }
}

