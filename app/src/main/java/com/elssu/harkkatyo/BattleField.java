package com.elssu.harkkatyo;

import android.util.Log;

import com.elssu.harkkatyo.fragments.BattleFieldFragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BattleField extends LutemonLocation {

    public interface FightListener {
        void onFightUpdate(String message);
        void onFightEnd(String result);
    }
    public BattleField() {
        super("BattleField");
    }

    public List<String> fight(String results) {
        Iterator<Lutemon> iterator = lutemons.values().iterator();
        Lutemon fighterA = iterator.next();
        Lutemon fighterB = iterator.next();
        List<String> result = new ArrayList<>();
        while ( fighterA.getHealth() > 0 && fighterB.getHealth() > 0) {
            int crit = 0;
            //Check if attack misses or is a critical hit
            //If attack hits then A attack B with possible added critical hit damage
            int dmgtaken = fighterB.getDefense() - fighterA.attack + crit + fighterA.experience / 2;
            if(missRoll()) {
                result.add(fighterA.getName() + " missed the attack!\n");
            }else {
                if (criticalHitRoll()) {
                    crit = 3;
                    fighterB.defense(fighterA.attack + crit + fighterA.experience / 2);
                    result.add(fighterA.getName() + " landed a critical hit, dealing " + (dmgtaken + crit) + " damage to " + fighterB.getName() + "!\n");


                }else {
                    fighterB.defense(fighterA.attack + crit + fighterA.experience / 2);
                    result.add(fighterA.getName() + " attacked " + fighterB.getName() + " for " + dmgtaken + " damage!\n");
                }
                //check if fighterB is able to fight
                if (fighterB.getId() <= 0) {

                    break;
                }
            }
            crit = 0;
            dmgtaken = fighterA.getDefense() - fighterB.attack + crit + fighterB.experience / 2;

            if(missRoll()) {
                result.add(fighterB.getName() + " missed the attack!\n");
            }else {
                if (criticalHitRoll()) {
                    crit = 3;
                    fighterA.defense(fighterB.attack + crit + fighterB.experience / 2);
                    result.add(fighterB.getName() + " landed a critical hit, dealing " + (dmgtaken + crit) + " damage to " + fighterA.getName() + "!\n");

                }else {
                    fighterA.defense(fighterB.attack + crit + fighterA.experience / 2);
                    result.add(fighterB.getName() + " landed a critical hit, dealing " + (dmgtaken + crit) + " damage to " + fighterA.getName() + "!\n");

                    fighterA.defense(fighterB.attack + crit + fighterB.experience / 2);
                }
                //check if fighterB is able to fight
                if (fighterA.getId() <= 0) {
                    result.add(fighterA.getName() + " has been defeated!\n");
                    break;
                }
            }
            fighterA.setHealth(fighterA.getMaxHealth());
            fighterB.setHealth(fighterB.getMaxHealth());
        }

        return result;
    }
    public boolean criticalHitRoll() {
        //get a true or false for critical hit with 10% chance to be true
        return Math.random() < 0.1;
    }

    public boolean missRoll() {
        //get a true or false for missing attack with 5% chance to be true
        return Math.random() < 0.05;
    }


}