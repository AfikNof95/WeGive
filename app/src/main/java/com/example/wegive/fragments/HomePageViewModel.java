package com.example.wegive.fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wegive.models.post.Post;
import com.example.wegive.models.post.PostModel;

import java.util.List;

public class HomePageViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private LiveData<List<Post>> data = PostModel.getInstance().getAllPosts();

    public LiveData<List<Post>> getData() {
        return data;
    }
}