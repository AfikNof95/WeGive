package com.example.wegive.firebase;

import androidx.annotation.NonNull;

import com.example.wegive.IListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executor;

public class FireBaseAuth {

    FirebaseAuth auth;


    public FireBaseAuth() {
        auth = FirebaseAuth.getInstance();
    }


    public void signUp(String email, String password, IListener<Task<AuthResult>> listener) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            listener.onComplete(task);
        });
    }
}
