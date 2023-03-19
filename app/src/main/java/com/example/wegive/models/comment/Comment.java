package com.example.wegive.models.comment;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Comment implements Serializable {

    public static final String ID = "id";
    public static final String USER_ID = "userId";
    public static final String POST_ID = "post_id";
    public static final String CONTENT = "content";
    public static final String USER_AVATAR = "userAvatar";
    public static final String USER_NAME = "userName";


    private String id;

    private String userId;

    private String postId;

    private String content;
    private String userAvatar;
    private String userName;


    public Comment(String id, @NonNull String userId, @NonNull String postId, String content, String userName, String userAvatar) {
        this.id = id == null ? UUID.randomUUID().toString() : id;
        this.userId = userId;
        this.postId = postId;
        this.content = content;
        this.userName = userName;
        this.userAvatar = userAvatar;
    }


    public static Comment fromJson(Map<String, Object> json) {
        String id = (String) json.get(ID);
        String userId = (String) json.get(USER_ID);
        String postId = (String) json.get(POST_ID);
        String content = (String) json.get(CONTENT);
        String userName = (String) json.get(USER_NAME);
        String userAvatar = (String) json.get(USER_AVATAR);
        Comment comment = new Comment(id, userId, postId, content, userName, userAvatar);
        return comment;

    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(ID, this.id);
        json.put(USER_ID, this.userId);
        json.put(POST_ID, this.postId);
        json.put(CONTENT, this.content);
        json.put(USER_NAME, this.userName);
        json.put(USER_AVATAR, this.userAvatar);
        return json;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

}
