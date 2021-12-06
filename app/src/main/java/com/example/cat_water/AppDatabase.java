package com.example.cat_water;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Drink.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DrinkDao drinkDao();
    public static final String DATABASE_NAME = "Drink";

    public static AppDatabase instance;
    public static AppDatabase getDatabase(final Context context){
        if(instance == null)
            instance = Room.databaseBuilder(
                    context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();

        return instance;

    }
}
