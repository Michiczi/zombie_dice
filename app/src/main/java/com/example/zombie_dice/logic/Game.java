package com.example.zombie_dice.logic;

import com.example.zombie_dice.model.Dice;
import com.example.zombie_dice.model.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

    private final List<Player> players;
    private int currentPlayerIndex;
    private final List<Dice> diceCup;
    private static final int WINNING_SCORE = 13;

    public Game(List<String> playerNames) {
        players = new ArrayList<>();
        for (String name : playerNames) {
            players.add(new Player(name));
        }
        currentPlayerIndex = 0;
        diceCup = new ArrayList<>();
        initializeDiceCup();
    }

    private void initializeDiceCup() {
        // 6 green, 4 yellow, 3 red
        for (int i = 0; i < 6; i++) {
            diceCup.add(new Dice(new Dice.DiceResult[]{Dice.DiceResult.BRAIN, Dice.DiceResult.BRAIN, Dice.DiceResult.BRAIN, Dice.DiceResult.RUN, Dice.DiceResult.RUN, Dice.DiceResult.SHOTGUN}));
        }
        for (int i = 0; i < 4; i++) {
            diceCup.add(new Dice(new Dice.DiceResult[]{Dice.DiceResult.BRAIN, Dice.DiceResult.BRAIN, Dice.DiceResult.RUN, Dice.DiceResult.RUN, Dice.DiceResult.SHOTGUN, Dice.DiceResult.SHOTGUN}));
        }
        for (int i = 0; i < 3; i++) {
            diceCup.add(new Dice(new Dice.DiceResult[]{Dice.DiceResult.BRAIN, Dice.DiceResult.RUN, Dice.DiceResult.RUN, Dice.DiceResult.SHOTGUN, Dice.DiceResult.SHOTGUN, Dice.DiceResult.SHOTGUN}));
        }
        Collections.shuffle(diceCup);
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public Player getWinner() {
        for (Player player : players) {
            if (player.getScore() >= WINNING_SCORE) {
                return player;
            }
        }
        return null;
    }

    public List<Dice> getDiceCup() {
        return diceCup;
    }
}
