package com.example.zombie_dice.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.zombie_dice.R;
import com.example.zombie_dice.storage.Storage;

public class MainActivity extends AppCompatActivity {

    private Storage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storage = new Storage(this);

        findViewById(R.id.new_game_button).setOnClickListener(v -> {
            storage.clearGame();
            startActivity(new Intent(MainActivity.this, PreGameActivity.class));
        });

        findViewById(R.id.rules_button).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, RulesActivity.class));
        });

        findViewById(R.id.exit_button).setOnClickListener(v -> {
            finish();
        });
    }
}
