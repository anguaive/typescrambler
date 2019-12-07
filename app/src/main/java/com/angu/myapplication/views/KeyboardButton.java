package com.angu.myapplication.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.AppCompatButton;

public class KeyboardButton extends AppCompatButton {

    public KeyboardButton(Context context) {
        super(context);
    }

    public KeyboardButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void initialize(final GameEditText gameEditText, final char letter) {
        setText(String.valueOf(Character.toUpperCase(letter)));
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // If letter is a backspace
                if(letter == '<') {
                    if(gameEditText.getText().length() > 0) {
                        gameEditText.getText().delete(gameEditText.getText().length() - 1,
                                gameEditText.getText().length());
                    }
                } else {
                    gameEditText.append(String.valueOf(letter));
                }
            }
        });
    }
}
