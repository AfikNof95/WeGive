package com.example.wegive.models.post;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


import com.example.wegive.MyApplication;

@Database(entities = {Post.class}, version = 80,exportSchema = false)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract PostDao postDao();
}

public class AppLocalDB {
    public static AppLocalDbRepository getAppDb() {
        return Room.databaseBuilder(MyApplication.getMyContext(),
                        AppLocalDbRepository.class,
                        "posts.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    private AppLocalDB() {
    }
}

