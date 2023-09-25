package com.example.myapplication;

import static com.example.myapplication.Constants.ARRAY;
import static com.example.myapplication.Constants.NULL;
import static com.example.myapplication.Laba6.MyString.toStr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Laba6.ArrayTabulatedFunction;
import com.example.myapplication.Laba6.NewActivity;
import com.google.android.material.button.MaterialButton;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                    double l = Double.parseDouble(toStr(leftDomainBorder));
                    double r = Double.parseDouble(toStr(rightDomainBorder));
                    int p = Integer.parseInt(toStr(pointsCount));
                    ArrayTabulatedFunction linkedListTabulatedFunction = new ArrayTabulatedFunction(l, r, p);
                    Intent intent = new Intent(MainActivity.this, NewActivity.class);
                    intent.putExtra(ARRAY, linkedListTabulatedFunction);
                    startActivity(intent);
                }
            }
        });
    }
}