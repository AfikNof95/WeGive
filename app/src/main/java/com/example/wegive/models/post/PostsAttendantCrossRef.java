package com.example.wegive.models.post;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.example.wegive.models.attendent.Attendant;

@Entity(primaryKeys = {"postId","attendantId"}, tableName = "post_attendant_join", foreignKeys = {@ForeignKey(entity = Post.class, parentColumns = "id", childColumns = "postId"),@ForeignKey(entity = Attendant.class, parentColumns = "id", childColumns = "attendantId")})
public class PostsAttendantCrossRef {
    @NonNull
    @ColumnInfo(name = "postId",index = true)
    public String postId;
    @NonNull
    @ColumnInfo(name = "attendantId",index = true)
    public String attendantId;

    public PostsAttendantCrossRef(@NonNull String postId, @NonNull String attendantId){
        this.postId = postId;
        this.attendantId = attendantId;
    }
}


