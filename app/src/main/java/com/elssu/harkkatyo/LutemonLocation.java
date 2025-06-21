package com.elssu.harkkatyo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public abstract class LutemonLocation {
    protected final String name;

    protected HashMap<Integer, Lutemon> lutemonLocations = new HashMap<>();

    public LutemonLocation(String name) {
        this.name = name;
    }

    public void addLutemon(Lutemon lutemon){
            lutemonLocations.put(lutemon.getId(), lutemon);
    }
    public Lutemon getLutemon(int id){
        return lutemonLocations.get(id);
    }
    public Lutemon removeLutemon(int id) {
        return lutemonLocations.remove(id);
    }

    public Collection<Lutemon> listLutemons() {
        return lutemonLocations.values();
    }

    public String getLocationName() {
        return name;
    }
    public void removeLutemon(){
        lutemonLocations.clear();
    }
}
