package com.example.zombie_dice.model;

import java.util.Random;

public class Dice {

    public enum DiceResult {
        BRAIN,
        SHOTGUN,
        RUN
    }

    public enum DiceColor {
        GREEN,
        YELLOW,
        RED
    }

    private final DiceResult[] sides;
    private final DiceColor color;

    public Dice(DiceResult[] sides, DiceColor color) {
        this.sides = sides;
        this.color = color;
    }

    public DiceResult roll() {
        return sides[new Random().nextInt(sides.length)];
    }

    public DiceColor getColor() {
        return color;
    }
}