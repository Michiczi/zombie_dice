package com.example.zombie_dice.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zombie_dice.R;
import com.google.android.material.button.MaterialButton;

public class PreGameActivity extends AppCompatActivity {

    private TextView numPlayersTextView;
    private MaterialButton incrementButton;
    private MaterialButton decrementButton;
    private int numPlayers = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_game);

        numPlayersTextView = findViewById(R.id.num_players_textview);
        incrementButton = findViewById(R.id.increment_button);
        decrementButton = findViewById(R.id.decrement_button);
        Button startGameButton = findViewById(R.id.start_game_button);
        Button backButton = findViewById(R.id.back_button);

        updatePlayerCountView();

        incrementButton.setOnClickListener(v -> {
            if (numPlayers < 8) {
                numPlayers++;
                updatePlayerCountView();
            }
        });

        decrementButton.setOnClickListener(v -> {
            if (numPlayers > 2) {
                numPlayers--;
                updatePlayerCountView();
            }
        });

        startGameButton.setOnClickListener(v -> {
            Intent intent = new Intent(PreGameActivity.this, GameActivity.class);
            intent.putExtra("numberOfPlayers", numPlayers);
            startActivity(intent);
            finish();
        });

        backButton.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void updatePlayerCountView() {
        numPlayersTextView.setText(String.valueOf(numPlayers));
        decrementButton.setEnabled(numPlayers > 2);
        incrementButton.setEnabled(numPlayers < 8);
    }
}