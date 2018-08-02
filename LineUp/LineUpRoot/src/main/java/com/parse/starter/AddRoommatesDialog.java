package com.parse.starter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class AddRoommatesDialog extends DialogFragment {

    private EditText editTextUsername;

    public interface RoommateDialogListener {
        public void addUser(String username);
    }
    RoommateDialogListener roommateDialogListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            roommateDialogListener = (RoommateDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_roommates_dialog, null);

        builder.setView(view)
                .setTitle("Add Roommate")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        roommateDialogListener.addUser(editTextUsername.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        editTextUsername = view.findViewById(R.id.rm_username);

        return builder.create();
    }




}
