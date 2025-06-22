package com.elssu.harkkatyo.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import java.io.File;
import com.elssu.harkkatyo.R;
import com.elssu.harkkatyo.Storage;



public class StatisticsSaveLoad extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics_save_load, container, false);

        Button saveButton = view.findViewById(R.id.SaveGameButton); // Save button
        Button loadButton = view.findViewById(R.id.LoadGameButton); // Load button

        saveButton.setOnClickListener(v -> {
            Storage.getInstance().saveLutemonsToFile(requireContext(), "lutemons.dat");
        });




        loadButton.setOnClickListener(v -> {
            File file = new File(requireContext().getFilesDir(), "lutemons.dat");
            if (!file.exists()) {
                Toast.makeText(requireContext(), "Save file not found!", Toast.LENGTH_SHORT).show();
                return;
            }
            Storage.getInstance().loadLutemonsFromFile(requireContext(), "lutemons.dat");

            Toast.makeText(requireContext(), "Game loaded successfully!", Toast.LENGTH_SHORT).show();


        });
        return view;
    }
}

