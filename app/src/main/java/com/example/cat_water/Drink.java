package com.example.cat_water;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Drink {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String Date;
    private String Water;

    public Drink(String Date, String Water){
        this.Date = Date ;
        this.Water = Water ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getWater() {
        return Water;
    }

    public void setWater(String water) {
        Water = water;
    }

    @Override
    public String toString() {
        return "Drink{" +
                "\n id=" + id +
                ", Date='" + Date + '\'' +
                ", Water='" + Water + '\'' +
                '}';
    }
}


