package com.elssu.harkkatyo;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    public Home getHome() {
        return home;
    }

    public TrainingArea getTrainingArea() {
        return trainingArea;
    }

    public BattleField getBattleField() {
        return battleField;
    }

    public void saveLutemonsToFile(Context context, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                context.openFileOutput(filename, Context.MODE_PRIVATE))) {
            oos.writeObject(lutemons);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadLutemonsFromFile(Context context, String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(
                context.openFileInput(filename))) {
            lutemons = (HashMap<Integer, Lutemon>) ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}








