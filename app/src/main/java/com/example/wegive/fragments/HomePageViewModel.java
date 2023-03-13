package com.example.wegive.fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.wegive.models.attendent.Attendant;
import com.example.wegive.models.attendent.AttendantModel;
import com.example.wegive.models.post.Post;
//import com.example.wegive.models.postAttendantPair.PostAttendantPair;
import com.example.wegive.models.post.PostModel;
import com.example.wegive.models.postAttendantPair.PostAttendantPair;

import java.util.List;

public class HomePageViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private LiveData<List<Post>> posts = PostModel.getInstance().getAllPosts();


    public LiveData<List<Post>> getPosts() {
        return posts;
    }
}