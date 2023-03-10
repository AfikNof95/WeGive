package com.example.wegive.models.post;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.wegive.MyApplication;
import com.example.wegive.models.user.User;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Post implements Serializable, Comparable<Post> {

    public static final String COLLECTION = "posts";
    static final String ID = "id";
    static final String TITLE = "title";
    static final String CONTENT = "content";
    static final String TIME = "time";
    static final String IMAGE_URL = "image_url";
    static final String CREATOR_NAME = "creator_name";
    static final String CREATOR_ID = "creator_id";
    static final String PARTICIPANTS = "participants";
    public static final String LAST_UPDATED = "last_updated";
    static final String LOCAL_LAST_UPDATED = "last_updated";


    @PrimaryKey
    @NotNull
    public String id;
    public String title;
    public String content;
    public String time;
    public String imageUrl;
    public String creatorName;
    public String creatorId;
    public String participants;
    public Long lastUpdated;

    public Post() {
        this.id = UUID.randomUUID().toString();
    }

    public Post( String title, String content, String time, String imageUrl, String creatorName, String creatorId, String participants) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.content = content;
        this.time = time;
        this.imageUrl = imageUrl;
        this.creatorName = creatorName;
        this.creatorId = creatorId;
        this.participants = participants;
    }


    public static Post fromJson(Map<String, Object> json) {
        String id = (String) json.get(ID);
        String title = (String) json.get(TITLE);
        String content = (String) json.get(CONTENT);
        String time = (String) json.get(TIME);
        String imageUrl = (String) json.get(IMAGE_URL);
        String creatorName = (String) json.get(CREATOR_NAME);
        String creatorId = (String) json.get(CREATOR_ID);
        String participants = (String) json.get(PARTICIPANTS);
        Post post = new Post(title, content, time, imageUrl, creatorName, creatorId, participants);
        Timestamp lastUpdated = (Timestamp) json.get(LAST_UPDATED);
        if (lastUpdated != null) {
            post.setLastUpdated(lastUpdated.getSeconds());
        }


        return post;
    }

    public Map<String,Object> toJson(){
        Map<String,Object> json = new HashMap<>();
        json.put(ID,this.id);
        json.put(TITLE,this.title);
        json.put(CONTENT,this.content);
        json.put(TIME,this.time);
        json.put(IMAGE_URL,this.imageUrl);
        json.put(CREATOR_NAME,this.creatorName);
        json.put(CREATOR_ID,this.creatorId);
        json.put(PARTICIPANTS,this.participants);
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


    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
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

    public String getParticipants() {
        return this.participants;
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

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }


    @Override
    public int compareTo(Post post) {
        return (Objects.nonNull(post.lastUpdated)) ? getLastUpdated().compareTo(post.lastUpdated) : 0;
    }
}
