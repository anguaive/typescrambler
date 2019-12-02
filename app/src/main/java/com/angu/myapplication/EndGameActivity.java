package com.angu.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.angu.myapplication.data.Statistics;
import com.angu.myapplication.data.StatisticsDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


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
            "Slightly above average!",
            "Great improvement!",
            "\"He's beginning to believe.\"",
            "Impressive, keep it up!",
            "Wow, you're really stepping up!",
            "Great improvement!",
            "Remarkable!",
            "Masterful!",
            "You were so close to the top!",
            "I was wrong to ever doubt you.",
            "Congratulations! You are the champion!"
    };

    private StatisticsDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_end_game);
        setFinishOnTouchOutside(false);
        Intent intent = getIntent();

        final Button btnSubmitAndToMenu = findViewById(R.id.btnEndGameSubmitAndToMenu);
        final TextView textMessage = findViewById(R.id.textEndGameMessage);
        final TextView textStatsLevel = findViewById(R.id.textEndGameStatsLevel);
        final TextView textStatsKeystrokes = findViewById(R.id.textEndGameStatsKeystrokes);
        final TextView textStatsKeystrokesCorrect = findViewById(R.id.textEndGameStatsKeystrokesCorrect);
        final TextView textStatsAccuracy = findViewById(R.id.textEndGameStatsAccuracy);
        final EditText textPlayerName = findViewById(R.id.textEndGamePlayerName);

        final int level = intent.getExtras().getInt("level") - 1;
        final int keystrokes = intent.getExtras().getInt("keystrokes");
        final int keystrokesCorrect = intent.getExtras().getInt("keystrokesCorrect");
        final double accuracy = keystrokes == 0 ? 0 : (double)keystrokesCorrect/keystrokes * 100;

        database = Room.databaseBuilder(
                getApplicationContext(),
                StatisticsDatabase.class,
                "statistics"
        ).build();

        textPlayerName.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    btnSubmitAndToMenu.performClick();
                    return true;
                }
                return false;
            }
        });


        textMessage.setText(endGameMessages[level / 5]);
        textStatsLevel.setText("You completed level " + level + "!");
        textStatsKeystrokes.setText("Keystrokes: " + keystrokes);
        textStatsKeystrokesCorrect.setText("Correct keystrokes: " + keystrokesCorrect);
        textStatsAccuracy.setText(String.format(Locale.US, "Accuracy: %.2f%%", accuracy));


        btnSubmitAndToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playerName = textPlayerName.getText().toString().isEmpty() ? "Anonymous" : textPlayerName.getText().toString();

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                Date today = Calendar.getInstance().getTime();
                String dateString = dateFormat.format(today);

                final Statistics stats = new Statistics(playerName, level, accuracy , dateString);
                saveStatsInBackground(stats);

                Toast.makeText(getApplicationContext(), "Score saved.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(EndGameActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void saveStatsInBackground(final Statistics stats) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                database.statisticsDao().insert(stats);
                return true;
            }
        }.execute();
    }
}
