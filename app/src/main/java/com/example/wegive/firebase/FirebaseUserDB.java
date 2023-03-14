package com.example.wegive.firebase;

import com.example.wegive.IListener;
import com.example.wegive.models.user.User;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class FirebaseUserDB extends FirebaseBaseDB {

    public void addUser(User user, IListener<Void> listener) {
        db.collection(User.COLLECTION).document(user.getId()).set(user.toJSON()).addOnCompleteListener(task -> {
            listener.onComplete(null);
        });
    }

    public void getUser(String userId, IListener<User> listener) {
        db.collection(User.COLLECTION).whereEqualTo(User.ID, userId).get()
                .addOnSuccessListener(task -> {
                    User response = User.fromJSON(Objects.requireNonNull(task.getDocuments().get(0).getData()));
                    listener.onComplete(response);
                });
    }

    public void getAllUsersSince(Long since, IListener<List<User>> listener) {
        db.collection(User.COLLECTION)
                .whereGreaterThanOrEqualTo(User.LAST_UPDATED, new Timestamp(since, 0))
                .get()
                .addOnCompleteListener(task -> {
                    List<User> list = new LinkedList<>();
                    if (task.isSuccessful()) {
                        QuerySnapshot jsonsList = task.getResult();
                        for (DocumentSnapshot json : jsonsList) {
                            User user = User.fromJSON(json.getData());
                            list.add(user);
                        }
                    }
                    listener.onComplete(list);
                });
    }

    public void getAllDeletedSince(Long since, IListener<List<String>> listener) {
        db.collection(User.COLLECTION)
                .addSnapshotListener((snapshots, e) -> {
                    List<String> list = new ArrayList<>();
                    if (snapshots != null) {
                        List<DocumentChange> changes = snapshots.getDocumentChanges();
                        for (DocumentChange dc : changes) {
                            if (dc.getType() == DocumentChange.Type.REMOVED) {
                                list.add(dc.getDocument().getId());
                            }
                        }
                    }
                    listener.onComplete(list);
                });
    }

}
