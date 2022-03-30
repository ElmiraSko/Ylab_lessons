package com.erasko.service;

import com.erasko.model.CurrentPlayers;
import com.erasko.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrentPlayersService {

    // Пока на прямую работаем с моделью CurrentPlayers
    CurrentPlayers currentPlayers;

    public CurrentPlayersService() {
    }

    @Autowired
    public CurrentPlayersService(CurrentPlayers currentPlayers) {
        this.currentPlayers = currentPlayers;
    }

    public Player[] getCurrentPlayers() {
        Player[] players = new Player[2];
        players[0] = currentPlayers.getPlayer1();
        players[1] = currentPlayers.getPlayer2();
        return players;
    }

    public void setCurrentPlayers(Player pl1, Player pl2) {
        currentPlayers.setPlayer1(pl1);
        currentPlayers.setPlayer2(pl2);
        System.out.println(currentPlayers);
    }

    public void setLastPlayer(Player player) {
        currentPlayers.setLastWentPlayer(player);
    }

    public Player getLastPlayer() {
        return currentPlayers.getLastWentPlayer();
    }

    public void clear() {
        currentPlayers.setPlayer1(null);
        currentPlayers.setPlayer2(null);
        currentPlayers.setLastWentPlayer(null);
    }

    // Возвращаем игрока по его id
    public Player getPlayerById(String playerId) {
        int id = Integer.parseInt(playerId);
        Player player = currentPlayers.getPlayer1();
        if (player.getId() != id) {
            player = currentPlayers.getPlayer2();
        }
        return player;
    }
}
