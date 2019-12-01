package com.angu.myapplication.views;

import android.content.Context;
import android.graphics.Color;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;

public class GameEditText extends AppCompatEditText {

    private boolean incorrect = false;
    private TextView hintTextView;
    public String hintText = "";

    public GameEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setIncorrect(boolean incorrect) {
        if(this.incorrect == incorrect) {
            return;
        }

        this.incorrect = incorrect;
        if(incorrect) {
            this.setTextColor(Color.RED);
            hintTextView.setText("");
        } else {
            this.setTextColor(Color.GREEN);
            hintTextView.setText(hintText);
        }
    }

    public void initialize(final TextView hintTextView) {
        this.hintTextView = hintTextView;
        setFilters(new InputFilter[]{new InputFilter.AllCaps()});
    }

    public void setGameText(String text) {
        hintText = text.toUpperCase();
        hintTextView.setText(hintText);
        setText("");
        setIncorrect(false);
    }
}
