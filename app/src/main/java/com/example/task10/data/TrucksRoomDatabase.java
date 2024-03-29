package com.example.task10.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Trucks.class}, version=1,exportSchema = false)
public abstract class TrucksRoomDatabase extends RoomDatabase {

    public abstract TrucksDAO trucksDAO();

    private static volatile TrucksRoomDatabase INSTANCE;

    static final int threads =4;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(threads);

    public static TrucksRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (TrucksRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    TrucksRoomDatabase.class, "trucks_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    public static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                TrucksDAO dao = INSTANCE.trucksDAO();
                dao.deleteAll();

            });
        }
    };
}



