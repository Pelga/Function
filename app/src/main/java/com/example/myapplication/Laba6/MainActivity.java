package com.example.myapplication.Laba6;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.Laba6.domain.ArrayTabulatedFunction;
import com.example.myapplication.R;
import com.google.android.material.button.MaterialButton;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements Serializable {
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CardView cardView = findViewById(R.id.card);
        EditText leftDomainBorder = findViewById(R.id.right_domain_border);
        EditText rightDomainBorder = findViewById(R.id.left_domain_border);
        EditText pointsCount = findViewById(R.id.points_count);
        MaterialButton materialButtonCreate = findViewById(R.id.material_button);
        MaterialButton materialButtonGenerate = findViewById(R.id.material_button_generate);
        MaterialButton materialButtonDownload = findViewById(R.id.material_button_download);
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        viewModel.isCloseCardView().observe(this, boo -> cardView.setVisibility(boo ? View.INVISIBLE : View.VISIBLE));
        viewModel.getMakeErrorToastLiveData().observe(
                this,
                string -> Toast.makeText(MainActivity.this, string, Toast.LENGTH_LONG).show()
        );
        viewModel.getArrayTabulatedFunctionLiveData().observe(this, this::openFragment);
        viewModel.getCloseKeyboardLiveData().observe(this, this::closeKeyboard);
        viewModel.getVisibilityLiveData().observe(this, cardView::setVisibility);

        materialButtonCreate.setOnClickListener(view -> {
            viewModel.materialButtonCreatePressed(rightDomainBorder, leftDomainBorder, pointsCount, view, View.INVISIBLE);
        });

        materialButtonGenerate.setOnClickListener(view -> {
            viewModel.materialButtonGeneratePressed(view, View.INVISIBLE, true);
        });

        materialButtonDownload.setOnClickListener(view -> {
            viewModel.materialButtonDownloadPressed(view, View.INVISIBLE, true);
        });
    }

    private void closeKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void openFragment(ArrayTabulatedFunction arrayTabulatedFunction) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        MyFragment fragment = new MyFragment(arrayTabulatedFunction);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}