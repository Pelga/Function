package com.example.myapplication.Laba6;

import static com.example.myapplication.Constants.ARRAY;
import static com.example.myapplication.Constants.DIALOG;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity);
        RecyclerView recyclerView2 = findViewById(R.id.recycler_view2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        ArrayTabulatedFunction list = (ArrayTabulatedFunction) getIntent().getSerializableExtra(ARRAY);
        assert list != null;
        tabAdapter = new TabAdapter(list);
        recyclerView2.setAdapter(tabAdapter);
        FloatingActionButton addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
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




