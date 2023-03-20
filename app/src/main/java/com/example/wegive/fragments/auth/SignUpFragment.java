package com.example.wegive.fragments.auth;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.wegive.MainActivity;
import com.example.wegive.R;
import com.example.wegive.databinding.FragmentLoginBinding;
import com.example.wegive.databinding.FragmentSignUpBinding;
import com.example.wegive.models.user.User;
import com.example.wegive.models.user.UserModel;
import com.example.wegive.utils.ProgressDialogGlobal;
import com.example.wegive.utils.SnackBarGlobal;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;


public class SignUpFragment extends Fragment {

    FragmentSignUpBinding binding;
    View view;
    ActivityResultLauncher<Void> camera;
    ActivityResultLauncher<String> gallery;
    AlertDialog imageUploadDialog;


    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        camera = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), result -> {
            if (result != null) {
                binding.signUpUserAvatar.setImageBitmap(result);
                binding.userAvatarError.setVisibility(View.INVISIBLE);
            }
        });

        gallery = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if (result != null) {
                binding.signUpUserAvatar.setImageURI(result);
                binding.userAvatarError.setVisibility(View.INVISIBLE);
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.hide();
        this.registerInputEvents();


        binding.existingUserSignIn.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment());
        });


        binding.signUpButton.setOnClickListener(view1 -> handleSignUp());


        // Inflate the layout for this fragment
        return view;
    }

    private void registerInputEvents() {
        binding.passwordInput.setOnKeyListener((view1, i, keyEvent) -> {
            binding.passwordInputLayout.setError(null);
            return false;
        });
        binding.emailInput.setOnKeyListener((view1, i, keyEvent) -> {
            binding.emailInputLayout.setError(null);
            return false;
        });
        binding.signUpNameInput.setOnKeyListener((view1, i, keyEvent) -> {
            binding.signUpNameInputLayout.setError(null);
            return false;
        });
        binding.signUpPhoneInput.setOnKeyListener((view1, i, keyEvent) -> {
            binding.signUpPhoneInputLayout.setError(null);
            return false;
        });
        binding.signUpUserAvatar.setOnClickListener(view1 -> {
            showUploadImageDialog();
        });

    }

    private void showUploadImageDialog() {
        if (imageUploadDialog != null) {
            imageUploadDialog.show();
            return;
        }
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

    private void handleSignUp() {
        if (validateForm()) {
            ProgressDialogGlobal pg = ProgressDialogGlobal.getInstance();
            pg.show(view, getString(R.string.sign_up_loading_message));
            String email = binding.emailInput.getText().toString();
            String password = binding.passwordInput.getText().toString();
            UserModel.instance().signUp(email, password, task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = task.getResult().getUser();
                    String userId = user.getUid();
                    Bitmap userAvatar = ((BitmapDrawable) binding.signUpUserAvatar.getDrawable()).getBitmap();
                    UserModel.instance().uploadUserAvatar(userId, userAvatar, data -> {
                        String name = binding.signUpNameInput.getText().toString();
                        String phoneNumber = binding.signUpPhoneInput.getText().toString();
                        User newUser = new User(userId, name, email, phoneNumber, data, false, false);
                        UserModel.instance().addUser(newUser, data1 -> {
                            pg.hide();
                            Navigation.findNavController(view).navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment());
                            SnackBarGlobal.make(view,getString(R.string.sign_up_welcome), SnackBarGlobal.SEVERITY.SUCCESS);
                        });
                        Log.d("TAG", "test");
                    });
                } else {
                    pg.hide();
                    binding.signUpErrorLabel.setText(task.getException().getMessage());
                    binding.signUpErrorLabel.setVisibility(View.VISIBLE);
                }


            });
        }
    }

    private boolean validateForm() {
        String email = binding.emailInput.getText().toString();
        String password = binding.passwordInput.getText().toString();
        String phoneNumber = binding.signUpPhoneInput.getText().toString();
        String fullName = binding.signUpNameInput.getText().toString();
        Drawable userAvatar = binding.signUpUserAvatar.getDrawable();
        boolean isValid = true;
        if (email.trim().equals("")) {
            binding.emailInputLayout.setError(getString(R.string.empty_email_error));
            isValid = false;
        }
        if (password.trim().equals("")) {
            binding.passwordInputLayout.setError(getString(R.string.empty_password_error));
            isValid = false;
        }
        if (fullName.trim().equals("")) {
            binding.signUpNameInputLayout.setError(getString(R.string.empty_name_error));
            isValid = false;
        }
        if (phoneNumber.trim().equals("")) {
            binding.signUpPhoneInputLayout.setError(getString(R.string.empty_phone_number_error));
            isValid = false;
        }
        if(userAvatar instanceof  VectorDrawable || userAvatar == null){
            binding.userAvatarError.setVisibility(View.VISIBLE);
            isValid = false;
        }
        return isValid;
    }



}