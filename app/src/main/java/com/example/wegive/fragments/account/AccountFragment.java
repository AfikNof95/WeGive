package com.example.wegive.fragments.account;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.wegive.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AccountFragment extends Fragment {

    AccountViewModel viewModel;

    public AccountFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(AccountViewModel.class);

        FragmentActivity parentActivity = getActivity();
        if (parentActivity != null) {
            BottomNavigationView bottomNavigationView = parentActivity.findViewById(R.id.bottom_navigation);
            if(bottomNavigationView != null) {
                bottomNavigationView.setVisibility(View.GONE);
            }
        }
    }
}