package com.example.wegive.fragments.account;

import android.graphics.Bitmap;

import androidx.lifecycle.ViewModel;

import com.example.wegive.IListener;
import com.example.wegive.models.user.User;
import com.example.wegive.models.user.UserModel;

public class AccountViewModel extends ViewModel {

    public User getCurrentUser() {
        return User.getCurrentUser();
    }

    public boolean isAvatarUpdated = false;

    public void editUserDetails(
            String name,
            String email,
            String phoneNumber,
            Bitmap userAvatar,
            IListener<Void> onSuccess
    ) {
        User currentUser = getCurrentUser();
        if (isAvatarUpdated) {
            UserModel.instance().uploadUserAvatar(currentUser.id, userAvatar, avatarUrl ->
                    updateUser(name, email, phoneNumber, avatarUrl, onSuccess));
        } else {
            updateUser(name, email, phoneNumber, currentUser.avatarUrl, onSuccess);
        }
    }

    public void updateUser(
            String name,
            String email,
            String phoneNumber,
            String avatarUrl,
            IListener<Void> onSuccess
    ) {
        User currentUser = getCurrentUser();
        User userToUpdate = new User(
                currentUser.id, name, email, phoneNumber, avatarUrl, currentUser.isVerified, currentUser.isAdmin
        );
        UserModel.instance().addUser(userToUpdate, data -> {
            User.setCurrentUser(userToUpdate);
            onSuccess.onComplete(data);
        });
    }
}
