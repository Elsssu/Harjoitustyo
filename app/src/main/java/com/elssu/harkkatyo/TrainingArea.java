package com.elssu.harkkatyo;

import java.util.HashMap;

public class TrainingArea extends LutemonLocation {
    private HashMap<Integer, Lutemon> lutemons = new HashMap<>();

    public TrainingArea() {
        super("TrainingArea");
    }

    private Lutemon currentLutemon;

    public Lutemon getCurrentLutemon() {
        return currentLutemon;
    }

    public void setCurrentLutemon(Lutemon lutemon) {
        this.currentLutemon = lutemon;
    }

    public void train() {
        Lutemon lutemon = lutemonLocations.get(0);


    }
}
