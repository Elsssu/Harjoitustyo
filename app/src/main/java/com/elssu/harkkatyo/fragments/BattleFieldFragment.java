package com.elssu.harkkatyo.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.elssu.harkkatyo.Lutemon;
import com.elssu.harkkatyo.R;
import com.elssu.harkkatyo.Storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


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
        TextView battleTextView = view.findViewById(R.id.BattleTextView);
        ScrollView battleText = view.findViewById(R.id.BattleText);
        TextView fighterANameText = view.findViewById(R.id.FighterANameText);
        TextView fighterBNameText = view.findViewById(R.id.FighterBNameText);
        ImageView SwordAtoB = view.findViewById(R.id.SwordAtoB);
        ImageView SwordBtoA = view.findViewById(R.id.SwordBtoA);
        ImageView ShieldAtoB = view.findViewById(R.id.ShieldAtoB);
        ImageView ShieldBtoA = view.findViewById(R.id.ShieldBtoA);

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


        fightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Re-fetch fighters in case the battlefield has changed
                Iterator<Lutemon> battlefieldLutemons = Storage.getInstance().getBattleField().listLutemons().iterator();
                fighterA = battlefieldLutemons.hasNext() ? battlefieldLutemons.next() : null;
                fighterB = battlefieldLutemons.hasNext() ? battlefieldLutemons.next() : null;

                if (fighterA == null || fighterB == null) {
                    battleTextView.setText("Not enough fighters on the battlefield!");
                    return;
                }
                if (fighterA.getId() == fighterB.getId()) {
                    battleTextView.setText("You need two different Lutemons on the battlefield!");
                    return;
                }
                List<String> fightResults = Storage.getInstance().getBattleField().fight("");
                battleTextView.setText("");
                Handler handler = new Handler();
                int delay = 3000; // 3 seconds per step

                for (int i = 0; i < fightResults.size(); i++) {
                    final int index = i;
                    handler.postDelayed(() -> {
                        // Hide all images first
                        SwordAtoB.setVisibility(View.GONE);
                        SwordBtoA.setVisibility(View.GONE);
                        ShieldAtoB.setVisibility(View.GONE);
                        ShieldBtoA.setVisibility(View.GONE);

                        String result = fightResults.get(index);

                        // Show the correct image based on the result text
                        if (result.contains("missed")) {
                            // Show nothing
                        } else if (result.contains("landed a critical hit") || result.contains("attacked")) {
                            if (result.startsWith(fighterA.getName())) {
                                SwordAtoB.setVisibility(View.VISIBLE);
                                ShieldBtoA.setVisibility(View.VISIBLE); // Fighter B defends
                            } else if (result.startsWith(fighterB.getName())) {
                                SwordBtoA.setVisibility(View.VISIBLE);
                                ShieldAtoB.setVisibility(View.VISIBLE); // Fighter A defends
                            }
                        } else if (result.contains("defend") || result.contains("damage to")) {
                            if (result.contains(fighterA.getName())) {
                                ShieldAtoB.setVisibility(View.VISIBLE);
                            } else if (result.contains(fighterB.getName())) {
                                ShieldBtoA.setVisibility(View.VISIBLE);
                            }
                        }

                        battleTextView.append(result);

                        // Hide the image after 1 second
                        handler.postDelayed(() -> {
                            SwordAtoB.setVisibility(View.GONE);
                            SwordBtoA.setVisibility(View.GONE);
                            ShieldAtoB.setVisibility(View.GONE);
                            ShieldBtoA.setVisibility(View.GONE);
                        }, 1000);

                        // End of fight logic
                        if (index == fightResults.size() - 1) {
                            handler.postDelayed(() -> {
                                if (fighterA.getHealth() <= 0) {
                                    fighterA.addLoss();
                                    fighterB.addWin();
                                    Lutemon removedB = Storage.getInstance().getBattleField().getLutemon(fighterB.getId());
                                    Storage.getInstance().getHome().addLutemon(removedB);
                                    Lutemon removedA = Storage.getInstance().getBattleField().getLutemon(fighterA.getId());
                                    Storage.getInstance().getHome().addLutemon(removedA);

                                    battleTextView.append(fighterB.getName() + " and " + fighterA.getName() + " were brought home to heal.\n");
                                } else if (fighterB.getHealth() <= 0) {
                                    fighterB.addLoss();
                                    fighterA.addWin();
                                    Lutemon removedB = Storage.getInstance().getBattleField().getLutemon(fighterB.getId());
                                    Storage.getInstance().getHome().addLutemon(removedB);
                                    Lutemon removedA = Storage.getInstance().getBattleField().getLutemon(fighterA.getId());
                                    Storage.getInstance().getHome().addLutemon(removedA);
                                    battleTextView.append(fighterA.getName() + " and " + fighterB.getName() + " were brought home to heal.\n");
                                }
                                fighterA= null;
                                fighterB= null;
                                updateBattleAreaVisuals(
                                        fighterA, fighterANameText, fighterAText, fighterAImage,
                                        fighterB, fighterBNameText, fighterBText, fighterBImage
                                );
                            }, delay);
                        }
                    }, delay * i);
                }
            }

        });
        moveHomeFromBattleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Lutemon lutemon : new ArrayList<>(Storage.getInstance().getBattleField().listLutemons())) {
                    Lutemon removedC = Storage.getInstance().getBattleField().getLutemon(lutemon.getId());
                    if (removedC != null) {
                        Storage.getInstance().getHome().addLutemon(removedC);
                    }
                }
                fighterA = null;
                fighterB = null;
                updateBattleAreaVisuals(fighterA, fighterANameText, fighterAText, fighterAImage, fighterB, fighterBNameText, fighterBText, fighterBImage);

            }
        });
        updateBattleAreaVisuals(fighterA, fighterANameText, fighterAText, fighterAImage, fighterB, fighterBNameText, fighterBText, fighterBImage);

        return view;
    }
    private void updateBattleAreaVisuals(
            Lutemon fighterA, TextView fighterANameText, TextView fighterAText, ImageView fighterAImage,
            Lutemon fighterB, TextView fighterBNameText, TextView fighterBText, ImageView fighterBImage) {

        Collection<Lutemon> lutemons = Storage.getInstance().getBattleField().listLutemons();
        if (lutemons.isEmpty() || fighterA == null) {
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