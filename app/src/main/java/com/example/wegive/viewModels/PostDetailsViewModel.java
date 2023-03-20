package com.example.wegive.viewModels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.wegive.IListener;
import com.example.wegive.models.post.Post;
import com.example.wegive.models.post.PostModel;
import com.example.wegive.models.user.User;
import com.example.wegive.models.user.UserModel;

import java.util.List;

public class PostDetailsViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private LiveData<List<User>> users = UserModel.instance().getAllUsers();
    private LiveData<List<Post>> posts = PostModel.getInstance().getAllPosts();


    public void refreshUsers() {
        UserModel.instance().refreshAllUsers();
    }

    public void refreshPosts() {
        PostModel.getInstance().refreshAllPosts();
    }

    public void updatePost(Post post, IListener<Void> listener) {
        PostModel.getInstance().updatePost(post, listener);
    }

    public void deletePost(String postId, IListener<Void> listener) {
        PostModel.getInstance().deletePost(postId, listener);
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }

    public LiveData<List<Post>> getPosts() {
        return posts;
    }
}