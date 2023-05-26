package com.example.task10.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "loginUsers")
public class Users implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public Users(int id, @NonNull String fullName, @NonNull String username, @NonNull String password, String phoneNo) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.phoneNo = phoneNo;
    }

    @NonNull
    @ColumnInfo(name="fullName")
    String fullName;

    @NonNull
    @ColumnInfo(name = "username")
    String username;

    @NonNull
    @ColumnInfo(name = "password")
    String password;

//    @ColumnInfo(name = "imageList")
//    private ImageView image;

    @ColumnInfo(name = "phoneNo")
    String phoneNo;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getFullName() {
        return fullName;
    }

    public void setFullName(@NonNull String fullName) {
        this.fullName = fullName;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

//    public ImageView getImage() {
//        return image;
//    }
//
//    public void setImage(ImageView image) {
//        this.image = image;
//    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
