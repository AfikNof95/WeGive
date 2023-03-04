package com.example.wegive.utils;

import android.app.ProgressDialog;
import android.view.View;


import com.example.wegive.MyApplication;

public class ProgressDialogGlobal {


    private static final ProgressDialogGlobal _instance = new ProgressDialogGlobal();
    private final ProgressDialog pDialog;

    public static ProgressDialogGlobal getInstance() {
        return _instance;
    }

    private ProgressDialogGlobal() {
        pDialog = new ProgressDialog(MyApplication.getMyContext());
    }

    public void show(View view, String text) {
        this.pDialog.setView(view);
        this.pDialog.setIndeterminate(true);
        this.pDialog.setMessage(text);
        this.pDialog.show();
    }
}
