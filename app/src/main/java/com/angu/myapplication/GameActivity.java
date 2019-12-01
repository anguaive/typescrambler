package com.angu.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.angu.myapplication.logic.GameState;
import com.angu.myapplication.logic.Statistics;
import com.angu.myapplication.views.GameEditText;

import java.util.Random;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity {

    private static Random random = new Random();
    private static GameState gameState = new GameState();
    private static Statistics statistics = new Statistics();
    private static ScheduledThreadPoolExecutor timer = new ScheduledThreadPoolExecutor(2);
    ScheduledFuture<?> timerSchedule, progressBarSchedule;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initializeGame();
    }

    public void initializeGame() {
        final GameEditText gameEditText = findViewById(R.id.gameEditText);
        gameEditText.initialize((TextView) findViewById(R.id.gameHintText));

        gameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                int hintLength = gameEditText.hintText.length();
                int textLength = s.length();
                System.out.println(text + " " + gameEditText.hintText);

                if (textLength > hintLength) {
                    gameEditText.setIncorrect(true);
                    return;
                } else {
                    if (!text.equals(gameEditText.hintText.substring(0, textLength))) {
                        gameEditText.setIncorrect(true);
                        return;
                    } else {
                        gameEditText.setIncorrect(false);
                    }
                }

                if (text.equals(gameEditText.hintText)) {
                    beginNextLevel();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        beginNextLevel();

    }

    public void beginGame() {

    }

    public void endGame() {
        // move to EndScoreActivity, display stats from the current round, then go back to menu
        // happens when the timer runs out
        // maybe display an end game animation
        // reset game
        gameState.difficulty = 0;
        Intent intent = new Intent(GameActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    public void beginNextLevel() {
        final GameEditText gameEditText = findViewById(R.id.gameEditText);
        final TextView levelText = findViewById(R.id.levelText);
        final ProgressBar progressBarTimer = findViewById(R.id.progressBarTimer);

        if(timerSchedule != null && progressBarSchedule != null) {
            timerSchedule.cancel(false);
            progressBarSchedule.cancel(false);
            progressBarTimer.setProgress(0);
        }
        // level begin animation, maybe a countdown or something
        gameState.increaseLevel();
        gameEditText.setGameText(gameState.word);
        levelText.setText(getString(R.string.level, gameState.difficulty));

        Thread timerTask = new Thread() {
            @Override
            public void run() {
                endGame();
            }
        };

        Thread progressBarTask = new Thread() {
            @Override
            public void run() {
                progressBarTimer.incrementProgressBy(50 * 100 / gameState.timeLimit);
            }
        };

        timerSchedule = timer.schedule(timerTask, gameState.timeLimit, TimeUnit.MILLISECONDS);
        progressBarSchedule = timer.scheduleAtFixedRate(progressBarTask, 0, 50, TimeUnit.MILLISECONDS);


    }


}
