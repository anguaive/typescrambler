package com.angu.myapplication.logic;

public class Statistics {

    public String playerName;
    public int levelReached;
    public int keystrokes;
    public int keystrokesCorrect;

    public Statistics(String playerName, int levelReached, int keystrokes, int keystrokesCorrect) {
        this.playerName = playerName;
        this.levelReached = levelReached;
        this.keystrokes = keystrokes;
        this.keystrokesCorrect = keystrokesCorrect;
    }
}
