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
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Users user);

    @Update
    void update(Users user);

    @Delete
    void delete(Users user);

    @Query("DELETE FROM loginUsers")
    void deleteAll();

    @Query("SELECT * FROM loginUsers")
    LiveData<List<Users>> getUsers();

    @Query("SELECT username, password FROM loginUsers WHERE username=:user and password=:pass")
    boolean loginUser(String user, String pass);

}
