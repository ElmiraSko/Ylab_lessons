package com.erasko.service.impl;
import com.erasko.DTO.CurrentPlayerDto;
import com.erasko.model.CurrentPlayer;
import com.erasko.model.Player;
import com.erasko.repository.PlayerRepository;
import com.erasko.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImp implements PlayerService {

    PlayerRepository playerRepository;
    // если еще не увеличивали количество побед игрока
    boolean isIncreasedNumberWins = false;

    @Autowired
    public PlayerServiceImp(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    @Override
    public void savePlayer(CurrentPlayerDto player) {
        // получили имя
        String name = player.getName();
        // проверили, существует ли игрок с таким именем
        if(!playerRepository.existsByName(name)) {
            Player player1 = new Player();
            player1.setName(name);
            playerRepository.save(player1);
        }
    }

    @Override
    public Optional<Player> findById(int id) {
        return playerRepository.findById(id);
    }

    @Override
    public Optional<Player> findByName(String name) {
        return playerRepository.findByName(name);
    }

    public boolean isIncreasedNumberWins() {
        return isIncreasedNumberWins;
    }

    public void setIncreasedNumberWins(boolean increasedNumberWins) {
        isIncreasedNumberWins = increasedNumberWins;
    }

    // увеличиваем число побед игрока
    @Override
    public int setWinsCount(CurrentPlayer player) {
        Player pl = playerRepository.findByName(player.getName()).get();
        pl.setWinsCount();
        Player winnerPlayer = playerRepository.save(pl);
        return winnerPlayer.getWinsCount();
    }
}
