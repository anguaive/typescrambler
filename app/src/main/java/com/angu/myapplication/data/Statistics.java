package com.angu.myapplication.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "statistics")
public class Statistics {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "name")
    public String playerName;

    @ColumnInfo(name = "level")
    public int levelReached;

    @ColumnInfo(name = "accuracy")
    public double accuracy;


    @ColumnInfo(name = "date")
    public String date;

    public Statistics(String playerName, int levelReached, double accuracy, String date) {
        this.playerName = playerName;
        this.levelReached = levelReached;
        this.accuracy = accuracy;
        this.date = date;
    }
}
