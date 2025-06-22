package com.elssu.harkkatyo;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

public class TrainingArea extends LutemonLocation implements Serializable {

    private int trainCounter = 0;
    private static final int trainThreshold = 1;
    public TrainingArea() {
        super("TrainingArea");
    }

    public void addLutemon(Lutemon lutemon) {
        lutemons.put(lutemon.getId(), lutemon);

    }

    public int getTrainCounter() {
        return trainCounter;
    }
    public int getTrainThreshold() {
        return trainThreshold;
    }

    public void train() {
        Collection<Lutemon> lutemonList = lutemons.values();
        Lutemon lutemon = lutemonList.iterator().next();
        trainCounter += 1;
         if(!(trainCounter < trainThreshold)) {
             lutemon.addExperience(1);
             lutemon.addTimesTraining(1);
             trainCounter = 0;
         }

    }
}
