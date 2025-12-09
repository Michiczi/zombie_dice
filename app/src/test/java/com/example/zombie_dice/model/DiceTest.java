package com.example.zombie_dice.model;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

public class DiceTest {

    @Test
    public void testGreenDiceDistribution() {
        Dice greenDice = new Dice(new Dice.DiceResult[]{Dice.DiceResult.BRAIN, Dice.DiceResult.BRAIN, Dice.DiceResult.BRAIN, Dice.DiceResult.RUN, Dice.DiceResult.RUN, Dice.DiceResult.SHOTGUN}, Dice.DiceColor.GREEN);
        Map<Dice.DiceResult, Integer> counts = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            Dice.DiceResult result = greenDice.roll();
            counts.put(result, counts.getOrDefault(result, 0) + 1);
        }

        // Expected: 3 Brains, 2 Runs, 1 Shotgun (50%, 33.3%, 16.7%)
        assertTrue(counts.get(Dice.DiceResult.BRAIN) > 400);
        assertTrue(counts.get(Dice.DiceResult.RUN) > 250);
        assertTrue(counts.get(Dice.DiceResult.SHOTGUN) > 100);
    }

    @Test
    public void testYellowDiceDistribution() {
        Dice yellowDice = new Dice(new Dice.DiceResult[]{Dice.DiceResult.BRAIN, Dice.DiceResult.BRAIN, Dice.DiceResult.RUN, Dice.DiceResult.RUN, Dice.DiceResult.SHOTGUN, Dice.DiceResult.SHOTGUN}, Dice.DiceColor.YELLOW);
        Map<Dice.DiceResult, Integer> counts = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            Dice.DiceResult result = yellowDice.roll();
            counts.put(result, counts.getOrDefault(result, 0) + 1);
        }

        // Expected: 2 Brains, 2 Runs, 2 Shotguns (33.3% each)
        assertTrue(counts.get(Dice.DiceResult.BRAIN) > 250);
        assertTrue(counts.get(Dice.DiceResult.RUN) > 250);
        assertTrue(counts.get(Dice.DiceResult.SHOTGUN) > 250);
    }

    @Test
    public void testRedDiceDistribution() {
        Dice redDice = new Dice(new Dice.DiceResult[]{Dice.DiceResult.BRAIN, Dice.DiceResult.RUN, Dice.DiceResult.RUN, Dice.DiceResult.SHOTGUN, Dice.DiceResult.SHOTGUN, Dice.DiceResult.SHOTGUN}, Dice.DiceColor.RED);
        Map<Dice.DiceResult, Integer> counts = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            Dice.DiceResult result = redDice.roll();
            counts.put(result, counts.getOrDefault(result, 0) + 1);
        }

        // Expected: 1 Brain, 2 Runs, 3 Shotguns (16.7%, 33.3%, 50%)
        assertTrue(counts.get(Dice.DiceResult.BRAIN) > 100);
        assertTrue(counts.get(Dice.DiceResult.RUN) > 250);
        assertTrue(counts.get(Dice.DiceResult.SHOTGUN) > 400);
    }
}
