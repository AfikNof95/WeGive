package com.example.wegive.models;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.wegive.models.attendent.Attendant;
import com.example.wegive.models.attendent.AttendantDao;
import com.example.wegive.models.post.Post;
import com.example.wegive.models.post.PostAttendantPair;
import com.example.wegive.models.post.PostDao;
import com.example.wegive.models.user.User;
import com.example.wegive.models.user.UserDao;

@Database(entities = {Post.class, Attendant.class, User.class, PostAttendantPair.class}, version = 1, exportSchema = false)
public abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract PostDao postDao();

    public abstract AttendantDao attendantDao();

    public abstract UserDao userDao();

    public abstract PostAttendantPair PostAttendantPair();
}
