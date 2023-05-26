package com.example.task10.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface TrucksDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Trucks trucks);

    @Update
    void update(Trucks trucks);

    @Delete
    void delete(Trucks trucks);

    @Query("DELETE FROM trucksData")
    void deleteAll();

    @Query("SELECT * FROM trucksData")
    LiveData<List<Trucks>> getTrucks();
}
