package com.example.wegive.models.post;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;


import com.example.wegive.models.postAttendantPair.PostAttendantPair;

import java.util.List;

@Dao
public interface PostDao {

    @Query("select * from Post ORDER BY createdAt DESC")
    LiveData<List<Post>> getAll();

    @Transaction
    @Query("SELECT * FROM  Post ORDER BY createdAt DESC ")
    LiveData<List<PostAttendantPair>> getAllPostWithAttendants();

    @Query("select * from Post where id = :postId")
    Post getPostById(String postId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Post... posts);


    @Delete
    void delete(Post post);

    @Query("DELETE FROM Post WHERE id= :id")
    void deletePostById(String id);
}
