package com.example.wegive.fragments.myPosts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.wegive.models.post.Post;
import com.example.wegive.models.post.PostModel;
import com.example.wegive.models.user.User;

import java.util.List;

public class MyPostsViewModel extends ViewModel {
    private LiveData<List<Post>> myPosts = PostModel.getInstance().getAllUserPosts(User.getCurrentUser().getId());


    public LiveData<List<Post>> getMyPosts() {
        return myPosts;
    }

}