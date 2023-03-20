package com.example.wegive.models;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.wegive.models.post.Post;
import com.example.wegive.models.post.PostDao;
import com.example.wegive.models.user.User;
import com.example.wegive.models.user.UserDao;
import com.example.wegive.utils.AttendantTypeConverter;
import com.example.wegive.utils.CommentsTypeConverter;

@TypeConverters({AttendantTypeConverter.class, CommentsTypeConverter.class})
@Database(entities = {Post.class, User.class}, version = 1, exportSchema = false)
public abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract PostDao postDao();

    public abstract UserDao userDao();

}
