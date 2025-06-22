package com.elssu.harkkatyo;

import com.elssu.harkkatyo.fragments.BattleFieldFragment;

import java.util.Iterator;

public class BattleField extends LutemonLocation {

    public interface FightListener {
        void onFightUpdate(String message);
        void onFightEnd(String result);
    }
    public BattleField() {
        super("BattleField");
    }

    public void fight(FightListener listener) {
        Iterator<Lutemon> iterator = lutemons.values().iterator();
        Lutemon fighterA = iterator.next();
        Lutemon fighterB = iterator.next();
        String listenerInfoA = "";
        String listenerInfoB = "";
        while ( fighterA.getHealth() > 0 && fighterB.getHealth() > 0) {
            int crit = 0;
            String listenerInfo = "";
            //Check if attack misses or is a critical hit
            //If attack hits then A attack B with possible added critical hit damage
            if(missRoll()) {
                //MIss
            }else {
                if (criticalHitRoll()) {
                    crit = 3;


                }
                fighterB.defense(fighterA.attack + crit + fighterA.experience/2);
                //check if fighterB is able to fight
                if (fighterB.getId() <= 0) {

                    break;
                }
            }
            crit = 0;
            if(missRoll()) {

            }else {
                if (criticalHitRoll()) {
                    crit = 3;
                }
                fighterA.defense(fighterB.attack + crit + fighterB.experience/2);
                //check if fighterB is able to fight
                if (fighterA.getId() <= 0) {
                    break;
                }
            }

        }
        if (fighterA.getHealth() <= 0) {
            //Moving unconscious fighter A to home
            Storage.getInstance().getHome().addLutemon(Storage.getInstance().getBattleField().getLutemon(fighterA.getId()));
            fighterB.addExperience(2);
        } else if (fighterB.getHealth() <= 0) {
            //Moving unconscious fighter B to home
            Storage.getInstance().getHome().addLutemon(Storage.getInstance().getBattleField().getLutemon(fighterB.getId()));
            fighterA.addExperience(2);
        }


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