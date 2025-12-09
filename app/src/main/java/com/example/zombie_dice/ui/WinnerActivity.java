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

        TextView winnerAnnouncementTextView = findViewById(R.id.winner_announcement_textview);
        TextView totalBrainsTextView = findViewById(R.id.total_brains_textview);
        Button playAgainButton = findViewById(R.id.play_again_button);
        Button exitButton = findViewById(R.id.exit_button);

        int winnerId = getIntent().getIntExtra("winnerId", 1);
        int totalBrains = getIntent().getIntExtra("totalBrains", 0);

        winnerAnnouncementTextView.setText(String.format(Locale.getDefault(), "GRACZ %d\nWYGRYWA!", winnerId));
        totalBrainsTextView.setText(String.valueOf(totalBrains));

        playAgainButton.setOnClickListener(v -> {
            Intent intent = new Intent(WinnerActivity.this, PreGameActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        exitButton.setOnClickListener(v -> {
            Intent intent = new Intent(WinnerActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}