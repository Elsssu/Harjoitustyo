package com.elssu.harkkatyo;

import java.util.Collection;
import java.util.HashMap;

public abstract class LutemonLocation {
    protected final String name;

    protected HashMap<Integer, Lutemon> lutemons = new HashMap<>();

    public LutemonLocation(String name) {
        this.name = name;
    }

    public void addLutemon(Lutemon lutemon){
        lutemons.put(lutemon.getId(), lutemon);
        Storage.getInstance().addLutemon(lutemon);
    }
    public Lutemon getLutemon(int id){
        Lutemon movingLutemon = lutemons.get(id);
        lutemons.remove(id);
        return movingLutemon;
    }
    public Collection<Lutemon> listLutemons() {
        return lutemons.values();
    }
}
