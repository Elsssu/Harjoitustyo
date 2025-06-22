package com.elssu.harkkatyo.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.elssu.harkkatyo.Lutemon;
import com.elssu.harkkatyo.R;
import com.elssu.harkkatyo.Storage;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Collection;
import java.util.Iterator;

public class TrainingAreaFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_training_area, container, false);
        view.setBackgroundResource(R.drawable.gym_bg);

        ImageView trainingLutemonImage= view.findViewById(R.id.trainingLutemonImage);
        ImageView infoButton = view.findViewById(R.id.InfoPopupImage);

        TextView trainingLutemonName = view.findViewById(R.id.lutemonTrainingName);
        TextView experienceText = view.findViewById(R.id.ExperienceText);

        Collection<Lutemon> lutemons = Storage.getInstance().getTrainingArea().listLutemons();

        Button moveHomeButton = view.findViewById(R.id.MoveHomeButton);
        Button trainingButton = view.findViewById(R.id.TrainingButton);

        ProgressBar progressBar = view.findViewById(R.id.ProgressBar);


        updateTrainingAreaVisuals(trainingLutemonName, trainingLutemonImage, experienceText);

        trainingButton.setOnClickListener(i ->{
            if (!lutemons.isEmpty()) {
                Storage.getInstance().getTrainingArea().train();
                int barMax = Storage.getInstance().getTrainingArea().getTrainThreshold();
                int barCounter = Storage.getInstance().getTrainingArea().getTrainCounter();;
                progressBar.setMax(barMax); // set max value
                progressBar.setProgress(barCounter);
            }
            updateTrainingAreaVisuals(trainingLutemonName, trainingLutemonImage, experienceText);
        });


        infoButton.setOnClickListener(v -> {
            BottomSheetDialog dialog = new BottomSheetDialog(getContext());
            dialog.setContentView(R.layout.training_info_popup);

            TextView trainInfoText = dialog.findViewById(R.id.TrainInfoText);

            int trainAmount = Storage.getInstance().getTrainingArea().getTrainThreshold();

            String infoText = "Here you can train your lutemons to gain experience points.\nClicking the train button " + trainAmount+ " times gives one experience point.\nMove a lutemon here to start training.";
            trainInfoText.setText(infoText);
            dialog.show();
                });

        moveHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collection<Lutemon> lutemons = Storage.getInstance().getTrainingArea().listLutemons();
                if (!lutemons.isEmpty()) {
                    Lutemon lutemon = lutemons.iterator().next();

                    // Remove from Training Area and add to Home
                    Storage.getInstance().getHome().addLutemon(Storage.getInstance().getTrainingArea().getLutemon(lutemon.getId()));



                }
                updateTrainingAreaVisuals(trainingLutemonName, trainingLutemonImage, experienceText);
            }
        });
        return view;

    }
    @Override
    public void onResume() {
        super.onResume();
        //Refresh Lutemons and update UI
        Iterator<Lutemon> battlefieldLutemons = Storage.getInstance().getBattleField().listLutemons().iterator();
        Lutemon fighterA = battlefieldLutemons.hasNext() ? battlefieldLutemons.next() : null;
        Lutemon fighterB = battlefieldLutemons.hasNext() ? battlefieldLutemons.next() : null;

        View view = getView();
        if (view != null) {
            ImageView trainingLutemonImage= view.findViewById(R.id.trainingLutemonImage);
            TextView trainingLutemonName = view.findViewById(R.id.lutemonTrainingName);
            TextView experienceText = view.findViewById(R.id.ExperienceText);
            updateTrainingAreaVisuals(trainingLutemonName, trainingLutemonImage, experienceText);
        }
    }
    private void updateTrainingAreaVisuals(TextView nameView, ImageView imageView, TextView textView) {
        Collection<Lutemon> lutemons = Storage.getInstance().getTrainingArea().listLutemons();
        if (lutemons.isEmpty()) {
            nameView.setText("Nobody here :/");
            imageView.setImageDrawable(null); // or set a default/empty image
        } else {
            Lutemon lutemon = lutemons.iterator().next();
            nameView.setText(lutemon.getName() + " (" + lutemon.getColor() + ")");
            switch (lutemon.getColor()) {
                case "Orange":
                    imageView.setImageResource(R.drawable.orangelutemon);
                    break;
                case "Black":
                    imageView.setImageResource(R.drawable.blacklutemon);
                    break;
                case "White":
                    imageView.setImageResource(R.drawable.whitelutemon);
                    break;
                case "Green":
                    imageView.setImageResource(R.drawable.greenlutemon);
                    break;
                case "Pink":
                    imageView.setImageResource(R.drawable.pinklutemon);
                    break;
            }
            textView.setText("Experience: " + lutemon.getExperience());
        }
    }
}