package com.example.wegive.utils;

import android.app.ProgressDialog;
import android.view.View;


public class ProgressDialogGlobal {


    private static final ProgressDialogGlobal _instance = new ProgressDialogGlobal();
    private ProgressDialog pDialog;

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

    public void hide() {
        this.pDialog.hide();
    }

    public void dismiss() {
        this.pDialog.dismiss();
    }
}
