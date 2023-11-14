package com.example.myapplication.Laba6;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.io.Serializable;
import java.util.ArrayList;


public class TabAdapter extends RecyclerView.Adapter<TabAdapter.TabViewHolder> implements Serializable {
    private ArrayList<FunctionPoint> list = new ArrayList<>();

    public TabAdapter(ArrayTabulatedFunction list) {
        for (int i = 0; i < list.getPointsCount(); i++) {
            this.list.add(list.getPoint(i));
        }
    }

    public ArrayTabulatedFunction getMyArrayTabulatedFunction() {
        FunctionPoint[] array = new FunctionPoint[list.size()];
        array = list.toArray(array);
        return new ArrayTabulatedFunction(array);
    }

    @NonNull
    @Override
    public TabViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.tabulated_function, viewGroup, false);
        return new TabViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TabViewHolder tabViewHolder, int i) {
        if (list.get(i) != null) {
            tabViewHolder.getButton().setText(list.get(i).toString());
        }
        tabViewHolder.getButton().setOnClickListener(view -> delete(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void delete(int position) {
        list.remove(position);
        notifyDataSetChanged();
    }

    public ArrayList<FunctionPoint> getList() {
        return list;
    }

    public void add(FunctionPoint functionPoint) {
        list.add(functionPoint);
        notifyDataSetChanged();
    }

    static class TabViewHolder extends RecyclerView.ViewHolder {

        private final Button tabulatedButton;

        public TabViewHolder(View itemView) {
            super(itemView);
            tabulatedButton = itemView.findViewById(R.id.tabulatedButton);
        }

        public Button getButton() {
            return tabulatedButton;
        }
    }
}


