package com.RPGproject.game;

import java.io.FileWriter;
import java.io.IOException;
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

    public Consumable(String name,String type, ArrayList<Integer> statChanges, String target, int duration,int cost){
        this.setName(name);
        this.setType(type);
        this.statChanges=statChanges;
        this.target = target;
        this.duration = duration;
        this.setMagic(true);
        this.setQuantity(1);
        this.setCost(cost);
    }

    @Override
    public void save(FileWriter fileWrite) throws IOException {
        fileWrite.write(getName()+": "+getCost()+"\n");
        fileWrite.write("{"+getType()+"\n");
        fileWrite.write(duration+"\n");
        fileWrite.write(target+"\n");
        String str = "[";
        for (int stat:statChanges) {
            str+=(stat+",");
        }
        str=str.substring(0,str.length()-1);
        str+="]}";
        fileWrite.write(str+"\n");
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
