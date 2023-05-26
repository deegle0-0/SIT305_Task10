package com.example.task10.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Users.class}, version=1,exportSchema = false)
public abstract class UsersRoomDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();

    private static volatile UsersRoomDatabase INSTANCE;

    static final int threads =4;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(threads);

    public static UsersRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (UsersRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    UsersRoomDatabase.class, "users_database")
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
                UserDAO dao = INSTANCE.userDAO();
                dao.deleteAll();

            });
        }
    };
}
