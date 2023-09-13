package com.example.myapplication.Laba6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import com.example.myapplication.R;
import java.io.Serializable;
public class NewActivity extends AppCompatActivity implements Serializable {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity);
        RecyclerView recycler_view2;
        recycler_view2 = findViewById(R.id.recycler_view2);
        recycler_view2.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        ArrayTabulatedFunction list = (ArrayTabulatedFunction) intent.getSerializableExtra("array");
        recycler_view2.setAdapter(new TabAdapter(list));
    }
}




