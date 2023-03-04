package com.example.wegive.fragments.auth;

import android.os.Bundle;

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
import com.example.wegive.models.user.UserModel;
import com.example.wegive.utils.ProgressDialogGlobal;
import com.example.wegive.utils.SnackBarGlobal;
import com.google.android.material.snackbar.Snackbar;


public class SignUpFragment extends Fragment {

    FragmentSignUpBinding binding;
    View view;
    // TODO: Rename and change types of parameters


    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MainActivity activity = (MainActivity) getActivity();
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        view = binding.getRoot();

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
    }

    private void handleSignUp() {
        if (validateForm()) {
            ProgressDialogGlobal pg = ProgressDialogGlobal.getInstance();
            pg.show(view,getString(R.string.sign_up_loading_message));
            String email = binding.emailInput.getText().toString();
            String password = binding.passwordInput.getText().toString();
            UserModel.instance().signUp(email, password, task -> {
                Log.d("TAG", "Test Register");
                if (task.isSuccessful()) {

                } else {
                    SnackBarGlobal.make(view, task.getException().getMessage(), SnackBarGlobal.SEVERITY.ERROR);
                }
            });
        }
    }

    private boolean validateForm() {
        String email = binding.emailInput.getText().toString();
        String password = binding.emailInput.getText().toString();
        boolean isValid = true;
        if (email.trim().equals("")) {
            binding.emailInputLayout.setError(getString(R.string.empty_email_error));
            isValid = false;
        }
        if (password.trim().equals("")) {
            binding.passwordInputLayout.setError(getString(R.string.empty_password_error));
            isValid = false;
        }
        return isValid;
    }
}