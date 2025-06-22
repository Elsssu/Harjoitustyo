package com.elssu.harkkatyo;

import android.util.Log;

public abstract class Lutemon {
    private static int idCounter = 1;
    protected String name;
    protected int health;
    protected int maxHealth;
    protected int experience;
    protected int defense;
    protected int attack;
    protected int id;
    protected int losses;
    protected int wins;
    protected String color;
    protected int  timesTraining;

    public Lutemon(String name, int attack, int defense, int maxHealth, String color){
        this.id = idCounter++;
        this.name = name;
        this.attack = attack;
        this.defense = defense;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.experience = 0;
        this.losses = 0;
        this.wins = 0;
        this.color = color;
        this.timesTraining = 0;

    }

    public int getNumberOfCreatedLutemons(){
        return idCounter;
    }
    public void defense(int dmg){
        health = health-(dmg-defense);
        if(health < 0 ) {
            this.health = 0;
        }
    }

    public int getId(){
        return id;
    }
    public String getColor(){
        return color;
    }
    public void addTimesTraining(int timesTraining) {
        this.timesTraining += timesTraining;
    }
    public void setIdCounter(int newIdCounter){
        idCounter = newIdCounter;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addLoss() {
        losses++;
    }

    public void addWin() {
        wins++;
    }

    public void addExperience(int experience) {
        this.experience += experience;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getExperience() {
        return experience;
    }

    public int getDefense() {
        return defense;
    }

    public int getAttack() {
        return attack;
    }

    public int getLosses() {
        return losses;
    }

    public int getWins() {
        return wins;
    }
}
