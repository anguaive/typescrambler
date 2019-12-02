package com.angu.myapplication.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StatisticsDao {
    @Query("SELECT * FROM statistics s ORDER BY s.level DESC")
    List<Statistics> getAll();

    @Insert
    long insert(Statistics stats);

    @Update
    void update(Statistics stats);

    @Delete
    void deleteItem(Statistics stats);
}
