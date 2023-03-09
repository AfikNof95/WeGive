package com.example.wegive.models.post;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.wegive.IListener;
import com.example.wegive.firebase.FireBaseStorage;
import com.example.wegive.firebase.FirebasePostDB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostModel {
    private static final PostModel _instance = new PostModel();

    private final Executor executor = Executors.newSingleThreadExecutor();

    private final FirebasePostDB db = new FirebasePostDB();
    private final FireBaseStorage storage = new FireBaseStorage();
    AppLocalDbRepository localDB = AppLocalDB.getAppDb();
    final public MutableLiveData<LoadingState> EventPostListLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);


    public static PostModel getInstance() {
        return _instance;
    }

    private PostModel() {

    }


    public enum LoadingState {
        LOADING,
        NOT_LOADING
    }


    private LiveData<List<Post>> postList;

    public LiveData<List<Post>> getAllPosts() {
        if (postList == null) {
            postList = localDB.postDao().getAll();
            refreshAllPosts();
        }
        return postList;
    }


    private void refreshAllPosts() {
        EventPostListLoadingState.setValue(LoadingState.LOADING);
        Long localLastUpdate = Post.getLocalLastUpdated();
        db.getAllPostsSince(localLastUpdate, response -> {
            executor.execute(() -> {
                Long time = localLastUpdate;
                for (Post post : response) {
                    post.setImageUrl(post.getImageUrl());
                    // insert new records into ROOM
                    localDB.postDao().insertAll(post);
                    if (time < post.getLastUpdated()) {
                        time = post.getLastUpdated();
                    }
                }

                Post.setLocalLastUpdate(time);
                EventPostListLoadingState.postValue(LoadingState.NOT_LOADING);
            });
        });
    }


    public void addPost(Post post, IListener<Void> listener) {
        db.addPost(post, (Void) -> {
            refreshAllPosts();
            listener.onComplete(null);
        });
    }

    public void updatePost(Post post, IListener<Void> listener) {
        addPost(post, listener);
    }

    public void deletePost(String postId, IListener<Void> listener) {
        db.deletePost(postId, listener);
    }

    public void uploadPostImage(String id, Bitmap bitmap, IListener<String> listener) {
        storage.uploadPostImage(id, bitmap, listener);
    }

    public void deletePostImage(String id, IListener<Void> listener) {
        storage.deletePostImage(id, listener);
    }


}
