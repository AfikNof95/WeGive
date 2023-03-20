package com.example.wegive.viewModels;


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

    public void refreshUsers() {
        UserModel.instance().refreshAllUsers();
    }

    public void refreshPosts() {
        PostModel.getInstance().refreshAllPosts();
    }

    public MutableLiveData<PostModel.LoadingState> getPostsLoadingState(){
        return PostModel.getInstance().EventPostListLoadingState;
    }


    public LiveData<List<Post>> getPosts() {
        return posts;
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }
}