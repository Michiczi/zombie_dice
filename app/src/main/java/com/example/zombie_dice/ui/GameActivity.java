package com.example.zombie_dice.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.zombie_dice.R;
import com.example.zombie_dice.logic.Game;
import com.example.zombie_dice.logic.RollResult;
import com.example.zombie_dice.logic.Turn;
import com.example.zombie_dice.model.Dice;
import com.example.zombie_dice.storage.Storage;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GameActivity extends AppCompatActivity {

    private Game game;
    private Turn currentTurn;
    private Storage storage;
    private Vibrator vibrator;

    private TextView playerTurnTextView;
    private TextView playerScoreTextView;
    private ImageView dice1ImageView, dice2ImageView, dice3ImageView;
    private TextView turnBrainsTextView, turnShotsTextView, bustedTextView;
    private MaterialButton rollButton, endTurnButton, exitButton;

    private boolean isRolling = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        storage = new Storage(this);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Initialize views from the new layout
        playerTurnTextView = findViewById(R.id.player_turn_textview);
        playerScoreTextView = findViewById(R.id.player_score_textview);
        dice1ImageView = findViewById(R.id.dice1_imageview);
        dice2ImageView = findViewById(R.id.dice2_imageview);
        dice3ImageView = findViewById(R.id.dice3_imageview);
        turnBrainsTextView = findViewById(R.id.turn_brains_textview);
        turnShotsTextView = findViewById(R.id.turn_shots_textview);
        bustedTextView = findViewById(R.id.busted_textview);
        rollButton = findViewById(R.id.roll_button);
        endTurnButton = findViewById(R.id.end_turn_button);
        exitButton = findViewById(R.id.exit_button);

        // Game setup
        game = storage.loadGame();
        if (game == null) {
            int numberOfPlayers = getIntent().getIntExtra("numberOfPlayers", 2);
            List<String> playerNames = new ArrayList<>();
            for (int i = 1; i <= numberOfPlayers; i++) {
                playerNames.add("Player " + i);
            }
            game = new Game(playerNames);
        }

        startNewTurn();

        rollButton.setOnClickListener(v -> rollDice());
        endTurnButton.setOnClickListener(v -> endTurn(false));
        exitButton.setOnClickListener(v -> {
            Intent intent = new Intent(GameActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void rollDice() {
        if (isRolling) return;

        isRolling = true;
        rollButton.setText("Rzucanie...");
        updateButtonStates();

        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        dice1ImageView.startAnimation(shake);
        dice2ImageView.startAnimation(shake);
        dice3ImageView.startAnimation(shake);

        new Handler().postDelayed(() -> {
            List<RollResult> results = currentTurn.rollDice();
            updateDiceImages(results);
            updateTurnStatus();
            updateUI();

            for (RollResult result : results) {
                if (result.getResult() == Dice.DiceResult.SHOTGUN) {
                    vibrate(50);
                }
            }

            isRolling = false;
            rollButton.setText("Rzuć kośćmi");

            if (currentTurn.isOver()) {
                bustedTextView.setVisibility(View.VISIBLE);
                vibrate(200);
                updateButtonStates();
                new Handler().postDelayed(() -> endTurn(true), 1500);
            } else {
                updateButtonStates();
            }
        }, 600);
    }

    private void startNewTurn() {
        currentTurn = new Turn(game.getCurrentPlayer(), game.getDiceCup());
        bustedTextView.setVisibility(View.GONE);
        rollDice(); // Automatically roll for the new player
    }

    private void endTurn(boolean isBust) {
        if (!isBust) {
            currentTurn.endTurn();
            if (game.getWinner() != null) {
                vibrate(500);
                storage.clearGame();
                Intent intent = new Intent(GameActivity.this, WinnerActivity.class);
                intent.putExtra("winnerId", game.getWinner().getId());
                intent.putExtra("totalBrains", game.getWinner().getScore());
                startActivity(intent);
                finish();
                return;
            }
        }

        game.nextPlayer();
        startNewTurn();
    }

    private void updateUI() {
        playerTurnTextView.setText(String.format(Locale.getDefault(), "Tura gracza: Gracz %d", game.getCurrentPlayer().getId()));
        playerScoreTextView.setText(String.format(Locale.getDefault(), "Zdobyte mózgi: %d/%d", game.getCurrentPlayer().getScore(), Game.WINNING_SCORE));
        updateButtonStates();
    }

    private void updateDiceImages(List<RollResult> results) {
        ImageView[] diceViews = {dice1ImageView, dice2ImageView, dice3ImageView};
        for (int i = 0; i < diceViews.length; i++) {
            if (i < results.size()) {
                RollResult result = results.get(i);
                int resourceId;
                switch (result.getResult()) {
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

                int colorRes;
                switch (result.getDice().getColor()) {
                    case GREEN:
                        colorRes = R.color.zd_green_500;
                        break;
                    case YELLOW:
                        colorRes = R.color.zd_yellow_500;
                        break;
                    case RED:
                        colorRes = R.color.zd_red_400;
                        break;
                    default:
                        colorRes = R.color.zd_gray_700;
                        break;
                }
                diceViews[i].setColorFilter(ContextCompat.getColor(this, colorRes), android.graphics.PorterDuff.Mode.SRC_IN);

                diceViews[i].setVisibility(View.VISIBLE);
            } else {
                diceViews[i].setVisibility(View.INVISIBLE);
            }
        }
    }


    private void updateTurnStatus() {
        turnBrainsTextView.setText(String.valueOf(currentTurn.getBrains()));
        turnShotsTextView.setText(String.valueOf(currentTurn.getShotguns()));
    }

    private void updateButtonStates() {
        boolean isBusted = currentTurn.isOver();
        rollButton.setEnabled(!isRolling && !isBusted);
        endTurnButton.setEnabled(!isRolling && !isBusted && currentTurn.getBrains() > 0);
    }

    private void vibrate(long milliseconds) {
        if (vibrator != null && vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(milliseconds);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        storage.saveGame(game);
    }
}
