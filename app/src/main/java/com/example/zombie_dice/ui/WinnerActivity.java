package com.example.zombie_dice.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zombie_dice.R;

import java.util.Locale;

public class WinnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);

        TextView winnerTextView = findViewById(R.id.winner_textview);
        Button newGameButton = findViewById(R.id.new_game_button);
        Button exitButton = findViewById(R.id.exit_button);

        String winnerName = getIntent().getStringExtra("winnerName");
        winnerTextView.setText(String.format(Locale.getDefault(), "%s wins!", winnerName));

        newGameButton.setOnClickListener(v -> {
            Intent intent = new Intent(WinnerActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        exitButton.setOnClickListener(v -> {
            finishAffinity();
        });
    }
}
