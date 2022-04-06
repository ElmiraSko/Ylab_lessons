package com.erasko.repository;

import com.erasko.model.CurrentPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Репозиторий для текущих играков
 */
@Repository
public class CurrentPlayerRepository {

    ArrayList<CurrentPlayer> currentPlayers;

    public CurrentPlayerRepository() {
    }
    @Autowired
    public CurrentPlayerRepository(ArrayList<CurrentPlayer> currentPlayers) {
        this.currentPlayers = currentPlayers;
    }

    public void save(CurrentPlayer player) {
        currentPlayers.add(player);
    }

    public CurrentPlayer findByGameId(int gameId) {
        if (currentPlayers.size() > 0) {
            for (CurrentPlayer pl : currentPlayers) {
                if (pl.getGameId() == gameId) {
                    return pl;
                }
            }
        }
        return null;
    }

    public List<CurrentPlayer> findAll() {
        return currentPlayers;
    }

    public void deleteAll() {
        currentPlayers.clear();
    }
}

