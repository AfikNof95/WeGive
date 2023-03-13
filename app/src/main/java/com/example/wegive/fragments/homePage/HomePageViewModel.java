package com.example.wegive.fragments.homePage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.wegive.models.post.Post;
import com.example.wegive.models.post.PostModel;


import java.util.List;

public class HomePageViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private LiveData<List<Post>> posts = PostModel.getInstance().getAllPosts();


    public LiveData<List<Post>> getPosts() {
        return posts;
    }
}