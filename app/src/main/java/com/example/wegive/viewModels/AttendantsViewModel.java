package com.example.wegive.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.wegive.models.post.Post;
import com.example.wegive.models.post.PostModel;
import com.example.wegive.models.user.User;
import com.example.wegive.models.user.UserModel;

import java.util.List;

public class AttendantsViewModel extends ViewModel {

    LiveData<List<User>> users = UserModel.instance().getAllUsers();

    LiveData<List<Post>> posts = PostModel.getInstance().getAllPosts();

    public LiveData<List<User>> getData(Post post) {
        return users;
    }

    public LiveData<List<Post>> getPosts() {
        return posts;
    }
}
