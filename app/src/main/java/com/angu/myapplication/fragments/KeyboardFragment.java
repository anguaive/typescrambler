package com.angu.myapplication.fragments;

import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.angu.myapplication.GameActivity;
import com.angu.myapplication.R;
import com.angu.myapplication.views.GameEditText;
import com.angu.myapplication.views.KeyboardButton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class KeyboardFragment extends Fragment {

    private final int ROW_COUNT = 5;
    private final int COLUMN_COUNT = 5;
    private Random random = new Random();

    private ViewGroup container;
    private float scale;
    private GameEditText textGameInput;
    private GridLayout layout;
    private List<Pair<Integer, Integer>> layoutCellCoordinates = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.container = container;


        View view = inflater.inflate(R.layout.keyboard, container, true);
        layout = view.findViewById(R.id.layoutKeyboard);
        return view;
    }

    public void initialize(GameEditText textGameInput) {
        this.textGameInput = textGameInput;
    }

    public void setupKeyboard(String word) {
        for (int y = 0; y < ROW_COUNT; y++) {
            for (int x = 0; x < COLUMN_COUNT; x++) {
                layoutCellCoordinates.add(new Pair<>(x, y));
            }
        }

        Set<Character> letters = new TreeSet<>();
        for (char letter : word.toCharArray()) {
            letters.add(Character.toUpperCase(letter));
        }
        for (Character letter : letters) {
            System.out.println(word);
            int index = random.nextInt(layoutCellCoordinates.size());
            Pair<Integer, Integer> cell = layoutCellCoordinates.get(index);
            layoutCellCoordinates.remove(index);
            addButton(letter, cell);
            System.out.println(letter + " (" + cell.first + "," + cell.second + ")");
        }

        // Add a backspace
        int index = random.nextInt(layoutCellCoordinates.size());
        Pair<Integer, Integer> cell = layoutCellCoordinates.get(index);
        layoutCellCoordinates.remove(index);
        addButton('<', cell);
        System.out.println('<' + " (" + cell.first + "," + cell.second + ")");


    }

    private void addButton(char letter, Pair<Integer, Integer> cell) {
        LinearLayout buttonLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.item_keyboard_button, container);
        KeyboardButton button = (KeyboardButton) buttonLayout.getChildAt(0);
        button.initialize(textGameInput, letter);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.rowSpec = GridLayout.spec(cell.first, GridLayout.CENTER);
        params.columnSpec = GridLayout.spec(cell.second, GridLayout.CENTER);
        buttonLayout.setLayoutParams(params);
        layout.addView(buttonLayout);

    }

    public void reset() {
        layout.removeAllViews();
        layoutCellCoordinates.clear();
    }
}
