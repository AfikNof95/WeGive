package com.example.wegive.models.post;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.wegive.IListener;
import com.example.wegive.firebase.FireBaseStorage;
import com.example.wegive.firebase.FirebasePostDB;
import com.example.wegive.models.AppLocalDB;
import com.example.wegive.models.AppLocalDbRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class PostModel {
    private static final PostModel _instance = new PostModel();

    private final Executor executor = Executors.newSingleThreadExecutor();

    private final FirebasePostDB db = new FirebasePostDB();
    private final FireBaseStorage storage = new FireBaseStorage();
    AppLocalDbRepository localDB = AppLocalDB.getAppDb();
    final public MutableLiveData<LoadingState> EventPostListLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);

    final public MutableLiveData<LoadingState> MyPostsListLoadingState = new MutableLiveData<>(LoadingState.NOT_LOADING);


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
    private LiveData<List<Post>> myPostsList;
    private LiveData<List<Post>> myEvents;


    public LiveData<List<Post>> getAllPosts() {
        if (postList == null) {
            postList = localDB.postDao().getAllPosts();
            refreshAllPosts();
        }
        return postList;
    }

    public LiveData<List<Post>> getAllPostsOrderedByDate() {
        if (myEvents == null) {
            myEvents = localDB.postDao().getAllPostsOrderedByDate();
            refreshAllPosts();
        }
        return myEvents;
    }

    public LiveData<List<Post>> getAllUserPosts(String userId) {
        myPostsList = localDB.postDao().getAllPostsByUserId(userId);
        refreshAllPosts();
        return myPostsList;
    }





    public void refreshAllPosts() {
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
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Post.setLocalLastUpdate(time);
                EventPostListLoadingState.postValue(LoadingState.NOT_LOADING);
            });
        });

        db.getAllDeletedSince(localLastUpdate, response -> {
            executor.execute(() -> {
                for (String id : response) {
                    localDB.postDao().deletePostById(id);
                }
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
        db.deletePost(postId, data -> {
            listener.onComplete(null);
            refreshAllPosts();
        });
    }

    public void uploadPostImage(String id, Bitmap bitmap, IListener<String> listener) {
        storage.uploadPostImage(id, bitmap, listener);
    }

    public void deletePostImage(String id, IListener<Void> listener) {
        storage.deletePostImage(id, listener);
    }


}
