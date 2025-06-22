package com.elssu.harkkatyo.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elssu.harkkatyo.R;

public class StatisticsSaveLoad extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Handle arguments if needed
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics_save_load, container, false);
    }
}/*public void saveGame(View view) {
        Storage.getInstance().saveGame(context);
    }

    public void loadGame(View view) {
        Storage.getInstance().loadGame(context);
    }*/