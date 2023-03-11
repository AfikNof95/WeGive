package com.example.wegive.models.attendent;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.wegive.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
public class Attendant implements Serializable {

    public static final String COLLECTION = "attendant";
    public static final String ID = "id";
    public static final String USER_ID = "user_id";
    public static final String POST_ID = "post_id";
    public static final String ATTENDANT_NAME = "attendant_name";
    public static final String ATTENDANT_AVATAR = "attendant_avatar";

    public static final String ATTENDANT_LAST_UPDATED = "attendant_last_updated";

    public static final String ATTENDANT_LOCAL_LAST_UPDATED = "attendant_local_last_updated";

    @PrimaryKey
    @NotNull
    private String id;
    private String userId;
    private String postId;

    private String attendantAvatar;
    private String attendantName;
    public Long lastUpdated;


    public Attendant(String id, String userId, String postId, String attendantName, String attendantAvatar) {
        this.id = id == null ? UUID.randomUUID().toString() : id;
        this.userId = userId;
        this.postId = postId;
        this.attendantAvatar = attendantAvatar;
        this.attendantName = attendantName;
    }


    public static Attendant fromJson(Map<String, Object> json) {
        String id = (String) json.get(ID);
        String userId = (String) json.get(USER_ID);
        String postId = (String) json.get(POST_ID);
        String attendantName = (String) json.get(ATTENDANT_NAME);
        String attendantAvatar = (String) json.get(ATTENDANT_AVATAR);
        Attendant attendant = new Attendant(id, userId, postId, attendantName, attendantAvatar);

        Timestamp lastUpdated = (Timestamp) json.get(ATTENDANT_LAST_UPDATED);
        if (lastUpdated != null) {
            attendant.setLastUpdated(lastUpdated.getSeconds());
        }
        return attendant;

    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(ID, this.id);
        json.put(USER_ID, this.userId);
        json.put(POST_ID, this.postId);
        json.put(ATTENDANT_NAME, this.attendantName);
        json.put(ATTENDANT_AVATAR, this.attendantAvatar);
        json.put(ATTENDANT_LAST_UPDATED, FieldValue.serverTimestamp());
        return json;
    }

    public static long getLocalLastUpdated() {
        SharedPreferences sp = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        return sp.getLong(ATTENDANT_LOCAL_LAST_UPDATED, 0);
    }

    public static void setLocalLastUpdate(Long time) {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(ATTENDANT_LOCAL_LAST_UPDATED, time);
        editor.commit();
    }


    @NonNull
    public String getId() {
        return this.id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return this.postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getAttendantName() {
        return attendantName;
    }

    public void setAttendantName(String attendantName) {
        this.attendantName = attendantName;
    }

    public String getAttendantAvatar() {
        return attendantAvatar;
    }

    public void setAttendantAvatar(String attendantAvatar) {
        this.attendantAvatar = attendantAvatar;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Long getLastUpdated() {
        return this.lastUpdated;
    }


}
