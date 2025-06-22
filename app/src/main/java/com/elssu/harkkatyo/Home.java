package com.elssu.harkkatyo;



public class Home extends LutemonLocation {
    public Home() {
        super("Home");
    }

    public void addLutemon(Lutemon lutemon) {
        regainhealth(lutemon);
        super.addLutemon(lutemon);
    }
    public void regainhealth(Lutemon lutemon) {
        lutemon.setHealth(lutemon.getMaxHealth());

    }



}
