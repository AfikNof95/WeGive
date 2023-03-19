package com.example.wegive.fragments.account;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wegive.R;
import com.example.wegive.databinding.FragmentAccountBinding;
import com.example.wegive.databinding.FragmentStaticAccountBinding;
import com.example.wegive.models.user.User;
import com.example.wegive.utils.SnackBarGlobal;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class StaticAccountFragment extends Fragment {

    StaticAccountViewModel viewModel;
    FragmentStaticAccountBinding binding;
    ActivityResultLauncher<Void> camera;
    ActivityResultLauncher<String> gallery;

    public StaticAccountFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        camera = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), result -> {
//            if (result != null) {
//                viewModel.isAvatarUpdated = true;
//                binding.userAvatar.setImageBitmap(result);
//                binding.userAvatarError.setVisibility(View.INVISIBLE);
//            }
//        });
//
//        gallery = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
//            if (result != null) {
//                viewModel.isAvatarUpdated = true;
//                binding.userAvatar.setImageURI(result);
//                binding.userAvatarError.setVisibility(View.INVISIBLE);
//            }
//        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        binding = FragmentAccountBinding.inflate(inflater, container, false);
//
//        User currentUser = viewModel.getCurrentUser();
//        binding.accountNameInput.setText(currentUser.getName());
//        binding.accountEmailInput.setText(currentUser.getEmail());
//        binding.accountPhoneInput.setText(currentUser.getPhoneNumber());
//        Picasso.get()
//                .load(currentUser.getAvatarUrl())
//                .placeholder(R.drawable.progress_animation)
//                .into(binding.userAvatar);
//
//
//        binding.userAvatar.setOnClickListener(v -> showUploadImageDialog());
//        binding.accountCancelButton.setOnClickListener((v) ->
//                Navigation.findNavController(v).popBackStack());
//        binding.accountSaveButton.setOnClickListener((v) -> {
//            if (validateForm()) {
//                viewModel.editUserDetails(
//                        Objects.toString(binding.accountNameInput.getText()).trim(),
//                        Objects.toString(binding.accountEmailInput.getText()).trim(),
//                        Objects.toString(binding.accountPhoneInput.getText()).trim(),
//                        ((BitmapDrawable) binding.userAvatar.getDrawable()).getBitmap(),
//                        (data) -> {
//                            Navigation.findNavController(v).popBackStack();
//                            SnackBarGlobal.make(
//                                    v,
//                                    getString(R.string.account_updated),
//                                    SnackBarGlobal.SEVERITY.SUCCESS
//                            );
//                        }
//                );
//            }
//        });
//
//        return binding.getRoot();
        return null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
//        viewModel = new ViewModelProvider(this).get(AccountViewModel.class);
//
//        FragmentActivity parentActivity = getActivity();
//        if (parentActivity != null) {
//            BottomNavigationView bottomNavigationView = parentActivity.findViewById(R.id.bottom_navigation);
//            if (bottomNavigationView != null) {
//                bottomNavigationView.setVisibility(View.GONE);
//            }
//        }
    }
}