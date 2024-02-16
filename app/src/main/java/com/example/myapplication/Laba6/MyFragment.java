package com.example.myapplication.Laba6;


import static com.example.myapplication.Constants.DIALOG;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Laba6.domain.ArrayTabulatedFunction;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;

public class MyFragment extends Fragment implements Serializable {
    private MyFragmentViewModel myFragmentViewModel;
    private MyDialogFragment myDialogFragment;
    private TabAdapter tabAdapter;
    private ArrayTabulatedFunction array;

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
        myFragmentViewModel = new ViewModelProvider(requireActivity()).get(MyFragmentViewModel.class);
        if (array != null) {
            myFragmentViewModel.setArrayTabulatedFunction(array);
        }
        tabAdapter = new TabAdapter();
        recyclerView.setAdapter(tabAdapter);

        myFragmentViewModel.getArrayTabulatedFunctionMutableLiveData().observe(getViewLifecycleOwner(),
                array -> tabAdapter.saveList(array));

        myFragmentViewModel.getOpenDialogFragmentLiveData().observe(getViewLifecycleOwner(), b -> openDialog());

        FloatingActionButton addButton = view.findViewById(R.id.add_button);
        Button buttonDatabase = view.findViewById(R.id.button_database);

        myFragmentViewModel.getMakeToastLiveData().observe(
                getViewLifecycleOwner(),
                string -> Toast.makeText(requireContext(), string, Toast.LENGTH_LONG).show()
        );

        addButton.setOnClickListener(view1 -> {
            myFragmentViewModel.addButtonPressed(false);
        });

        buttonDatabase.setOnClickListener(view1 -> myFragmentViewModel.buttonDatabasePressed(tabAdapter.getList()));
        return view;
    }

    public void openDialog() {
        myDialogFragment = new MyDialogFragment();
        myDialogFragment.show(getChildFragmentManager(), DIALOG);
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
}

