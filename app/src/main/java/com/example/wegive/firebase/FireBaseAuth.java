package com.example.wegive.firebase;

import com.example.wegive.IListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class FireBaseAuth {

    FirebaseAuth auth;


    public FireBaseAuth() {
        auth = FirebaseAuth.getInstance();
    }


    public void signUp(String email, String password, IListener<Task> listener){
    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task->{
        listener.onComplete(task);
    });
    }
}
