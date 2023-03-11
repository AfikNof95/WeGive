package com.example.wegive.models.post;

import androidx.room.Embedded;
import androidx.room.Entity;

@Entity(primaryKeys = {"postId,userId"})
public class PostsAttendantCrossRef {
    public String postId;
    public String userId;
}


