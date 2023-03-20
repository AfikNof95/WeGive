package com.example.wegive.models.post;


import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import androidx.room.PrimaryKey;

import com.example.wegive.MyApplication;
import com.example.wegive.models.attendent.Attendant;
import com.example.wegive.models.comment.Comment;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;


import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


@Entity
public class Post implements Serializable, Comparable<Post> {

    public static final String COLLECTION = "posts";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String TIME = "time";
    public static final String IMAGE_URL = "image_url";
    public static final String CREATOR_NAME = "creator_name";

    public static final String CREATOR_AVATAR = "creator_avatar";
    public static final String CREATOR_ID = "creator_id";
    public static final String CREATED_AT = "created_at";
    public static final String ATTENDANTS = "attendants";
    public static final String COMMENTS = "comments";
    public static final String LAST_UPDATED = "last_updated";
    public static final String LOCAL_LAST_UPDATED = "last_updated";


    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "id", index = true)
    public String id;
    public String title;
    public String content;
    public String time;
    public String imageUrl;
    public String creatorName;
    public String creatorId;

    public String creatorAvatar;

    public Long createdAt;

    public List<Attendant> attendants;

    public List<Comment> comments;
    public Long lastUpdated;


    public Post(String id, String title, String content, String time, String imageUrl, String creatorName, String creatorId, String creatorAvatar, List<Attendant> attendants, List<Comment> comments, Long createdAt) {
        this.id = id == null ? UUID.randomUUID().toString() : id;
        this.title = title;
        this.content = content;
        this.time = time;
        this.imageUrl = imageUrl;
        this.creatorName = creatorName;
        this.creatorId = creatorId;
        this.creatorAvatar = creatorAvatar;
        this.attendants = attendants == null ? new ArrayList<>() : attendants;
        this.comments = comments == null ? new ArrayList<>() : comments;
        this.createdAt = createdAt != null ? createdAt : (new Date()).getTime();
    }


    public static Post fromJson(Map<String, Object> json) {
        String id = (String) json.get(ID);
        String title = (String) json.get(TITLE);
        String content = (String) json.get(CONTENT);
        String time = (String) json.get(TIME);
        String imageUrl = (String) json.get(IMAGE_URL);
        String creatorName = (String) json.get(CREATOR_NAME);
        String creatorId = (String) json.get(CREATOR_ID);
        List<Attendant> attendants = (List<Attendant>) json.get(ATTENDANTS);
        List<Comment> comments = (List<Comment>) json.get(COMMENTS);
        String creatorAvatar = (String) json.get(CREATOR_AVATAR);
        Long createdAt = (Long) json.get(CREATED_AT);
        Post post = new Post(id, title, content, time, imageUrl, creatorName, creatorId, creatorAvatar, attendants, comments, createdAt);
        Timestamp lastUpdated = (Timestamp) json.get(LAST_UPDATED);
        if (lastUpdated != null) {
            post.setLastUpdated(lastUpdated.getSeconds());
        }


        return post;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(ID, this.id);
        json.put(TITLE, this.title);
        json.put(CONTENT, this.content);
        json.put(TIME, this.time);
        json.put(IMAGE_URL, this.imageUrl);
        json.put(CREATOR_NAME, this.creatorName);
        json.put(CREATOR_ID, this.creatorId);
        json.put(CREATOR_AVATAR, this.creatorAvatar);
        json.put(ATTENDANTS, this.attendants);
        json.put(COMMENTS, this.comments);
        json.put(CREATED_AT, this.createdAt);
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());

        return json;
    }


    public static long getLocalLastUpdated() {
        SharedPreferences sp = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        return sp.getLong(LOCAL_LAST_UPDATED, 0);
    }

    public static void setLocalLastUpdate(Long time) {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(LOCAL_LAST_UPDATED, time);
        editor.commit();
    }


    @NotNull
    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public String getTime() {
        return this.time;
    }

    public String getCreatorId() {
        return this.creatorId;
    }

    public String getCreatorName() {
        return this.creatorName;
    }


    public String getImageUrl() {
        return this.imageUrl;
    }

    public List<Attendant> getAttendants() {
        return this.attendants;
    }

    public Long getLastUpdated() {
        return this.lastUpdated;
    }

    @NotNull
    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }


    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setAttendants(List<Attendant> attendants) {
        this.attendants = attendants;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getCreatorAvatar() {
        return creatorAvatar;
    }

    public void setCreatorAvatar(String creatorAvatar) {
        this.creatorAvatar = creatorAvatar;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public int compareTo(Post post) {
        return (Objects.nonNull(post.lastUpdated)) ? getLastUpdated().compareTo(post.lastUpdated) : 0;
    }

    public Long getCreatedAtUnformatted() {

        return this.createdAt;
    }

    public String getCreatedAt() {
        return DateFormat.getDateInstance().format(new Date(this.createdAt));
    }
}


