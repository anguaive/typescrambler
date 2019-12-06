package com.angu.myapplication.logic;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

public class WordList {

    // words list from https://github.com/dwyl/english-words (thinned down version of words_alpha)
    private final String WORD_LIST_PATH = "words_alpha_sorted.txt";

    private TreeMap<Integer, List<String>> wordsGroupedByLength;
    private Random random = new Random();

    public WordList(AssetManager assetManager) {
        List<String> words = new ArrayList<>();
        wordsGroupedByLength = new TreeMap<>();
        for (int i = 5; i < 17; i++) {
            wordsGroupedByLength.put(i, new ArrayList<String>());
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open(WORD_LIST_PATH)));
            while (reader.ready()) {
                words.add(reader.readLine());
            }

            Collections.sort(words, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    if (o1.length() < o2.length()) {
                        return -1;
                    } else if (o1.length() > o2.length()) {
                        return 1;
                    } else
                        return 0;
                }
            });

            for (String word : words) {
                wordsGroupedByLength.get(word.length()).add(word);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getRandomWord(int level) {
        double wordLength = 4 + Math.sqrt(Math.sqrt(level)) + Math.sqrt(level) * random.nextDouble();
        if(wordLength > 16) {
            wordLength = 16;
        }

        List<String> wordCandidates = wordsGroupedByLength.get((int)wordLength);
        return wordCandidates.get(random.nextInt(wordCandidates.size()));
    }
}
