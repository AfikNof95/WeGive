package com.example.wegive.models.post;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.wegive.models.postAttendantPair.PostAttendantPair;

import java.util.List;

@Dao
public interface PostsAttendantCrossDao {
    @Transaction
    @Query("SELECT * FROM Post")
    public List<PostAttendantPair> getPostWithAttendant();


}
