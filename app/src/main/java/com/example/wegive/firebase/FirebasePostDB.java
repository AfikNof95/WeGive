package com.example.wegive.firebase;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.example.wegive.IListener;
import com.example.wegive.models.post.Post;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class FirebasePostDB extends FirebaseBaseDB {

    public void getAllPostsSince(Long since, IListener<List<Post>> callback) {
        db.collection(Post.COLLECTION)
                .whereGreaterThanOrEqualTo(Post.LAST_UPDATED, new Timestamp(since, 0))
                .get()
                .addOnCompleteListener(task -> {
                    List<Post> list = new LinkedList<>();
                    if (task.isSuccessful()) {
                        QuerySnapshot jsonsList = task.getResult();
                        for (DocumentSnapshot json : jsonsList) {
                            Post post = Post.fromJson(json.getData());
                            list.add(post);
                        }
                    }
                    callback.onComplete(list);
                });

    }

    public void getAllDeletedSince(Long since, IListener<List<Post>> callback) {
        List<Post> list = new LinkedList<>();
        db.collection(Post.COLLECTION)
                .whereGreaterThanOrEqualTo(Post.LAST_UPDATED, new Timestamp(since, 0))
                .addSnapshotListener((snapshots, e) -> {
                    for (DocumentChange dc : snapshots.getDocumentChanges()) {
                        switch (dc.getType()) {
                            case REMOVED:
                                Log.d(TAG, "Removed city: " + dc.getDocument().getData());
                                System.out.println(Post.fromJson(dc.getDocument().getData()));
                                list.add(Post.fromJson(dc.getDocument().getData()));
                                break;
                        }
                    }

                    callback.onComplete(list);
                });
    }

    public void addPost(Post post, IListener<Void> listener) {
        db.collection(Post.COLLECTION).document(post.getId()).set(post.toJson())
                .addOnCompleteListener(task -> listener.onComplete(null));
    }

    public void deletePost(String postId, IListener<Void> listener) {
        db.collection(Post.COLLECTION).document(postId).delete()
                .addOnCompleteListener(task -> listener.onComplete(null));
    }

    public void getPostById(String id, IListener<Post> callback) {
        db.collection(Post.COLLECTION).whereEqualTo("id", id).get()
                .addOnSuccessListener(task -> {
                    Post post = Post.fromJson(Objects.requireNonNull(task.getDocuments().get(0).getData()));

                    callback.onComplete(post);
                });
    }

}
