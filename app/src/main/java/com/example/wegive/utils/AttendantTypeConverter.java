package com.example.wegive.utils;


import androidx.room.TypeConverter;

import com.example.wegive.models.attendent.Attendant;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AttendantTypeConverter {

    @TypeConverter
    public String convertAttendantListToJSON(List<Attendant> attendantList) {
        return (new Gson()).toJson(attendantList);
    }

    @TypeConverter
    public List<Attendant> convertJSONStringToAttendantList(String jsonString) {
        Type attendantListType = new TypeToken<ArrayList<Attendant>>() {
        }.getType();
        return ((new Gson()).fromJson(jsonString, attendantListType));
    }
}
