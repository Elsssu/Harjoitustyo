package com.elssu.harkkatyo;

import android.util.Log;

import com.elssu.harkkatyo.fragments.BattleFieldFragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BattleField extends LutemonLocation {


    public BattleField() {
        super("BattleField");
    }

    public List<String> fight(String results) {
        Iterator<Lutemon> iterator = lutemons.values().iterator();
        Lutemon fighterA = iterator.next();
        Lutemon fighterB = iterator.next();
        List<String> result = new ArrayList<>();
        while (fighterA.getHealth() > 0 && fighterB.getHealth() > 0) {

            int crit = 0;
            int attackValue = fighterA.getAttack() + fighterA.getExperience() / 2;
            if (missRoll()) {
                result.add(fighterA.getName() + " missed the attack!\n");
            } else {
                if (criticalHitRoll()) {
                    crit = 2;
                    attackValue += crit;
                    result.add(fighterA.getName() + " landed a critical hit, dealing " + (attackValue - fighterB.getDefense()) + " damage to " + fighterB.getName() + "!\n");
                } else {
                    result.add(fighterA.getName() + " attacked " + fighterB.getName() + " for " + (attackValue - fighterB.getDefense()) + " damage!\n");
                }
                fighterB.defense(attackValue);
                if (fighterB.getHealth() <= 0) {
                    result.add(fighterB.getName() + " has been defeated!\n");
                    break;
                }
            }


            crit = 0;
            attackValue = fighterB.getAttack() + fighterB.getExperience() / 2;
            if (missRoll()) {
                result.add(fighterB.getName() + " missed the attack!\n");
            } else {
                if (criticalHitRoll()) {
                    crit = 2;
                    attackValue += crit;
                    result.add(fighterB.getName() + " landed a critical hit, dealing " + (attackValue - fighterA.getDefense()) + " damage to " + fighterA.getName() + "!\n");
                } else {
                    result.add(fighterB.getName() + " attacked " + fighterA.getName() + " for " + (attackValue - fighterA.getDefense()) + " damage!\n");
                }
                fighterA.defense(attackValue);
                if (fighterA.getHealth() <= 0) {
                    result.add(fighterA.getName() + " has been defeated!\n");
                    break;
                }
            }
        }
        return result;
    }
    public boolean criticalHitRoll() {
        return Math.random() < 0.45;
    }

    public boolean missRoll() {
        return Math.random() < 0.2;
    }


}