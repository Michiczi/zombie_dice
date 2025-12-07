package com.example.zombie_dice.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.zombie_dice.logic.Game;
import com.google.gson.Gson;

public class Storage {

    private static final String PREFS_NAME = "ZombieDicePrefs";
    private static final String GAME_KEY = "game";

    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    public Storage(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveGame(Game game) {
        String json = gson.toJson(game);
        sharedPreferences.edit().putString(GAME_KEY, json).apply();
    }

    public Game loadGame() {
        String json = sharedPreferences.getString(GAME_KEY, null);
        if (json == null) {
            return null;
        }
        return gson.fromJson(json, Game.class);
    }

    public void clearGame() {
        sharedPreferences.edit().remove(GAME_KEY).apply();
    }
}
