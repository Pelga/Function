package com.example.myapplication.Laba6;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.R;

import java.io.Serializable;

public class MyDialogFragment extends DialogFragment implements Serializable {
    private EditText xDialog2;
    private EditText yDialog2;
    private Callback callback;
    public static final String DIALOG_TITLE = "Function point";
    public static final String CANCEL = "cancel";
    public static final String OK = "ok";
    public static final String NULL = "";


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog, null);
        builder.setView(view)
                .setTitle(DIALOG_TITLE)
                .setNegativeButton(CANCEL, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                })
                .setPositiveButton(OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if (!xDialog2.getText().toString().trim().equals(NULL) || yDialog2.getText().toString().equals(NULL)) {
                            double x = Double.parseDouble(xDialog2.getText().toString());
                            double y = Double.parseDouble(yDialog2.getText().toString());
                            FunctionPoint functionPoint = new FunctionPoint(x, y);
                            callback.applyText(functionPoint);
                        }
                    }
                });
        xDialog2 = view.findViewById(R.id.x_dialog2);
        yDialog2 = view.findViewById(R.id.y_dialog2);

        return builder.create();
    }

    public void show(FragmentManager fragmentManager, String tag, Callback callback) {
        super.show(fragmentManager, tag);
        this.callback = callback;
    }

    public interface Callback {
        void applyText(FunctionPoint functionPoint);
    }
}