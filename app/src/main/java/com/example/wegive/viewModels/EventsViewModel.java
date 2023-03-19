package com.example.wegive.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.wegive.models.post.Post;
import com.example.wegive.models.post.PostModel;
import com.example.wegive.models.user.User;

import java.util.List;

public class EventsViewModel extends ViewModel {
    private LiveData<List<Post>> events = PostModel.getInstance().getAllPosts();


    public LiveData<List<Post>> getEvents() {
        return events;
    }

}