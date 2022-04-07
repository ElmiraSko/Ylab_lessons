package com.erasko.service;

import com.erasko.DTO.CurrentPlayerDto;
import com.erasko.model.CurrentPlayer;
import com.erasko.model.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerService {

    List<Player> findAll();
    void savePlayer(CurrentPlayerDto player);
    Optional<Player> findById(int id);
    Optional<Player> findByName(String name);
    int setWinsCount(CurrentPlayer player);

}
