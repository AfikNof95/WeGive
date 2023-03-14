package com.example.wegive.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.wegive.MainActivity;
import com.example.wegive.R;
import com.google.firebase.auth.FirebaseAuthException;

public class ErrorHandler {

    public enum ERROR_MESSAGES {
        ERROR_USER_NOT_FOUND("ERROR_USER_NOT_FOUND"),
        ERROR_WRONG_PASSWORD("ERROR_WRONG_PASSWORD"),
        ERROR_INVALID_EMAIL("ERROR_INVALID_EMAIL");
        private final String errorMessage;

        ERROR_MESSAGES(final String message) {
            errorMessage = message;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        @NonNull
        @Override
        public String toString() {
            return this.getErrorMessage();
        }
    }

    public static String getAuthErrorMessage(Context context, FirebaseAuthException ex) {
        Resources resources = context.getResources();

        Log.d("ERROR_TAG", ex.getErrorCode());
        String parsedMessage = resources.getString(R.string.generic_auth_exception);


        try {
            switch (ERROR_MESSAGES.valueOf(ex.getErrorCode())) {
                case ERROR_USER_NOT_FOUND:
                case ERROR_WRONG_PASSWORD:
                    parsedMessage = resources.getString(R.string.auth_wrong_credentials);
                    break;
                case ERROR_INVALID_EMAIL:
                    parsedMessage = ex.getMessage();
                    break;
                default:
                    break;
            }
        } catch (Exception exception) {
            return parsedMessage;
        }


        return parsedMessage;
    }
}
