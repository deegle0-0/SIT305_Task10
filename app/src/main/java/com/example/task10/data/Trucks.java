package com.example.task10.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "trucksData")
public class Trucks implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name="model")
    String model;

    @NonNull
    @ColumnInfo(name="date")
    String dates;

    @NonNull
    @ColumnInfo(name="location")
    String location;


    @NonNull
    @ColumnInfo(name="number")
    String number;

    @NonNull
    public String getNumber() {
        return number;
    }

    public void setNumber(@NonNull String number) {
        this.number = number;
    }

    @NonNull
    public String getLocation() {
        return location;
    }

    public void setLocation(@NonNull String location) {
        this.location = location;
    }

    public Trucks(int id, @NonNull String model, @NonNull String dates, @NonNull String location,@NonNull String number) {
        this.id = id;
        this.model = model;
        this.dates = dates;
        this.location= location;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getModel() {
        return model;
    }

    public void setModel(@NonNull String model) {
        this.model = model;
    }

    @NonNull
    public  String getDates() {
        return dates;
    }

    public void setDates(@NonNull String dates) {
        this.dates = dates;
    }
}
