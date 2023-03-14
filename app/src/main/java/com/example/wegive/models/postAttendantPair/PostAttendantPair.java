package com.example.wegive.models.postAttendantPair;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.wegive.models.attendent.Attendant;
import com.example.wegive.models.post.Post;
import com.example.wegive.models.post.PostsAttendantCrossRef;

import java.util.List;


public class PostAttendantPair {
    @Embedded
    public Post post;
    @Relation(parentColumn = "id", entityColumn = "id", associateBy = @Junction(value = PostsAttendantCrossRef.class,parentColumn = "postId",entityColumn = "attendantId"))
    public List<Attendant> attendantList;
}
