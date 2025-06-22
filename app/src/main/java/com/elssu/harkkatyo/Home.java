package com.elssu.harkkatyo;

import android.widget.Toast;

import java.util.HashMap;

public class Home extends LutemonLocation {
    public Home() {
        super("Home");
    }

    public void createLutemon(Lutemon lutemon) {
        Storage.getInstance().addLutemon(lutemon);
        addLutemon(lutemon);
    }
    public void addLutemon(Lutemon lutemon) {
        lutemon.setHealth(lutemon.getMaxHealth()); // Heal the Lutemon
        super.addLutemon(lutemon); // Add to Home
    }



}
