package com.erasko.service;

import com.erasko.model.Player;
import com.erasko.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PlayerServiceImp implements PlayerService {

    PlayerRepository playerRepository;
    // еще не увеличивали
    boolean isIncreasedNumberWins = false;

    @Autowired
    public PlayerServiceImp(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public ArrayList<Player> getAllPlayer() {
        return playerRepository.getPlayers();
    }

    @Override
    public Player getPlayerByName(String name) {
        return playerRepository.getByName(name);
    }

    // Добавление игрока в общий список игроков
    @Override
    public void addPlayer(Player player) {
        if (isNewPlayer(player)) {
            playerRepository.addPlayer(player);
        }
    }

    // Проверяем, новый ли игрок
    private boolean isNewPlayer(Player player) {
        if (playerRepository.getSize() > 0) {
            for (Player pl : playerRepository.getPlayers()) {
                if (pl.equals(player)) {
                    return false;
                }
            }
        }
        return true;
    }

    // Возвращаем игрока по его id
    @Override
    public Player getPlayerById(String playerId) {
        int id = Integer.parseInt(playerId);
        if (playerRepository.getSize() > 0) {
            for (Player pl : playerRepository.getPlayers()) {
                if (pl.getId() == id) {
                    return pl;
                }
            }
        }
        return null;
    }

    // Если игрок есть в списке, возвращаем его
    public Player checkPlayer(Player player) {
        if (playerRepository.getSize() > 0) {
            for (Player pl : playerRepository.getPlayers()) {
                if (pl.equals(player)) {
                    return pl;
                }
            }
        }
        return player;
    }

    // увеличиваем число побед игрока
    public void setWinsCount(Player player) {
        if (playerRepository.getSize() > 0) {
            for (Player pl : playerRepository.getPlayers()) {
                if (pl.equals(player)) {
                    pl.setWinsCount();
                }
            }
        }
    }

    public boolean isIncreasedNumberWins() {
        return isIncreasedNumberWins;
    }

    public void setIncreasedNumberWins(boolean increasedNumberWins) {
        isIncreasedNumberWins = increasedNumberWins;
    }
}
