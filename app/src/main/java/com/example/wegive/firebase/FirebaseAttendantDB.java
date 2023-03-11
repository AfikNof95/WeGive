package com.example.wegive.firebase;

import com.example.wegive.IListener;
import com.example.wegive.models.attendent.Attendant;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class FirebaseAttendantDB extends FirebaseBaseDB {

    public void getAllSince(Long since, IListener<List<Attendant>> callback) {
        db.collection(Attendant.COLLECTION)
                .whereGreaterThanOrEqualTo(Attendant.ATTENDANT_LAST_UPDATED, new Timestamp(since, 0))
                .get()
                .addOnCompleteListener(task -> {
                    List<Attendant> list = new LinkedList<>();
                    if (task.isSuccessful()) {
                        QuerySnapshot jsonsList = task.getResult();
                        for (DocumentSnapshot json : jsonsList) {
                            Attendant attendant = Attendant.fromJson(json.getData());
                            list.add(attendant);
                        }
                    }
                    callback.onComplete(list);
                });

    }

    public void getAllDeletedSince(Long since, IListener<List<String>> callback) {

        db.collection(Attendant.COLLECTION)
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
                    callback.onComplete(list);
                });
    }

    public void add(Attendant attendant, IListener<Void> listener) {
        db.collection(Attendant.COLLECTION).document(attendant.getId()).set(attendant.toJson())
                .addOnCompleteListener(task -> listener.onComplete(null));
    }

    public void delete(String attendantId, IListener<Void> listener) {
        db.collection(Attendant.COLLECTION).document(attendantId).delete()
                .addOnCompleteListener(task -> listener.onComplete(null));
    }

    public void getById(String id, IListener<Attendant> callback) {
        db.collection(Attendant.COLLECTION).whereEqualTo("id", id).get()
                .addOnSuccessListener(task -> {
                    Attendant attendant = Attendant.fromJson(Objects.requireNonNull(task.getDocuments().get(0).getData()));

                    callback.onComplete(attendant);
                });
    }

}
