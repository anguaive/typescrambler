package com.angu.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.angu.myapplication.logic.Statistics;

import org.w3c.dom.Text;


public class EndGameActivity extends AppCompatActivity {

    private String[] endGameMessages = {
            "Yikes.",
            "Ugh... Are you sure you know how to play?",
            "Do you know what these letters mean?",
            "Have you ever held a phone before?",
            "Phew... You were lucky to get this far.",
            "Slightly less disappointing than usual.",
            "Nice try. Maybe next time.",
            "Meh, I've seen worse.",
            "Huh... You're showing sings of life.",
            "Not great, but not terrible.",
            "Oh... You're starting to get the hang of it.",
            "Great improvement!",
            "\"He's beginning to believe.\"",
            "Impressive, keep it up!",
            "Wow, you're really stepping up!",
            "Slightly above average!",
            "Remarkable!",
            "Masterful!",
            "You were so close to the top!",
            "I was wrong to ever doubt you.",
            "Congratulations! You are the champion!"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_end_game);
        setFinishOnTouchOutside(false);
        Intent intent = getIntent();

        final Button btnToMenu = findViewById(R.id.btnEndGameToMenu);
        final TextView textMessage = findViewById(R.id.textEndGameMessage);
        final TextView textStatsLevel = findViewById(R.id.textEndGameStatsLevel);
        final TextView textStatsKeystrokes = findViewById(R.id.textEndGameStatsKeystrokes);
        final TextView textStatsKeystrokesCorrect = findViewById(R.id.textEndGameStatsKeystrokesCorrect);
        final EditText textPlayerName = findViewById(R.id.textEndGamePlayerName)

        final int level = intent.getExtras().getInt("level") - 1;
        final int keystrokes = intent.getExtras().getInt("keystrokes");
        final int keystrokesCorrect = intent.getExtras().getInt("keystrokesCorrect");


        textMessage.setText(endGameMessages[level / 5]);
        textStatsLevel.setText("You completed level " + level + "!");
        textStatsKeystrokes.setText("Keystrokes: " + keystrokes);
        textStatsKeystrokesCorrect.setText("Correct keystrokes: " + keystrokesCorrect);

        btnToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playerName = textPlayerName.getText() == null ? "Anonymous" : textPlayerName.getText().toString();
                Statistics stats = new Statistics(playerName, level, keystrokes, keystrokesCorrect);
                Intent intent = new Intent(EndGameActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

    }
}
