package com.example.wegive.firebase;

import com.example.wegive.IListener;
import com.example.wegive.models.user.User;

import java.util.Objects;

public class FirebaseUserDB extends FirebaseBaseDB {

    public void addUser(User user, IListener<Void> listener) {
        db.collection(User.COLLECTION).document(user.getId()).set(user.toJSON()).addOnCompleteListener(task -> {
            listener.onComplete(null);
        });
    }

    public void getUser(String email, IListener<User> listener) {
        db.collection(User.COLLECTION).whereEqualTo(User.EMAIL, email).get()
                .addOnSuccessListener(task -> {
                    User response = User.fromJSON(Objects.requireNonNull(task.getDocuments().get(0).getData()));
                    listener.onComplete(response);
                });
    }

}
