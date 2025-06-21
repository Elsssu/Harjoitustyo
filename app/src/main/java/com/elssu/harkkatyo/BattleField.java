package com.elssu.harkkatyo;

import java.util.Iterator;

public class BattleField extends LutemonLocation {
    public BattleField() {
        super("BattleField");
    }

    public void fightLutemon() {
        Iterator<Lutemon> iterator = lutemonLocations.values().iterator();
        Lutemon fighterA = iterator.next();
        Lutemon fighterB = iterator.next();

        while ( fighterA.getHealth() > 0 && fighterB.getHealth() > 0) {
            int crit = 0;
            //Check if attack misses or is a critical hit
            //If attack hits then A attack B with possible added critical hit damage
            if(missRoll()) {

            }else if (criticalHitRoll()){
                crit = 3;
            }else {
                fighterB.defense(fighterA.attack + crit + fighterA.experience/2);
            }
            //check if fighterB is able to fight
            if (fighterB.getId() <= 0) {
                break;
            }

            crit = 0;
            //Same as above but now B attacks A
            if(missRoll()) {

            }else if (criticalHitRoll()){
                crit = 3;
            }else {
                fighterA.defense(fighterB.attack + crit + fighterB.experience/2);
            }

        }
        if (fighterA.getHealth() <= 0) {
            //Fighter A is dead
            fighterA.setHealth(0);
            fighterB.addExperience(2);
        } else if (fighterB.getHealth() <= 0) {
            //Fighter B is dead
            fighterB.setHealth(0);
            fighterA.addExperience(2);
        }


        //Moving Fighters to Home
        Storage storage = Storage.getInstance();
        storage.moveLutemon(fighterB.getId(), storage.getTrainingArea(), storage.getHome());
        storage.moveLutemon(fighterA.getId(), storage.getTrainingArea(), storage.getHome());


    }
    public boolean criticalHitRoll() {
        //get a true or false for critical hit with 10% chance to be true
        return Math.random() < 0.1;
    }

    public boolean missRoll() {
        //get a true or false for missing attack with 5% chance to be true
        return Math.random() < 0.05;
    }
    public void addLutemon(Lutemon lutemon) {
        super.addLutemon(lutemon);
    }

}