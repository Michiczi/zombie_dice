package com.example.zombie_dice.logic;

import com.example.zombie_dice.model.Dice;
import com.example.zombie_dice.model.Player;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class TurnTest {

    @Test
    public void testTurnEndsAfter3Shotguns() {
        Player player = new Player(1, "Test Player");
        List<Dice> diceCup = new ArrayList<>();
        // All dice are shotguns
        for (int i = 0; i < 13; i++) {
            diceCup.add(new Dice(new Dice.DiceResult[]{Dice.DiceResult.SHOTGUN}, Dice.DiceColor.GREEN));
        }

        Turn turn = new Turn(player, diceCup);
        turn.rollDice();
        assertTrue(turn.isOver());
        turn.endTurn();
        assertEquals(0, player.getScore());
    }

    @Test
    public void testScoreIsSaved() {
        Player player = new Player(1, "Test Player");
        List<Dice> diceCup = new ArrayList<>();
        // All dice are brains
        for (int i = 0; i < 13; i++) {
            diceCup.add(new Dice(new Dice.DiceResult[]{Dice.DiceResult.BRAIN}, Dice.DiceColor.GREEN));
        }

        Turn turn = new Turn(player, diceCup);
        turn.rollDice();
        assertFalse(turn.isOver());
        assertEquals(3, turn.getBrains());
        turn.endTurn();
        assertEquals(3, player.getScore());
    }
}
