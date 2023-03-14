package com.example.wegive.utils;

import android.app.ProgressDialog;
import android.view.View;


import com.example.wegive.MyApplication;

public class ProgressDialogGlobal {


    private static final ProgressDialogGlobal _instance = new ProgressDialogGlobal();
    private  ProgressDialog pDialog;

    public static ProgressDialogGlobal getInstance() {
        return _instance;
    }

    private ProgressDialogGlobal() {
    }

    public void show(View view, String text) {
        this.pDialog = new ProgressDialog(view.getContext());
        this.pDialog.setIndeterminate(true);
        this.pDialog.setMessage(text);
        this.pDialog.show();
    }

    public void hide(){
        this.pDialog.hide();
    }
}
