package com.example.wegive.fragments.account;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.wegive.R;
import com.example.wegive.databinding.FragmentStaticAccountBinding;
import com.example.wegive.models.user.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

public class StaticAccountFragment extends Fragment {

    StaticAccountViewModel viewModel;
    FragmentStaticAccountBinding binding;

    public StaticAccountFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStaticAccountBinding.inflate(inflater, container, false);

        User currentUser = viewModel.getCurrentUser();
        binding.staticAccountNameInputText.setText(currentUser.getName());
        binding.staticAccountEmailInputText.setText(currentUser.getEmail());
        binding.staticAccountPhoneInputText.setText(currentUser.getPhoneNumber());
        Picasso.get()
                .load(currentUser.getAvatarUrl())
                .placeholder(R.drawable.progress_animation)
                .into(binding.userAvatar);


        binding.staticAccountCancelButton.setOnClickListener((v) ->
                Navigation.findNavController(v).popBackStack());
        binding.staticAccountSaveButton.setOnClickListener((v) ->
                Navigation
                        .findNavController(v)
                        .navigate(
                                com.example.wegive.fragments.account.StaticAccountFragmentDirections.actionStaticAccountFragmentToAccountFragment()
                        )
        );

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(StaticAccountViewModel.class);

        FragmentActivity parentActivity = getActivity();
        if (parentActivity != null) {
            BottomNavigationView bottomNavigationView = parentActivity.findViewById(R.id.bottom_navigation);
            if (bottomNavigationView != null) {
                bottomNavigationView.setVisibility(View.GONE);
            }
        }
    }
}