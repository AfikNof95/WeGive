package com.example.wegive.models;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.example.wegive.MyApplication;
import com.example.wegive.models.post.Post;
import com.example.wegive.models.post.PostDao;

public class AppLocalDB {
    public static AppLocalDbRepository getAppDb() {
        return Room.databaseBuilder(MyApplication.getMyContext(),
                        AppLocalDbRepository.class,
                        "weGive.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    private AppLocalDB() {
    }
}

