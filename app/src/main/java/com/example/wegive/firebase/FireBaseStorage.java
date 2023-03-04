package com.example.wegive.firebase;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.wegive.IListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class FireBaseStorage {
    FirebaseStorage storage;

    public FireBaseStorage() {
        storage = FirebaseStorage.getInstance();
    }


    public void uploadImage(String path, Bitmap image, IListener<String> listener) {
        StorageReference sf = storage.getReference();
        StorageReference imageRef = sf.child("images/" + path + ".jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        listener.onComplete(uri.toString());
                    }
                });
            }
        });
    }

    public void uploadUserAvatar(String userId, Bitmap avatarBitmap, IListener<String> listener) {
        String path = "users/" + userId;
        uploadImage(path, avatarBitmap, listener);
    }
}
