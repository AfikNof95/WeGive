package com.example.wegive.fragments.account;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
    ActivityResultLauncher<Void> camera;
    ActivityResultLauncher<String> gallery;
    AlertDialog imageUploadDialog;

    public AccountFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        camera = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), result -> {
            if (result != null) {
                binding.userAvatar.setImageBitmap(result);
                binding.userAvatarError.setVisibility(View.INVISIBLE);
            }
        });

        gallery = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if (result != null) {
                binding.userAvatar.setImageURI(result);
                binding.userAvatarError.setVisibility(View.INVISIBLE);
            }
        });
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


        binding.userAvatar.setOnClickListener(v -> showUploadImageDialog());
        binding.accountCancelButton.setOnClickListener((v) ->
                Navigation.findNavController(v).popBackStack());

        return binding.getRoot();
    }

    private void showUploadImageDialog() {
        if (imageUploadDialog != null) {
            imageUploadDialog.show();
            return;
        }

        FragmentActivity parentActivity = getActivity();
        if (parentActivity != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Upload Profile Picture");
            builder.setPositiveButton("Camera", (dialogInterface, i) -> {
                camera.launch(null);
            });
            builder.setNegativeButton("Gallery", (dialogInterface, i) -> {
                gallery.launch("image/*");
            });
            imageUploadDialog = builder.create();
            imageUploadDialog.show();
        }
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