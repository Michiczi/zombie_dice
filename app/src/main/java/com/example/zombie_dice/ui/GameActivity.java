package com.example.zombie_dice.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zombie_dice.R;
import com.example.zombie_dice.logic.Game;
import com.example.zombie_dice.logic.Turn;
import com.example.zombie_dice.model.Dice;
import com.example.zombie_dice.storage.Storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GameActivity extends AppCompatActivity {

    private Game game;
    private Turn currentTurn;
    private Storage storage;
    private Vibrator vibrator;

    private TextView playerInfoTextView;
    private ImageView dice1ImageView, dice2ImageView, dice3ImageView;
    private TextView turnScoreTextView;
    private Button rollButton, endTurnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        storage = new Storage(this);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        playerInfoTextView = findViewById(R.id.player_info_textview);
        dice1ImageView = findViewById(R.id.dice1_imageview);
        dice2ImageView = findViewById(R.id.dice2_imageview);
        dice3ImageView = findViewById(R.id.dice3_imageview);
        turnScoreTextView = findViewById(R.id.turn_score_textview);
        rollButton = findViewById(R.id.roll_button);
        endTurnButton = findViewById(R.id.end_turn_button);

        game = storage.loadGame();
        if (game == null) {
            int numberOfPlayers = getIntent().getIntExtra("numberOfPlayers", 2); // Default to 2
            List<String> playerNames = new ArrayList<>();
            for (int i = 1; i <= numberOfPlayers; i++) {
                playerNames.add("Player " + i);
            }
            game = new Game(playerNames);
        }

        startNewTurn();

        rollButton.setOnClickListener(v -> {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            dice1ImageView.startAnimation(shake);
            dice2ImageView.startAnimation(shake);
            dice3ImageView.startAnimation(shake);

            new Handler().postDelayed(() -> {
                List<Dice.DiceResult> results = currentTurn.rollDice();
                updateDiceImages(results);
                updateTurnStatus();

                for (Dice.DiceResult result : results) {
                    if (result == Dice.DiceResult.SHOTGUN) {
                        vibrate(50);
                    }
                }

                if (currentTurn.isOver()) {
                    Toast.makeText(this, "3 shotguns! You lost your brains.", Toast.LENGTH_SHORT).show();
                    endTurn();
                }
            }, 500);
        });

        endTurnButton.setOnClickListener(v -> endTurn());
    }

    @Override
    protected void onPause() {
        super.onPause();
        storage.saveGame(game);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Game savedGame = storage.loadGame();
        if (savedGame != null) {
            game = savedGame;
            startNewTurn();
        }
    }

    private void startNewTurn() {
        currentTurn = new Turn(game.getCurrentPlayer(), game.getDiceCup());
        updateUI();
    }

    private void endTurn() {
        currentTurn.endTurn();
        if (game.getWinner() != null) {
            vibrate(500);
            storage.clearGame();
            Intent intent = new Intent(GameActivity.this, WinnerActivity.class);
            intent.putExtra("winnerName", game.getWinner().getName());
            startActivity(intent);
            finish();
        } else {
            game.nextPlayer();
            startNewTurn();
        }
    }

    private void updateUI() {
        playerInfoTextView.setText(String.format(Locale.getDefault(), "%s: %d", game.getCurrentPlayer().getName(), game.getCurrentPlayer().getScore()));
        updateTurnStatus();
        // Clear dice images
        dice1ImageView.setImageResource(android.R.color.transparent);
        dice2ImageView.setImageResource(android.R.color.transparent);
        dice3ImageView.setImageResource(android.R.color.transparent);
    }

    private void updateDiceImages(List<Dice.DiceResult> results) {
        ImageView[] diceViews = {dice1ImageView, dice2ImageView, dice3ImageView};
        for (int i = 0; i < results.size(); i++) {
            int resourceId;
            switch (results.get(i)) {
                case BRAIN:
                    resourceId = R.drawable.ic_brain;
                    break;
                case SHOTGUN:
                    resourceId = R.drawable.ic_shotgun;
                    break;
                case RUN:
                    resourceId = R.drawable.ic_run;
                    break;
                default:
                    resourceId = android.R.color.transparent;
                    break;
            }
            diceViews[i].setImageResource(resourceId);
        }
    }

    private void updateTurnStatus() {
        turnScoreTextView.setText(String.format(Locale.getDefault(), "Brains: %d, Shotguns: %d", currentTurn.getBrains(), currentTurn.getShotguns()));
    }

    private void vibrate(long milliseconds) {
        if (vibrator != null && vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                vibrator.vibrate(milliseconds);
            }
        }
    }
}
