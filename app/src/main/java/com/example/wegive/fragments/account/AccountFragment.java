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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.wegive.R;
import com.example.wegive.databinding.FragmentAccountBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

public class AccountFragment extends Fragment {

    private NavController navController;

    AccountViewModel viewModel;
    FragmentAccountBinding binding;

    public AccountFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);

        binding.accountNameInput.setText(viewModel.currentUser.getName());
        binding.accountEmailInput.setText(viewModel.currentUser.getEmail());
        binding.accountPhoneInput.setText(viewModel.currentUser.getPhoneNumber());
        Picasso.get()
                .load(viewModel.currentUser.getAvatarUrl())
                .placeholder(R.drawable.progress_animation)
                .into(binding.userAvatar);


        binding.accountCancelButton.setOnClickListener((v) ->
                Navigation.findNavController(v).popBackStack());

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(AccountViewModel.class);

        FragmentActivity parentActivity = getActivity();
        if (parentActivity != null) {
            BottomNavigationView bottomNavigationView = parentActivity.findViewById(R.id.bottom_navigation);
            if (bottomNavigationView != null) {
                bottomNavigationView.setVisibility(View.GONE);
            }
        }
    }
}