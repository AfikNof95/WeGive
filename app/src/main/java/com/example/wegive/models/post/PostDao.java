package com.example.wegive.models.post;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;



import java.util.List;

@Dao
public interface PostDao {

    @Query("select * from Post ORDER BY createdAt DESC")
    LiveData<List<Post>> getAll();

    @Query("SELECT Post.id as id, Post.title as title, Post.content as content, Post.time as time, Post.creatorId as creatorId,User.name as creatorName, User.avatarUrl as creatorAvatar, Post.createdAt as createdAt, Post.attendants as attendants,Post.comments as comments,Post.imageUrl as imageUrl, Post.lastUpdated as lastUpdated FROM Post LEFT JOIN User on Post.creatorId = User.id  WHERE Post.creatorId = :userId ORDER BY post.createdAt DESC")
    LiveData<List<Post>> getAllPostsByUserId(String userId);


    @Query("SELECT Post.id as id, Post.title as title, Post.content as content, Post.time as time, Post.creatorId as creatorId,User.name as creatorName, User.avatarUrl as creatorAvatar, Post.createdAt as createdAt, Post.attendants as attendants,Post.comments as comments,Post.imageUrl as imageUrl, Post.lastUpdated as lastUpdated FROM Post LEFT JOIN User on Post.creatorId = User.id ORDER BY post.createdAt DESC")
    LiveData<List<Post>> getAllPosts();

    @Query("SELECT Post.id as id, Post.title as title, Post.content as content, Post.time as time, Post.creatorId as creatorId,User.name as creatorName, User.avatarUrl as creatorAvatar, Post.createdAt as createdAt, Post.attendants as attendants,Post.comments as comments,Post.imageUrl as imageUrl, Post.lastUpdated as lastUpdated FROM Post LEFT JOIN User on Post.creatorId = User.id ORDER BY post.time DESC")
    LiveData<List<Post>> getAllPostsOrderedByDate();



    @Query("select * from Post where id = :postId")
    Post getPostById(String postId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Post... posts);


    @Delete
    void delete(Post post);

    @Query("DELETE FROM Post WHERE id= :id")
    void deletePostById(String id);
}
