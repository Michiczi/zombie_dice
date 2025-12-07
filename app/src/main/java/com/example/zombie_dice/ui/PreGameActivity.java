package com.example.zombie_dice.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zombie_dice.R;

public class PreGameActivity extends AppCompatActivity {

    private EditText numberOfPlayersEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_game);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        numberOfPlayersEditText = findViewById(R.id.number_of_players_edittext);
        Button startGameButton = findViewById(R.id.start_game_button);

        startGameButton.setOnClickListener(v -> {
            String numberOfPlayersStr = numberOfPlayersEditText.getText().toString();
            if (numberOfPlayersStr.isEmpty()) {
                Toast.makeText(this, "Please enter the number of players", Toast.LENGTH_SHORT).show();
                return;
            }

            int numberOfPlayers = Integer.parseInt(numberOfPlayersStr);
            if (numberOfPlayers < 2) {
                Toast.makeText(this, "You need at least 2 players", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(PreGameActivity.this, GameActivity.class);
            intent.putExtra("numberOfPlayers", numberOfPlayers);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
