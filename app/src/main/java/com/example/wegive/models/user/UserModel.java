package com.example.wegive.models.user;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.wegive.IListener;
import com.example.wegive.firebase.FireBaseStorage;
import com.example.wegive.firebase.FirebaseUserDB;
import com.example.wegive.firebase.FireBaseAuth;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserModel {

    public static class UserModelHolder {
        public static final UserModel _instance = new UserModel();
    }


    public enum LoadingState {
        LOADING,
        NOT_LOADING
    }

//    final public MutableLiveData<LoadingState> UsersLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);
//    private LiveData<List<User>> userList;
//    private ExecutorService es = Executors.newSingleThreadExecutor();
//    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private FirebaseUserDB userDB = new FirebaseUserDB();
    private FireBaseAuth fireBaseAuth = new FireBaseAuth();

    private FireBaseStorage storage = new FireBaseStorage();

    public static UserModel instance() {
        return UserModelHolder._instance;
    }


    private UserModel() {
    }

//    public LiveData<List<User>> getAllUsers() {
//        return userList;
//    }




    public void signUp(String email, String password, IListener<Task<AuthResult>> listener) {

        fireBaseAuth.signUp(email, password, listener);

    }

    public void signIn(String email,String password,IListener<Task<AuthResult>> listener){
    fireBaseAuth.signIn(email,password,listener);
    }

    public void uploadUserAvatar(String userId, Bitmap bitmap,IListener<String> listener){
        storage.uploadUserAvatar(userId,bitmap,listener);
    }

    public void addUser(User user,IListener<Void> listener){
    userDB.addUser(user,listener);
    }

    public void getUser(String userId,IListener<User> listener){
        userDB.getUser(userId,listener);
    }


}
