package com.example.myapplication;

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
        EditText left_domain_border = findViewById(R.id.right_domain_border);
        EditText right_domain_border = findViewById(R.id.left_domain_border);
        EditText points_count = findViewById(R.id.points_count);
        MaterialButton material_button;
        material_button = findViewById(R.id.material_button);
        material_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (right_domain_border.getText().toString().trim().equals("") || left_domain_border.getText().toString().equals("") || points_count.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                } else {
                    double l = Double.parseDouble(left_domain_border.getText().toString());
                    double r = Double.parseDouble(right_domain_border.getText().toString());
                    int p = Integer.parseInt(points_count.getText().toString());
                    ArrayTabulatedFunction linkedListTabulatedFunction = new ArrayTabulatedFunction(l, r, p);
                    Intent intent = new Intent(MainActivity.this, NewActivity.class);
                    intent.putExtra("array", linkedListTabulatedFunction);
                    startActivity(intent);
                }
            }
        });
    }
}