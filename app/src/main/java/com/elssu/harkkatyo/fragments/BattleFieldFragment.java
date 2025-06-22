package com.elssu.harkkatyo.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.elssu.harkkatyo.Lutemon;
import com.elssu.harkkatyo.R;
import com.elssu.harkkatyo.Storage;

import java.util.Collection;
import java.util.Iterator;


public class BattleFieldFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }
    private Lutemon fighterA = null;
    private Lutemon fighterB = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_battle_field, container, false);
        view.setBackgroundResource(R.drawable.battle_bg);

        TextView fighterAText = view.findViewById(R.id.FighterAText);
        TextView fighterBText = view.findViewById(R.id.FighterBText);
        TextView battleText = view.findViewById(R.id.BattleText);
        TextView fighterANameText = view.findViewById(R.id.FighterANameText);
        TextView fighterBNameText = view.findViewById(R.id.FighterBNameText);

        ImageView fighterAImage = view.findViewById(R.id.FighterAImage);
        ImageView fighterBImage = view.findViewById(R.id.FighterBImage);

        Button fightButton = view.findViewById(R.id.FightStartButton);
        Button moveHomeFromBattleButton = view.findViewById(R.id.MoveHomeFromBattleButton);

        Iterator<Lutemon> battlefieldLutemons = Storage.getInstance().getBattleField().listLutemons().iterator();


        if (battlefieldLutemons.hasNext()) {
            fighterA = battlefieldLutemons.next();
        }
        if (battlefieldLutemons.hasNext()) {
            fighterB = battlefieldLutemons.next();
        }

        moveHomeFromBattleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collection<Lutemon> lutemons = Storage.getInstance().getBattleField().listLutemons();
                if (!lutemons.isEmpty()) {
                    Lutemon lutemon = lutemons.iterator().next();

                    // Remove from Training Area and add to Home
                    Storage.getInstance().getHome().addLutemon(Storage.getInstance().getBattleField().getLutemon(lutemon.getId()));



                }

                updateBattleAreaVisuals( fighterA,  fighterANameText,  fighterAText,  fighterAImage,
                        fighterB,  fighterBNameText,  fighterBText,  fighterBImage);
            }
        });
        updateBattleAreaVisuals(fighterA, fighterANameText, fighterAText, fighterAImage, fighterB, fighterBNameText, fighterBText, fighterBImage);

        return view;
    }
    private void updateBattleAreaVisuals(
            Lutemon fighterA, TextView fighterANameText, TextView fighterAText, ImageView fighterAImage,
            Lutemon fighterB, TextView fighterBNameText, TextView fighterBText, ImageView fighterBImage) {

        Collection<Lutemon> lutemons = Storage.getInstance().getBattleField().listLutemons();
        if (lutemons.isEmpty()) {
            fighterAText.setText("Nobody here :/");
            fighterANameText.setText("");
            fighterAImage.setImageDrawable(null);
        } else {
            String ANameText = fighterA.getName() + " (" + fighterA.getColor() + ")";
            String AText = "Attack:             " + fighterA.getAttack() + "\n" +
                    "Defense:          " + fighterA.getDefense() + "\n" +
                    "HP:           " +fighterA.getHealth() + "/" + fighterA.getMaxHealth();
            fighterANameText.setText(ANameText);
            fighterAText.setText(AText);
            setLutemonImage(fighterA, fighterAImage);
        }

        if (fighterB != null) {
            String BNameText = fighterB.getName() + " (" + fighterB.getColor() + ")";
            String BText = "Attack:             " + fighterB.getAttack() + "\n" +
                    "Defense:          " + fighterB.getDefense() + "\n" +
                    "HP:           " +fighterB.getHealth() + "/" + fighterB.getMaxHealth();
            fighterBNameText.setText(BNameText);
            fighterBText.setText(BText);
            setLutemonImage(fighterB, fighterBImage);
        } else {
            fighterBText.setText("Nobody here :/");
            fighterBNameText.setText("");
            fighterBImage.setImageDrawable(null);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Re-fetch Lutemons and update UI
        Iterator<Lutemon> battlefieldLutemons = Storage.getInstance().getBattleField().listLutemons().iterator();
        Lutemon fighterA = battlefieldLutemons.hasNext() ? battlefieldLutemons.next() : null;
        Lutemon fighterB = battlefieldLutemons.hasNext() ? battlefieldLutemons.next() : null;

        View view = getView();
        if (view != null) {
            TextView fighterAText = view.findViewById(R.id.FighterAText);
            TextView fighterBText = view.findViewById(R.id.FighterBText);
            ImageView fighterAImage = view.findViewById(R.id.FighterAImage);
            ImageView fighterBImage = view.findViewById(R.id.FighterBImage);
            TextView fighterANameText = view.findViewById(R.id.FighterANameText);
            TextView fighterBNameText = view.findViewById(R.id.FighterBNameText);
            updateBattleAreaVisuals(fighterA, fighterANameText, fighterAText, fighterAImage, fighterB, fighterBNameText, fighterBText, fighterBImage);
        }
    }
    private void setLutemonImage(Lutemon lutemon, ImageView imageView) {
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
    }
}