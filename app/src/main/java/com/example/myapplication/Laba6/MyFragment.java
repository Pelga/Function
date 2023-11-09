package com.example.myapplication.Laba6;

import static com.example.myapplication.Constants.DIALOG;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MyFragment extends Fragment implements Serializable {
    private TabAdapter tabAdapter;
    private ArrayTabulatedFunction array;
    private MyDialogFragment myDialogFragment;
    public static final String MY_KEY_ARRAY = "myKeyArrayTabulatedFunction";


    public MyFragment(ArrayTabulatedFunction array) {
        this.array = array;
    }

    public MyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (savedInstanceState != null && array == null) {
            array = (ArrayTabulatedFunction) savedInstanceState.getSerializable(MY_KEY_ARRAY);
            assert array != null;
        }
        tabAdapter = new TabAdapter(array);
        recyclerView.setAdapter(tabAdapter);
        FloatingActionButton addButton = view.findViewById(R.id.add_button);
        Button buttonDatabase = view.findViewById(R.id.button_database);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
        buttonDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabase database = App.getInstance().getDatabase();
                MyDao dao = database.myDao();
                toList(array).forEach(functionPoint -> {
                    MyEntity entity = new MyEntity(functionPoint.getX(), functionPoint.getY());
                    dao.insert(entity);
                });
                getActivity().getSupportFragmentManager().beginTransaction()
                        .remove(MyFragment.this)
                        .commit();
            }
        });
        return view;
    }

    public void openDialog() {
        myDialogFragment = new MyDialogFragment();
        myDialogFragment.show(getChildFragmentManager(), DIALOG, new MyDialogFragment.Callback() {
            @Override
            public void applyText(FunctionPoint functionPoint) {
                tabAdapter.add(functionPoint);
            }
        });
    }

    @Override
    public void onPause() {
        closeDialog();
        super.onPause();
    }

    public List<FunctionPoint> toList(ArrayTabulatedFunction list) {
        List<FunctionPoint> list1 = new ArrayList<>();
        for (int i = 0; i < list.getPointsCount(); i++) {
            list1.add(list.getPoint(i));
        }
        return list1;
    }

    public void closeDialog() {
        if (myDialogFragment != null) {
            myDialogFragment.dismiss();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(MY_KEY_ARRAY, tabAdapter.getMyArrayTabulatedFunction());
    }
}

