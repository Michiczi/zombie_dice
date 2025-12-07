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
        this.diceCup = diceCup;
        this.brains = 0;
        this.shotguns = 0;
        this.runDices = new ArrayList<>();
    }

    public List<Dice.DiceResult> rollDice() {
        List<Dice.DiceResult> results = new ArrayList<>();
        List<Dice> dicesToRoll = new ArrayList<>(runDices);
        runDices.clear();

        while (dicesToRoll.size() < 3) {
            dicesToRoll.add(diceCup.get(new Random().nextInt(diceCup.size())));
        }

        for (Dice dice : dicesToRoll) {
            Dice.DiceResult result = dice.roll();
            results.add(result);
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
