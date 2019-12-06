package com.angu.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final Button btnNewGame = findViewById(R.id.btnMenuNewGame);
        final Button btnHighscore = findViewById(R.id.btnMenuHighscores);
        final Button btnSettings = findViewById(R.id.btnMenuSettings);

        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, GameActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

        btnHighscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, HighscoreActivity.class);
                startActivity(intent);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        System.out.println(getApplicationContext().fileList().length);
        try {
            AssetManager manager = getApplicationContext().getAssets();
            BufferedReader reader = new BufferedReader(new InputStreamReader(manager.open("words_alpha_sorted.txt")));
            ArrayList<String> words = new ArrayList<String>();
            while(reader.ready()) {
                words.add(reader.readLine());
            }
            System.out.println("Size: " + words.size());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
