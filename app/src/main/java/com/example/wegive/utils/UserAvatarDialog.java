/*
package com.example.wegive.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;


public class UserAvatarDialog extends DialogFragment {
    UserAvatarDialogListener listener;
    public interface UserAvatarDialogListener{

        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }






    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener = (UserAvatarDialogListener) context;
        }catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Upload Profile Picture");
        builder.setPositiveButton("Camera", (DialogInterface dialogInterface, int i)->{
            listener.onDialogPositiveClick(UserAvatarDialog.this);
        });
        builder.setNegativeButton("Gallery", (DialogInterface dialogInterface, int i)->{
            listener.onDialogNegativeClick(UserAvatarDialog.this);
        });
        return builder.create();
    }
}
*/
