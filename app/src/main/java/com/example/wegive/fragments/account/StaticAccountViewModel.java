package com.example.wegive.fragments.account;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wegive.IListener;
import com.example.wegive.R;
import com.example.wegive.models.user.User;
import com.example.wegive.models.user.UserModel;

public class StaticAccountViewModel extends Fragment {

    public User getCurrentUser() {
        return User.getCurrentUser();
    }

}