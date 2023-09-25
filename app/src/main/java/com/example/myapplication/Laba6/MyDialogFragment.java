package com.example.myapplication.Laba6;

import static com.example.myapplication.Constants.CANCEL;
import static com.example.myapplication.Constants.DIALOG_TITLE;
import static com.example.myapplication.Constants.NULL;
import static com.example.myapplication.Constants.OK;
import static com.example.myapplication.Laba6.MyString.toStr;

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
                        if (!toStr(xDialog2).trim().equals(NULL) || toStr(yDialog2).equals(NULL)) {
                            double x = Double.parseDouble(toStr(xDialog2));
                            double y = Double.parseDouble(toStr(yDialog2));
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