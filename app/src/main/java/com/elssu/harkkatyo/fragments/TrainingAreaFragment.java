package com.elssu.harkkatyo.fragments;

import android.os.Bundle;


import androidx.fragment.app.Fragment;


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

import org.w3c.dom.Text;

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
        TextView timesTrainingText = view.findViewById(R.id.TimesTrainingText);
        Collection<Lutemon> lutemons = Storage.getInstance().getTrainingArea().listLutemons();

        Button moveHomeButton = view.findViewById(R.id.MoveHomeButton);
        Button trainingButton = view.findViewById(R.id.TrainingButton);

        ProgressBar progressBar = view.findViewById(R.id.ProgressBar);


        updateTrainingAreaVisuals(trainingLutemonName, trainingLutemonImage, experienceText, timesTrainingText);

        trainingButton.setOnClickListener(i ->{
            if (!lutemons.isEmpty()) {
                Storage.getInstance().getTrainingArea().train();
                int barMax = Storage.getInstance().getTrainingArea().getTrainThreshold();
                int barCounter = Storage.getInstance().getTrainingArea().getTrainCounter();;
                progressBar.setMax(barMax); // set max value
                progressBar.setProgress(barCounter);
            }
            updateTrainingAreaVisuals(trainingLutemonName, trainingLutemonImage, experienceText, timesTrainingText);
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

                    Storage.getInstance().getHome().addLutemon(Storage.getInstance().getTrainingArea().getLutemon(lutemon.getId()));



                }
                updateTrainingAreaVisuals(trainingLutemonName, trainingLutemonImage, experienceText, timesTrainingText);
            }
        });
        return view;

    }
    @Override
    public void onResume() {
        super.onResume();

        Iterator<Lutemon> battlefieldLutemons = Storage.getInstance().getBattleField().listLutemons().iterator();
        Lutemon fighterA = battlefieldLutemons.hasNext() ? battlefieldLutemons.next() : null;
        Lutemon fighterB = battlefieldLutemons.hasNext() ? battlefieldLutemons.next() : null;

        View view = getView();
        if (view != null) {
            ImageView trainingLutemonImage= view.findViewById(R.id.trainingLutemonImage);
            TextView trainingLutemonName = view.findViewById(R.id.lutemonTrainingName);
            TextView experienceText = view.findViewById(R.id.ExperienceText);
            TextView timesTrainingText = view.findViewById(R.id.TimesTrainingText);
            updateTrainingAreaVisuals(trainingLutemonName, trainingLutemonImage, experienceText, timesTrainingText);
        }
    }
    private void updateTrainingAreaVisuals(TextView nameView, ImageView imageView, TextView experienceText, TextView timesTrainingText) {
        Collection<Lutemon> lutemons = Storage.getInstance().getTrainingArea().listLutemons();
        if (lutemons.isEmpty()) {
            nameView.setText("Nobody here :/");
            imageView.setImageDrawable(null);
            experienceText.setText("");
            timesTrainingText.setText("");
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
            experienceText.setText("Experience: " + lutemon.getExperience());
            timesTrainingText.setText("Times trained:\n   " + lutemon.getTimesTraining());
        }
    }
}