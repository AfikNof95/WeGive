package com.example.wegive.models.post;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.wegive.models.attendent.Attendant;

import java.util.List;

@Entity
public class PostAttendantPair {
    @Embedded
    public Post post;
    @Relation(parentColumn = "id", entityColumn = "id", associateBy = @Junction(PostsAttendantCrossRef.class))
    public List<Attendant> attendantList;
}
