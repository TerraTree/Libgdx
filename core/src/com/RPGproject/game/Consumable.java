package com.RPGproject.game;

import java.util.ArrayList;

public class Consumable extends Item{
    private String target;
    private ArrayList<Integer> statChanges; //order of stats: defense,attack,health,strength,dexterity,agility,wisdom,intelligence
    private int duration;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public ArrayList<Integer> getStatChanges() {
        return statChanges;
    }

    public void setStatChanges(ArrayList<Integer> statChanges) {
        this.statChanges = statChanges;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Consumable(String name, ArrayList<Integer> statChanges, String target, int duration,int cost){
        this.setName(name);
        this.statChanges=statChanges;
        this.target = target;
        this.duration = duration;
        this.setMagic(true);
        this.setQuantity(1);
        this.setCost(cost);
    }

    public void Consume(entity target){
        changeStats(target,statChanges);
        if(duration>0) {
            target.getStatusEffects().add(this);
        }
    }

    public void durationEnd(entity target){
        for (int i:statChanges) {
            i=-i;
        }
        changeStats(target,statChanges);
    }
}
