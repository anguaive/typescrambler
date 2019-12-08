package com.angu.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.angu.myapplication.fragments.KeyboardFragment;
import com.angu.myapplication.logic.GameState;
import com.angu.myapplication.logic.WordList;
import com.angu.myapplication.views.GameEditText;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity {

    private GameState gameState;
    private WordList wordList;
    private int keystrokes, keystrokesCorrect;
    private static ScheduledThreadPoolExecutor timer = new ScheduledThreadPoolExecutor(2);
    ScheduledFuture<?> timerSchedule, progressBarSchedule, timerAnimSchedule;


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
        AssetManager assetManager = getApplicationContext().getAssets();
        wordList = new WordList(assetManager);
        gameState = new GameState(wordList);

        final GameEditText textGameInput = findViewById(R.id.textGameInput);
        textGameInput.initialize((TextView) findViewById(R.id.textGameObjective));

        final KeyboardFragment keyboardFragment = (KeyboardFragment) getSupportFragmentManager().findFragmentById(R.id.layoutMain);
        keyboardFragment.initialize(textGameInput);

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
                if (newInput.trim().length() > oldInput.trim().length()) {
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
                        if (newInput.trim().length() > oldInput.trim().length()) {
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
        final KeyboardFragment keyboardFragment = (KeyboardFragment) getSupportFragmentManager().findFragmentById(R.id.layoutMain);
        final Animation timerLowAnimation = AnimationUtils.loadAnimation(this, R.anim.timer_low_animation);
        final ImageView imgTimerLowAnimation = findViewById(R.id.imgTimerAnim);

        if (keyboardFragment == null) {
            return;
        }

        keyboardFragment.reset();

        if (timerSchedule != null) {
            timerSchedule.cancel(false);
        }
        if (progressBarSchedule != null) {
            progressBarSchedule.cancel(false);
        }
        if (timerAnimSchedule != null) {
            timerAnimSchedule.cancel(false);
        }
        progressTimer.setProgress(0);

        // TODO: level begin animation, maybe a countdown or something
        gameState.increaseLevel();
        textGameInput.setObjective(gameState.word);
        keyboardFragment.setupKeyboard(gameState.word);
        textCurrentLevel.setText(getString(R.string.level, gameState.level));
        timerLowAnimation.setDuration((long)(gameState.timeLimit * 0.4));
        timerLowAnimation.setStartOffset((long)(gameState.timeLimit * 0.6));


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

        Thread timerAnimTask = new Thread() {
            @Override
            public void run() {
                imgTimerLowAnimation.startAnimation(timerLowAnimation);
            }
        };

        timerSchedule = timer.schedule(timerTask, (long) gameState.timeLimit, TimeUnit.MILLISECONDS);
        timerAnimSchedule = timer.schedule(timerAnimTask, 0, TimeUnit.MILLISECONDS);
        progressBarSchedule = timer.scheduleAtFixedRate(progressBarTask, 0, (long) gameState.timeLimit / 100, TimeUnit.MILLISECONDS);


    }


}
