package com.erasko.service;

import com.erasko.model.Player;

import java.io.Serializable;
import java.util.ArrayList;

public interface PlayerService extends Serializable {
    ArrayList<Player> getAllPlayer();
    Player getPlayerByName(String name);
    Player getPlayerById(String id);
    void addPlayer(Player player);

}
