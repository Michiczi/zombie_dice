package com.example.zombie_dice.logic;

import com.example.zombie_dice.model.Dice;

public class RollResult {
    private final Dice dice;
    private final Dice.DiceResult result;

    public RollResult(Dice dice, Dice.DiceResult result) {
        this.dice = dice;
        this.result = result;
    }

    public Dice getDice() {
        return dice;
    }

    public Dice.DiceResult getResult() {
        return result;
    }
}
