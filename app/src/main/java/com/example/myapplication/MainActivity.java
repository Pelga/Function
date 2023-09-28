package com.example.myapplication;

import static com.example.myapplication.Constants.ARRAY;
import static com.example.myapplication.Constants.NULL;
import static com.example.myapplication.Laba6.MyString.toStr;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.Laba6.ArrayTabulatedFunction;
import com.example.myapplication.Laba6.MyFragment;
import com.google.android.material.button.MaterialButton;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager = getSupportFragmentManager();
        setContentView(R.layout.activity_main);
        CardView cardView = findViewById(R.id.card);
        EditText leftDomainBorder = findViewById(R.id.right_domain_border);
        EditText rightDomainBorder = findViewById(R.id.left_domain_border);
        EditText pointsCount = findViewById(R.id.points_count);
        MaterialButton materialButton = findViewById(R.id.material_button);
        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toStr(rightDomainBorder).trim().equals(NULL) || toStr(leftDomainBorder).equals(NULL) || toStr(pointsCount).equals(ARRAY)) {
                    Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                } else {
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    cardView.setVisibility(View.INVISIBLE);
                    double l = Double.parseDouble(toStr(leftDomainBorder));
                    double r = Double.parseDouble(toStr(rightDomainBorder));
                    int p = Integer.parseInt(toStr(pointsCount));
                    ArrayTabulatedFunction arrayTabulatedFunction = new ArrayTabulatedFunction(l, r, p);
                    MyFragment fragment = new MyFragment(arrayTabulatedFunction);
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .commit();
                }
            }
        });
    }
}