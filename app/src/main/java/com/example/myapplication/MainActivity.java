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
    public static final String ARRAY = "array";
    public static final String NULL = "";

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
                if (toStr(right_domain_border).trim().equals(NULL) || toStr(left_domain_border).equals(NULL) || toStr(points_count).equals(ARRAY)) {
                    Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                } else {
                    double l = Double.parseDouble(toStr(left_domain_border));
                    double r = Double.parseDouble(toStr(right_domain_border));
                    int p = Integer.parseInt(toStr(points_count));
                    ArrayTabulatedFunction linkedListTabulatedFunction = new ArrayTabulatedFunction(l, r, p);
                    Intent intent = new Intent(MainActivity.this, NewActivity.class);
                    intent.putExtra(ARRAY, linkedListTabulatedFunction);
                    startActivity(intent);
                }
            }
        });
    }

    private String toStr(EditText editText) {
        return editText.getText().toString();
    }
}