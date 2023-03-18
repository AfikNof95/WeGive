package com.example.wegive.fragments.account;

import androidx.lifecycle.ViewModel;

import com.example.wegive.models.user.User;

public class AccountViewModel extends ViewModel {

    public User currentUser = User.getCurrentUser();
}
