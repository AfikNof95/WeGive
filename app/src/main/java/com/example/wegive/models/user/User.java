package com.example.wegive.models.user;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


import com.example.wegive.MyApplication;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Entity
public class User {

    public static final String COLLECTION = "users";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String AVATAR_URL = "avatar_url";
    public static final String IS_VERIFIED = "is_verified";
    public static final String IS_ADMIN = "is_admin";
    public static final String LAST_UPDATED = "users_last_updated";
    public static final String LOCAL_LAST_UPDATED = "users_local_last_updated";


    @PrimaryKey
    @NotNull
    public String id = "";

    public String name = "";
    public String email = "";

    public String phoneNumber = "";
    public String avatarUrl = "";
    public Boolean isVerified = false;
    public Boolean isAdmin = false;
    public long lastUpdated;



    public User(@NonNull String id, String name, String email, String phoneNumber, String avatarUrl, Boolean isVerified, Boolean isAdmin) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.avatarUrl = avatarUrl;
        this.isVerified = isVerified;
        this.isAdmin = isAdmin;
    }


    public Map<String, Object> toJSON() {
        Map<String, Object> json = new HashMap<>();
        json.put(ID, getId());
        json.put(NAME, getName());
        json.put(EMAIL, getEmail());
        json.put(PHONE_NUMBER, getPhoneNumber());
        json.put(AVATAR_URL, getAvatarUrl());
        json.put(IS_VERIFIED, getIsVerified());
        json.put(IS_ADMIN, getIsAdmin());
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());

        return json;
    }


    public static User fromJSON(Map<String, Object> json) {
        String id = (String) json.get(ID);
        String name = (String) json.get(NAME);
        String email = (String) json.get(EMAIL);
        String phoneNumber = (String) json.get(PHONE_NUMBER);
        String avatarUrl = (String) json.get(AVATAR_URL);
        Boolean isVerified = (Boolean) json.get(IS_VERIFIED);
        Boolean isAdmin = (Boolean) json.get(IS_ADMIN);
        User user = new User(id, name, email, phoneNumber, avatarUrl, isVerified, isAdmin);

        Timestamp time = (Timestamp) json.get(LAST_UPDATED);
        if (time != null) {
            user.setLastUpdated(time.getSeconds());
        }


        return user;
    }



    public Long getLastUpdated(){
        return this.lastUpdated;
    }


    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }


    public static void setCurrentUser(User user) {
        SharedPreferences sp = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        editor.putString("user", gson.toJson(user));
        editor.commit();
    }

    public static User getCurrentUser() {
        SharedPreferences sp = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("user", "");
        return gson.fromJson(json, User.class);
    }

    public static void signOut() {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("user");
        editor.commit();
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean getIsVerified() {
        return this.isVerified;
    }

    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public boolean getIsAdmin() {
        return this.isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }


}
