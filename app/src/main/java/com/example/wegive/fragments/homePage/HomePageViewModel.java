package com.example.wegive.fragments.homePage;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wegive.models.charity.Charity;
import com.example.wegive.models.charity.CharityAPIModel;
import com.example.wegive.models.post.Post;
import com.example.wegive.models.post.PostModel;
import com.example.wegive.models.user.User;
import com.example.wegive.models.user.UserModel;

import java.util.List;

public class HomePageViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private LiveData<List<Post>> posts = PostModel.getInstance().getAllPosts();

    private LiveData<List<User>> users = UserModel.instance().getAllUsers();


    public LiveData<List<Post>> getPosts() {
        return posts;
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }
}