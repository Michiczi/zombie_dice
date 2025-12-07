package com.example.zombie_dice.logic;

import com.example.zombie_dice.model.Player;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void testPlayerSwitching() {
        Game game = new Game(Arrays.asList("Player 1", "Player 2"));
        assertEquals("Player 1", game.getCurrentPlayer().getName());
        game.nextPlayer();
        assertEquals("Player 2", game.getCurrentPlayer().getName());
        game.nextPlayer();
        assertEquals("Player 1", game.getCurrentPlayer().getName());
    }

    @Test
    public void testWinningCondition() {
        Game game = new Game(Arrays.asList("Player 1", "Player 2"));
        Player player1 = game.getCurrentPlayer();
        assertNull(game.getWinner());
        player1.addScore(13);
        assertEquals(player1, game.getWinner());
    }
}
