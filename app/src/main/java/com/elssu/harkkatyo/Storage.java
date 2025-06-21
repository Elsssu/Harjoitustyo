package com.elssu.harkkatyo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Storage {
    private HashMap<Integer, Lutemon> lutemons = new HashMap<>();
    private Home home =new Home();
    private TrainingArea trainingArea = new TrainingArea();
    private BattleField battleField = new BattleField();
    private static Storage Storage = null;
    private Storage(){}
    public static Storage getInstance() {
        if(Storage == null) {
            Storage = new Storage();
        }
        return Storage;
    }
    public void addLutemon(Lutemon lutemon) {

        lutemons.put(lutemon.getId(), lutemon);
    }

    public Lutemon getLutemon(int id){
        return lutemons.get(id);
    }
    public ArrayList<Lutemon> getAllLutemon(){
        ArrayList<Lutemon> list = new ArrayList<>(lutemons.values());
        return list;
    }
    public void moveLutemon(int targetId, LutemonLocation from, LutemonLocation to) {
        for (Iterator<Lutemon> it = from.listLutemons().iterator(); it.hasNext(); ) {
            Lutemon l = it.next();
            if (l.getId() == targetId) {
                it.remove();
                to.listLutemons().add(l);
                break;
            }
        }
    }
    public void listLutemons(){

    }

    public Home getHome() {
        return home;
    }

    public TrainingArea getTrainingArea() {
        return trainingArea;
    }

    public BattleField getBattleField() {
        return battleField;
    }



}
