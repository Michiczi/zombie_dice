package com.example.zombie_dice.model;

import java.util.Random;

public class Dice {

    public enum DiceResult {
        BRAIN,
        SHOTGUN,
        RUN
    }

    private final DiceResult[] sides;

    public Dice(DiceResult[] sides) {
        this.sides = sides;
    }

    public DiceResult roll() {
        return sides[new Random().nextInt(sides.length)];
    }
}
