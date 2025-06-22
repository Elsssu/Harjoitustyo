package com.elssu.harkkatyo.fragments;

import android.os.Bundle;
import android.os.Handler;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BattleFieldFragment extends Fragment {

    private Lutemon fighterA = null;
    private Lutemon fighterB = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_battle_field, container, false);
        view.setBackgroundResource(R.drawable.battle_bg);

        TextView fighterAText = view.findViewById(R.id.FighterAText);
        TextView fighterBText = view.findViewById(R.id.FighterBText);
        TextView battleTextView = view.findViewById(R.id.BattleTextView);
        TextView fighterANameText = view.findViewById(R.id.FighterANameText);
        TextView fighterBNameText = view.findViewById(R.id.FighterBNameText);
        ImageView fighterAImage = view.findViewById(R.id.FighterAImage);
        ImageView fighterBImage = view.findViewById(R.id.FighterBImage);
        ImageView SwordAtoB = view.findViewById(R.id.SwordAtoB);
        ImageView SwordBtoA = view.findViewById(R.id.SwordBtoA);
        ImageView ShieldAtoB = view.findViewById(R.id.ShieldAtoB);
        ImageView ShieldBtoA = view.findViewById(R.id.ShieldBtoA);

        Button fightButton = view.findViewById(R.id.FightStartButton);
        Button moveHomeFromBattleButton = view.findViewById(R.id.MoveHomeFromBattleButton);

        Iterator<Lutemon> battlefieldLutemons = Storage.getInstance().getBattleField().listLutemons().iterator();
        if (battlefieldLutemons.hasNext()) fighterA = battlefieldLutemons.next();
        if (battlefieldLutemons.hasNext()) fighterB = battlefieldLutemons.next();

        fightButton.setOnClickListener(v -> {
            Iterator<Lutemon> it = Storage.getInstance().getBattleField().listLutemons().iterator();
            fighterA = it.hasNext() ? it.next() : null;
            fighterB = it.hasNext() ? it.next() : null;

            if (fighterA == null || fighterB == null) {
                battleTextView.setText("Not enough fighters on the battlefield!");
                return;
            }
            if (fighterA.getId() == fighterB.getId()) {
                battleTextView.setText("You need two different Lutemons on the battlefield!");
                return;
            }

            int localHealthA = fighterA.getHealth();
            int localHealthB = fighterB.getHealth();

            List<String> fightResults = new ArrayList<>();
            List<Runnable> visuals = new ArrayList<>();

            while (localHealthA > 0 && localHealthB > 0) {
                int attackValueA = fighterA.getAttack() + fighterA.getExperience() / 2;
                boolean missA = Math.random() < 0.2;
                boolean critA = !missA && Math.random() < 0.45;
                int damageA = attackValueA - fighterB.getDefense();
                if (missA) {
                    fightResults.add(fighterA.getName() + " missed the attack!\n");
                    visuals.add(() -> {
                        SwordAtoB.setVisibility(View.GONE);
                        ShieldBtoA.setVisibility(View.GONE);
                    });
                } else {
                    if (critA) {
                        damageA += 2;
                        fightResults.add(fighterA.getName() + " landed a critical hit, dealing " + Math.max(0, damageA) + " damage to " + fighterB.getName() + "!\n");
                    } else {
                        fightResults.add(fighterA.getName() + " attacked " + fighterB.getName() + " for " + Math.max(0, damageA) + " damage!\n");
                    }
                    visuals.add(() -> {
                        SwordAtoB.setVisibility(View.VISIBLE);
                        ShieldBtoA.setVisibility(View.VISIBLE);
                    });
                    localHealthB -= Math.max(0, damageA);
                    if (localHealthB < 0) localHealthB = 0;
                    if (localHealthB == 0) {
                        fightResults.add(fighterB.getName() + " has been defeated!\n");
                        visuals.add(() -> {
                            SwordAtoB.setVisibility(View.GONE);
                            ShieldBtoA.setVisibility(View.GONE);
                        });
                        break;
                    }
                }

                int attackValueB = fighterB.getAttack() + fighterB.getExperience() / 2;
                boolean missB = Math.random() < 0.2;
                boolean critB = !missB && Math.random() < 0.45;
                int damageB = attackValueB - fighterA.getDefense();
                if (missB) {
                    fightResults.add(fighterB.getName() + " missed the attack!\n");
                    visuals.add(() -> {
                        SwordBtoA.setVisibility(View.GONE);
                        ShieldAtoB.setVisibility(View.GONE);
                    });
                } else {
                    if (critB) {
                        damageB += 2;
                        fightResults.add(fighterB.getName() + " landed a critical hit, dealing " + Math.max(0, damageB) + " damage to " + fighterA.getName() + "!\n");
                    } else {
                        fightResults.add(fighterB.getName() + " attacked " + fighterA.getName() + " for " + Math.max(0, damageB) + " damage!\n");
                    }
                    visuals.add(() -> {
                        SwordBtoA.setVisibility(View.VISIBLE);
                        ShieldAtoB.setVisibility(View.VISIBLE);
                    });
                    localHealthA -= Math.max(0, damageB);
                    if (localHealthA < 0) localHealthA = 0;
                    if (localHealthA == 0) {
                        fightResults.add(fighterA.getName() + " has been defeated!\n");
                        visuals.add(() -> {
                            SwordBtoA.setVisibility(View.GONE);
                            ShieldAtoB.setVisibility(View.GONE);
                        });
                        break;
                    }
                }
            }

            Handler handler = new Handler();
            battleTextView.setText("");
            int[] healthA = {fighterA.getHealth()};
            int[] healthB = {fighterB.getHealth()};
            int[] localA = {fighterA.getHealth()};
            int[] localB = {fighterB.getHealth()};
            for (int i = 0; i < fightResults.size(); i++) {
                final int idx = i;
                handler.postDelayed(() -> {
                    SwordAtoB.setVisibility(View.GONE);
                    SwordBtoA.setVisibility(View.GONE);
                    ShieldAtoB.setVisibility(View.GONE);
                    ShieldBtoA.setVisibility(View.GONE);

                    visuals.get(idx).run();

                    String res = fightResults.get(idx);
                    if (res.contains("attacked") || res.contains("landed a critical hit")) {
                        int dmg = 0;
                        java.util.regex.Matcher m = java.util.regex.Pattern.compile("(dealing |for )(\\d+)").matcher(res);
                        if (m.find()) dmg = Integer.parseInt(m.group(2));
                        if (res.startsWith(fighterA.getName())) {
                            localB[0] -= dmg;
                            if (localB[0] < 0) localB[0] = 0;
                        } else if (res.startsWith(fighterB.getName())) {
                            localA[0] -= dmg;
                            if (localA[0] < 0) localA[0] = 0;
                        }
                    }

                    updateBattleAreaVisualsWithLocalHealth(
                            fighterA, fighterANameText, fighterAText, fighterAImage, localA[0], fighterA.getMaxHealth(),
                            fighterB, fighterBNameText, fighterBText, fighterBImage, localB[0], fighterB.getMaxHealth()
                    );
                    battleTextView.append(res);

                    handler.postDelayed(() -> {
                        SwordAtoB.setVisibility(View.GONE);
                        SwordBtoA.setVisibility(View.GONE);
                        ShieldAtoB.setVisibility(View.GONE);
                        ShieldBtoA.setVisibility(View.GONE);
                    }, 1000);

                    if (idx == fightResults.size() - 1) {
                        handler.postDelayed(() -> {
                            fighterA.setHealth(localA[0]);
                            fighterB.setHealth(localB[0]);
                            if (localA[0] == 0) {
                                fighterA.addLoss();
                                fighterB.addWin();
                            } else if (localB[0] == 0) {
                                fighterB.addLoss();
                                fighterA.addWin();
                            }
                            Storage.getInstance().getHome().addLutemon(Storage.getInstance().getBattleField().getLutemon(fighterA.getId()));
                            Storage.getInstance().getHome().addLutemon(Storage.getInstance().getBattleField().getLutemon(fighterB.getId()));
                            battleTextView.append("Both fighters were brought home to heal.\n");
                            fighterA = null;
                            fighterB = null;
                            updateBattleAreaVisuals(fighterA, fighterANameText, fighterAText, fighterAImage, fighterB, fighterBNameText, fighterBText, fighterBImage);
                        }, 2000);
                    }
                }, 2000 * idx);
            }
        });

        moveHomeFromBattleButton.setOnClickListener(v -> {
            for (Lutemon lutemon : new ArrayList<>(Storage.getInstance().getBattleField().listLutemons())) {
                Lutemon removedC = Storage.getInstance().getBattleField().getLutemon(lutemon.getId());
                if (removedC != null) Storage.getInstance().getHome().addLutemon(removedC);
            }
            fighterA = null;
            fighterB = null;
            updateBattleAreaVisuals(fighterA, fighterANameText, fighterAText, fighterAImage, fighterB, fighterBNameText, fighterBText, fighterBImage);
        });

        updateBattleAreaVisuals(fighterA, fighterANameText, fighterAText, fighterAImage, fighterB, fighterBNameText, fighterBText, fighterBImage);
        return view;
    }

    private void updateBattleAreaVisuals(
            Lutemon fighterA, TextView fighterANameText, TextView fighterAText, ImageView fighterAImage,
            Lutemon fighterB, TextView fighterBNameText, TextView fighterBText, ImageView fighterBImage) {
        if (fighterA == null) {
            fighterAText.setText("Nobody here :/");
            fighterANameText.setText("");
            fighterAImage.setImageDrawable(null);
        } else {
            fighterANameText.setText(fighterA.getName() + " (" + fighterA.getColor() + ")");
            fighterAText.setText("Attack: " + fighterA.getAttack() + "\nDefense: " + fighterA.getDefense() + "\nHP: " + fighterA.getHealth() + "/" + fighterA.getMaxHealth());
            setLutemonImage(fighterA, fighterAImage);
        }
        if (fighterB == null) {
            fighterBText.setText("Nobody here :/");
            fighterBNameText.setText("");
            fighterBImage.setImageDrawable(null);
        } else {
            fighterBNameText.setText(fighterB.getName() + " (" + fighterB.getColor() + ")");
            fighterBText.setText("Attack: " + fighterB.getAttack() + "\nDefense: " + fighterB.getDefense() + "\nHP: " + fighterB.getHealth() + "/" + fighterB.getMaxHealth());
            setLutemonImage(fighterB, fighterBImage);
        }
    }

    private void updateBattleAreaVisualsWithLocalHealth(
            Lutemon fighterA, TextView fighterANameText, TextView fighterAText, ImageView fighterAImage, int localHealthA, int maxHealthA,
            Lutemon fighterB, TextView fighterBNameText, TextView fighterBText, ImageView fighterBImage, int localHealthB, int maxHealthB
    ) {
        if (fighterA != null) {
            fighterANameText.setText(fighterA.getName() + " (" + fighterA.getColor() + ")");
            fighterAText.setText("Attack: " + fighterA.getAttack() + "\nDefense: " + fighterA.getDefense() + "\nHP: " + localHealthA + "/" + maxHealthA);
            setLutemonImage(fighterA, fighterAImage);
        }
        if (fighterB != null) {
            fighterBNameText.setText(fighterB.getName() + " (" + fighterB.getColor() + ")");
            fighterBText.setText("Attack: " + fighterB.getAttack() + "\nDefense: " + fighterB.getDefense() + "\nHP: " + localHealthB + "/" + maxHealthB);
            setLutemonImage(fighterB, fighterBImage);
        }
    }

    private void setLutemonImage(Lutemon lutemon, ImageView imageView) {
        switch (lutemon.getColor()) {
            case "Orange": imageView.setImageResource(R.drawable.orangelutemon); break;
            case "Black": imageView.setImageResource(R.drawable.blacklutemon); break;
            case "White": imageView.setImageResource(R.drawable.whitelutemon); break;
            case "Green": imageView.setImageResource(R.drawable.greenlutemon); break;
            case "Pink": imageView.setImageResource(R.drawable.pinklutemon); break;
        }
    }
}