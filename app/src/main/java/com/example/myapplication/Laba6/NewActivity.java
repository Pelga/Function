package com.example.myapplication.Laba6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;

public class NewActivity extends AppCompatActivity implements Serializable {
    private TabAdapter tabAdapter;
    public static final String ARRAY = "array";
    public static final String DIALOG = "dialog";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity);
        RecyclerView recyclerView2;
        FloatingActionButton add_button;
        recyclerView2 = findViewById(R.id.recycler_view2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        ArrayTabulatedFunction list = (ArrayTabulatedFunction) intent.getSerializableExtra(ARRAY);
        assert list != null;
        tabAdapter = new TabAdapter(list);
        recyclerView2.setAdapter(tabAdapter);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    public void openDialog() {
        MyDialogFragment myDialogFragment = new MyDialogFragment();
        myDialogFragment.show(getSupportFragmentManager(), DIALOG, new MyDialogFragment.Callback() {
            @Override
            public void applyText(FunctionPoint functionPoint) {
                tabAdapter.add(functionPoint);
            }
        });
    }
}




