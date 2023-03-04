package com.example.wegive.utils;

import android.view.View;

import com.example.wegive.R;
import com.google.android.material.snackbar.Snackbar;

public class SnackBarGlobal {

    public enum SEVERITY {
        ERROR,
        WARNING,
        SUCCESS,
        INFO
    }

    public static void make(View view, String text, SEVERITY severity) {
        Snackbar sb = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        View sbView = sb.getView();
        switch (severity) {
            case ERROR:
                sbView.setBackgroundColor(view.getResources().getColor(R.color.error));
                break;
            case WARNING:
                sbView.setBackgroundColor(view.getResources().getColor(R.color.warning));
                break;
            case SUCCESS:
                sbView.setBackgroundColor(view.getResources().getColor(R.color.success));
                break;
            case INFO:
                sbView.setBackgroundColor(view.getResources().getColor(R.color.info));
                break;
            default:
                break;
        }

        sb.show();
    }

}
