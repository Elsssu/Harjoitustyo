package com.elssu.harkkatyo;

import android.content.Context;

import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;


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
            Toast .makeText(context, "Lutemons saved successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadLutemonsFromFile(Context context, String filename) {
        Toast.makeText(context, "Loading Lutemons...", Toast.LENGTH_SHORT).show();
        try (ObjectInputStream ois = new ObjectInputStream(context.openFileInput(filename))) {
            HashMap<Integer, Lutemon> loadedLutemons = (HashMap<Integer, Lutemon>) ois.readObject();


            StringBuilder names = new StringBuilder();
            for (Lutemon l : loadedLutemons.values()) {
                names.append(l.getName()).append(", ");
            }
            Toast.makeText(context, "Loaded from file: " + names.toString(), Toast.LENGTH_LONG).show();

            lutemons = loadedLutemons;
            home = new Home();
            trainingArea = new TrainingArea();
            battleField = new BattleField();
            for (Lutemon l : lutemons.values()) {
                home.addLutemon(l);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}








