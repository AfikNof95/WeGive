package com.example.wegive.fragments.auth;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wegive.R;
import com.example.wegive.databinding.FragmentForgotPasswordBinding;
import com.example.wegive.firebase.FireBaseAuth;
import com.example.wegive.models.user.UserModel;
import com.example.wegive.utils.ErrorHandler;
import com.example.wegive.utils.SnackBarGlobal;
import com.google.firebase.auth.FirebaseAuthException;

public class ForgotPasswordFragment extends Fragment {

    FragmentForgotPasswordBinding binding;
    View view;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.hide();

        setEventsListeners();

        return view;
    }

    private void setEventsListeners() {
        binding.forgotPasswordCancelButton.setOnClickListener(view1 -> {
            Navigation.findNavController(view).popBackStack();
        });

        binding.forgotPasswordConfirmButton.setOnClickListener(view1 -> {
            String email = binding.forgotPasswordEmailInput.getText().toString();
            UserModel.instance().forgotPassword(email, data -> {
                if (data.isSuccessful()) {
                    Log.d("TAG", "Password reset link sent");
                    SnackBarGlobal.make(view, getString(R.string.forgot_password_success), SnackBarGlobal.SEVERITY.SUCCESS);
                    Navigation.findNavController(view).popBackStack();
                } else {
                    try {
                        FirebaseAuthException error = (FirebaseAuthException) (data.getException());
                        Context context = requireContext();
                        binding.forgotPasswordErrorLabel.setText(ErrorHandler.getAuthErrorMessage(context, error));
                    } catch (Exception ex) {
                        binding.forgotPasswordErrorLabel.setText(getString(R.string.generic_auth_exception));
                    }
                }
            });
        });
    }
}