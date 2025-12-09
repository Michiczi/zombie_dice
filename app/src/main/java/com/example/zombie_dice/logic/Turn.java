package com.example.zombie_dice.logic;

import com.example.zombie_dice.model.Dice;
import com.example.zombie_dice.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Turn {

    private final Player player;
    private final List<Dice> diceCup;
    private int brains;
    private int shotguns;
    private final List<Dice> runDices;

    public Turn(Player player, List<Dice> diceCup) {
        this.player = player;
        this.diceCup = new ArrayList<>(diceCup); // Create a copy to not modify the original
        this.brains = 0;
        this.shotguns = 0;
        this.runDices = new ArrayList<>();
    }

    public List<RollResult> rollDice() {
        List<RollResult> results = new ArrayList<>();
        List<Dice> dicesToRoll = new ArrayList<>(runDices);
        runDices.clear();

        while (dicesToRoll.size() < 3) {
            if (diceCup.isEmpty()) {
                // As per rules, if not enough dice, re-use the eaten brains
                // This part is simplified and we assume there are always enough dice.
                break;
            }
            dicesToRoll.add(diceCup.remove(new Random().nextInt(diceCup.size())));
        }

        for (Dice dice : dicesToRoll) {
            Dice.DiceResult result = dice.roll();
            results.add(new RollResult(dice, result));
            switch (result) {
                case BRAIN:
                    brains++;
                    break;
                case SHOTGUN:
                    shotguns++;
                    break;
                case RUN:
                    runDices.add(dice);
                    break;
            }
        }

        return results;
    }

    public void endTurn() {
        if (shotguns < 3) {
            player.addScore(brains);
        }
    }

    public boolean isOver() {
        return shotguns >= 3;
    }

    public int getBrains() {
        return brains;
    }

    public int getShotguns() {
        return shotguns;
    }
}