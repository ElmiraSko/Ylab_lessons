package com.erasko.service;
import com.erasko.DTO.CurrentPlayerDto;
import com.erasko.model.CurrentPlayer;
import com.erasko.model.Player;
import com.erasko.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    PlayerRepository playerRepository;
    // если еще не увеличивали количество побед игрока
    boolean isIncreasedNumberWins = false;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

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

    public Optional<Player> findById(int id) {
        return playerRepository.findById(id);
    }

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
    public int setWinsCount(CurrentPlayer player) {
        Player pl = playerRepository.findByName(player.getName()).get();
        pl.setWinsCount();
        Player winnerPlayer = playerRepository.save(pl);
        return winnerPlayer.getWinsCount();
    }
}
