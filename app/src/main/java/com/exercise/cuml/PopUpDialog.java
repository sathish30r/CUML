package com.exercise.cuml;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class PopUpDialog extends AppCompatDialogFragment {

    EditText editTextCount;
    DialogListener listener;
    String myDataFromActivity;
    String count;
    int addedCount=0;
    Button buttonAddNumbers;
    Button buttonSubNumbers;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_dialog,null);
        builder.setView(view)
                .setTitle("Count")

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                            count = editTextCount.getText().toString();
                            listener.applyCounts(count, myDataFromActivity);
                    }
                });


        editTextCount = view.findViewById(R.id.editNumbers);
        editTextCount.setFocusable(false);
        editTextCount.setEnabled(false);
        editTextCount.setCursorVisible(false);
        editTextCount.setKeyListener(null);
        editTextCount.setBackgroundColor(Color.TRANSPARENT);
        buttonAddNumbers = view.findViewById(R.id.buttonAddNumbers);
        buttonSubNumbers = view.findViewById(R.id.buttonSubNumbers);

        buttonSubNumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addedCount >0)
                {
                    addedCount = addedCount -5;
                }
                editTextCount.setText(String.valueOf(addedCount));
            }
        });

        buttonAddNumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addedCount >=0 && addedCount <=45) {
                    addedCount = addedCount + 5;
                }
                editTextCount.setText(String.valueOf(addedCount));
            }
        });
        return builder.create();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getting getButtonPressNo value from mainActivity through intent
        MainActivity activity = (MainActivity) getActivity();
        myDataFromActivity = activity.sendCoreText();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener =(DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"must implement DialogListener");
        }
    }

    public interface DialogListener{
        void applyCounts(String count, String myDataFromActivity);
    }
}
