package com.erasko.controller;

import com.erasko.model.Player;
import com.erasko.repository.PlayerRepository;
import com.erasko.service.MainService;
import com.erasko.service.impl.PlayerServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MainControllerTest {

    MainService mainService;
    PlayerRepository playerRepository;

    @BeforeEach
    public void init() {
        playerRepository = mock(PlayerRepository.class);
        mainService = new MainService();
        mainService.setPlayerService(new PlayerServiceImp(playerRepository));
    }

    @Test
    public void testFindById() {
        Player expectedPlayer = new Player();
        expectedPlayer.setId(1);
        expectedPlayer.setName("Player1");
        expectedPlayer.setWinsCount();

        when(playerRepository.findById(eq(1)))
                .thenReturn(Optional.of(expectedPlayer));

        Optional<Player> player = playerRepository.findById(1);

        assertTrue(player.isPresent());
        assertEquals(expectedPlayer.getId(), player.get().getId());
        assertEquals(expectedPlayer.getName(), player.get().getName());
        assertEquals(expectedPlayer.getWinsCount(), player.get().getWinsCount());
    }
}
