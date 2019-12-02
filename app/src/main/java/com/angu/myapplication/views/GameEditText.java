package com.angu.myapplication.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;

import com.angu.myapplication.R;
import com.angu.myapplication.data.Statistics;

import java.util.List;

public class GameEditText extends AppCompatEditText {

    private boolean incorrect = false;
    private TextView textObjective;
    public String objective = "";

    public GameEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setIncorrect(boolean incorrect) {
        if(this.incorrect == incorrect) {
            return;
        }

        this.incorrect = incorrect;
        if(incorrect) {
            this.setTextColor(getResources().getColor(R.color.colorIncorrectInput));
            textObjective.setText("");
        } else {
            this.setTextColor(getResources().getColor(R.color.colorCorrectInput));
            textObjective.setText(objective);
        }
    }

    public void initialize(final TextView textObjective) {
        this.textObjective = textObjective;
        setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        setOnTouchListener(new OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    public void setObjective(String objective) {
        this.objective = objective.toUpperCase();
        textObjective.setText(this.objective);
        setText("");
        setIncorrect(false);
    }
}
