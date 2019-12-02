package com.angu.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import com.angu.myapplication.adapter.StatisticsAdapter;
import com.angu.myapplication.data.Statistics;
import com.angu.myapplication.data.StatisticsDatabase;

import java.util.List;

public class HighscoreActivity extends AppCompatActivity {

    private StatisticsDatabase database;
    private StatisticsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        database = Room.databaseBuilder(
                getApplicationContext(),
                StatisticsDatabase.class,
                "statistics"
        ).build();

        RecyclerView recyclerView = findViewById(R.id.layoutHighscores);
        adapter = new StatisticsAdapter();
        loadItemsInBackground();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("StaticFieldLeak")
    private void loadItemsInBackground() {
        new AsyncTask<Void, Void, List<Statistics>>() {

            @Override
            protected List<Statistics> doInBackground(Void... voids) {
                return database.statisticsDao().getAll();
            }

            @Override
            protected void onPostExecute(List<Statistics> shoppingItems) {
                adapter.update(shoppingItems);
            }
        }.execute();
    }

}
