package com.erasko.repository;

import com.erasko.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class PlayerRepository {

    // Общий список игроков
    ArrayList<Player> players;

    @Autowired
    public PlayerRepository(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        players.add(player);
        System.out.println(players);
    }

    public int getSize() {
        return players.size();
    }

    // public Optional<Player> getByName(String name);
    public Player getByName(String name) {
        if (players.size() > 0) {
            for (Player pl : players) {
                if (pl.getName().equals(name)) {
                    return pl;
                }
            }
        }
        return null;
    }
}
