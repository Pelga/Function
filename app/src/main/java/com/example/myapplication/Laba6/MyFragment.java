package com.example.myapplication.Laba6;

import static com.example.myapplication.Constants.DIALOG;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;

public class MyFragment extends Fragment implements Serializable {
    private TabAdapter tabAdapter;
    private ArrayTabulatedFunction array;
    private MyDialogFragment myDialogFragment;

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
        if (array != null) {
            tabAdapter = new TabAdapter(array);
        } else {
            if (savedInstanceState != null) {
                array = (ArrayTabulatedFunction) savedInstanceState.getSerializable("myKeyArrayTabulatedFunction");
                assert array != null;
                tabAdapter = new TabAdapter(array);
            }
        }
        recyclerView.setAdapter(tabAdapter);
        FloatingActionButton addButton = view.findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
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

    public void closeDialog() {
        if (myDialogFragment != null) {
            myDialogFragment.dismiss();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("myKeyArrayTabulatedFunction", tabAdapter.getMyArrayTabulatedFunction());
    }
}

