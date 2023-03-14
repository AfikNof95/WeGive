package com.example.wegive.fragments.auth;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wegive.MainActivity;
import com.example.wegive.R;
import com.example.wegive.databinding.FragmentLoginBinding;
import com.example.wegive.models.user.User;
import com.example.wegive.models.user.UserModel;
import com.example.wegive.utils.ErrorHandler;
import com.example.wegive.utils.ProgressDialogGlobal;
import com.example.wegive.utils.SnackBarGlobal;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;


public class LoginFragment extends Fragment {

    FragmentLoginBinding binding;
    View view;

    NavController navController;
    // TODO: Rename and change types of parameters


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        view = binding.getRoot();

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.hide();
        BottomNavigationView bottomNavigationView = ((AppCompatActivity) getActivity()).findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.GONE);

        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        registerInputEvents();


        binding.newUserRegister.setOnClickListener(view1 -> {
            navController.navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment());
        });

        binding.signInButton.setOnClickListener(view1 -> {
        handleSignIn();
        });


        return view;
    }


    private void handleSignIn() {
        String email = binding.emailInput.getText().toString();
        String password = binding.passwordInput.getText().toString();
        if (validateForm()) {
            ProgressDialogGlobal pg = ProgressDialogGlobal.getInstance();
            pg.show(view, getString(R.string.sign_in_loading_message));

            UserModel.instance().signIn(email, password, data -> {
                if (data.isSuccessful()) {
                    FirebaseUser fbUser = data.getResult().getUser();
                    UserModel.instance().getUser(fbUser.getUid(), user -> {
                        User.setCurrentUser(user);
                        SnackBarGlobal.make(view, getString(R.string.sign_in_success), SnackBarGlobal.SEVERITY.SUCCESS);
                        navController.navigate(LoginFragmentDirections.actionLoginFragmentToHomePageFragment());
                        getActivity().invalidateOptionsMenu();

                    });
                } else {
                    if (data.getException() instanceof FirebaseAuthException) {
                        try {
                            FirebaseAuthException error = (FirebaseAuthException) (data.getException());
                            Context context = requireContext();
                            binding.signInErrorLabel.setText(ErrorHandler.getAuthErrorMessage(context, error));
                        } catch (Exception ex) {
                            binding.signInErrorLabel.setText(getString(R.string.generic_auth_exception));
                        }

                    }

                    binding.signInErrorLabel.setVisibility(View.VISIBLE);
                }
                pg.hide();
            });
        }

    }

    private boolean validateForm() {
        String email = binding.emailInput.getText().toString();
        String password = binding.passwordInput.getText().toString();
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

    private void registerInputEvents() {
        binding.passwordInput.setOnKeyListener((view1, i, keyEvent) -> {
            if (binding.passwordInputLayout.getError() != null) {
                binding.passwordInputLayout.setError(null);
            }

            return false;
        });
        binding.emailInput.setOnKeyListener((view1, i, keyEvent) -> {
            if (binding.emailInputLayout.getError() != null) {
                binding.emailInputLayout.setError(null);
            }

            return false;
        });

    }
}