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
    private EditText x_dialog2;
    private EditText y_dialog2;
    Callback callback;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog, null);
        builder.setView(view)
                .setTitle("Function point")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                          if (!x_dialog2.getText().toString().trim().equals("") || y_dialog2.getText().toString().equals("")) {
                              double x = Double.parseDouble(x_dialog2.getText().toString());
                              double y = Double.parseDouble(y_dialog2.getText().toString());
                              FunctionPoint functionPoint = new FunctionPoint(x, y);
                              callback.applyText(functionPoint);
                          }
                    }
                });
        x_dialog2 = view.findViewById(R.id.x_dialog2);
        y_dialog2 = view.findViewById(R.id.y_dialog2);

        return builder.create();
    }
    public void show( FragmentManager fragmentManager, String tag, Callback callback) {
        super.show(fragmentManager, tag);
        this.callback = callback;
    }

    public interface Callback{
        public void applyText(FunctionPoint functionPoint);
    }
}