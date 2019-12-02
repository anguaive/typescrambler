package com.angu.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.angu.myapplication.logic.GameState;
import com.angu.myapplication.views.GameEditText;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity {

    private static GameState gameState = new GameState();
    private int keystrokes, keystrokesCorrect;
    private static ScheduledThreadPoolExecutor timer = new ScheduledThreadPoolExecutor(2);
    ScheduledFuture<?> timerSchedule, progressBarSchedule;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initializeGame();
    }

    @Override
    protected void onPause() {
        super.onPause();

        resetGame();
        if (timerSchedule != null && progressBarSchedule != null) {
            timerSchedule.cancel(false);
            progressBarSchedule.cancel(false);
        }
    }

    public void initializeGame() {
        resetGame();

        final GameEditText textGameInput = findViewById(R.id.textGameInput);
        textGameInput.initialize((TextView) findViewById(R.id.textGameObjective));

        textGameInput.addTextChangedListener(new TextWatcher() {

            String oldInput;
            String newInput;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                oldInput = textGameInput.getText() == null ? "" : textGameInput.getText().toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newInput = s.toString();
                int objectiveLength = textGameInput.objective.length();
                int newInputLength = s.length();

                // space and backspace will not count as keystrokes
                // therefore, a keystroke only happens if the trimmed length of the newInput is larger than that of the oldInput
                if(newInput.trim().length() > oldInput.trim().length()) {
                    keystrokes++;
                }

                if (newInputLength > objectiveLength) {
                    textGameInput.setIncorrect(true);
                    return;
                } else {
                    if (!newInput.equals(textGameInput.objective.substring(0, newInputLength))) {
                        textGameInput.setIncorrect(true);
                        return;
                    } else {
                        textGameInput.setIncorrect(false);
                        if(newInput.trim().length() > oldInput.trim().length()) {
                            keystrokesCorrect++;
                        }
                    }
                }

                if (newInput.equals(textGameInput.objective)) {
                    beginNextLevel();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        beginNextLevel();

    }

    public void resetGame() {
        gameState.level = 0;
    }

    public void endGame() {
        Bundle statsBundle = new Bundle();
        statsBundle.putInt("level", gameState.level);
        statsBundle.putInt("keystrokes", keystrokes);
        statsBundle.putInt("keystrokesCorrect", keystrokesCorrect);

        Intent intent = new Intent(GameActivity.this, EndGameActivity.class);
        intent.putExtras(statsBundle);
        startActivity(intent);
    }

    public void beginNextLevel() {
        final GameEditText textGameInput = findViewById(R.id.textGameInput);
        final TextView textCurrentLevel = findViewById(R.id.textCurrentLevel);
        final ProgressBar progressTimer = findViewById(R.id.progressTimer);

        if (timerSchedule != null && progressBarSchedule != null) {
            timerSchedule.cancel(false);
            progressBarSchedule.cancel(false);
            progressTimer.setProgress(0);
        }
        // level begin animation, maybe a countdown or something
        gameState.increaseLevel();
        textGameInput.setObjective(gameState.word);
        textCurrentLevel.setText(getString(R.string.level, gameState.level));

        Thread timerTask = new Thread() {
            @Override
            public void run() {
                endGame();
            }
        };

        Thread progressBarTask = new Thread() {
            @Override
            public void run() {
                progressTimer.incrementProgressBy(1);
            }
        };

        timerSchedule = timer.schedule(timerTask, (int) gameState.timeLimit, TimeUnit.MILLISECONDS);
        progressBarSchedule = timer.scheduleAtFixedRate(progressBarTask, 0, (long) gameState.timeLimit / 100, TimeUnit.MILLISECONDS);


    }


}
