package com.example.cat_water;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DrinkDao {
    @Query("SELECT * FROM Drink")
    List<Drink> getAll();

    @Insert
    void insert(Drink drink);

    @Update
    void update(Drink drink);

    @Delete
    void delete(Drink drink);
}
