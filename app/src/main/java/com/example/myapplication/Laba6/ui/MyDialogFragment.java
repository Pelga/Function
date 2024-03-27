package com.example.myapplication.Laba6.ui;

import static com.example.myapplication.Laba6.domain.Constants.CANCEL;
import static com.example.myapplication.Laba6.domain.Constants.DIALOG_TITLE;
import static com.example.myapplication.Laba6.domain.Constants.NULL;
import static com.example.myapplication.Laba6.domain.Constants.OK;
import static com.example.myapplication.Laba6.domain.MyString.toStr;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;

import java.io.Serializable;

public class MyDialogFragment extends DialogFragment implements Serializable {
    private MyFragmentViewModel myFragmentViewModel;
    private EditText xDialog2;
    private EditText yDialog2;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        myFragmentViewModel = new ViewModelProvider(requireActivity()).get(MyFragmentViewModel.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog, null);
        builder.setView(view)
                .setTitle(DIALOG_TITLE)
                .setNegativeButton(CANCEL, (dialog, i) -> {
                })
                .setPositiveButton(OK, (dialog, i) -> {
                    String strX = toStr(xDialog2);
                    String strY = toStr(yDialog2);
                    if (!strX.trim().equals(NULL) || strY.equals(NULL)) {
                        double x = Double.parseDouble(strX);
                        double y = Double.parseDouble(strY);
                        myFragmentViewModel.positiveButtonPressed(x, y);
                    }
                });
        xDialog2 = view.findViewById(R.id.x_dialog2);
        yDialog2 = view.findViewById(R.id.y_dialog2);
        return builder.create();
    }
}
