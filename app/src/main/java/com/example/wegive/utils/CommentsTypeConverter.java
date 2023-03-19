package com.example.wegive.utils;


import androidx.room.TypeConverter;

import com.example.wegive.models.comment.Comment;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CommentsTypeConverter {

    @TypeConverter
    public String convertCommentListToJSON(List<Comment> commentList) {
        return (new Gson()).toJson(commentList);
    }

    @TypeConverter
    public List<Comment> convertJSONStringToCommentList(String jsonString) {
        Type commentListType = new TypeToken<ArrayList<Comment>>() {
        }.getType();
        return ((new Gson()).fromJson(jsonString, commentListType));
    }
}
