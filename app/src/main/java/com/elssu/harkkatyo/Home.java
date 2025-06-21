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
    public void regainHealth () {
        for(Lutemon l : lutemonLocations.values()) {
            l.setHealth(l.getMaxHealth());
        }

    }

}
