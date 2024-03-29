package com.RPGproject.game;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Equipment extends Item{
    String equipmentPosition;

    String name;
    private ArrayList<Integer> stats; //order of stats: defense,attack,health,strength,dexterity,agility,wisdom,intelligence

    public String getEquipmentPosition() {
        return equipmentPosition;
    }

    public void setEquipmentPosition(String equipmentPosition) {
        this.equipmentPosition = equipmentPosition;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getStats() {
        return stats;
    }

    public void setStats(ArrayList<Integer> stats) {
        this.stats = stats;
    }

    public Equipment(String equipmentPosition, String type, String name, ArrayList<Integer> statPoints,int cost){
        this.equipmentPosition = equipmentPosition;
        this.setType(type);
        this.name=name;
        this.stats=statPoints;
        this.setCost(cost);
        if (stats.size()>3){
            this.setMagic(true);
        }
        setQuantity(1);
    }

    @Override
    public void save(FileWriter fileWrite) throws IOException {
        fileWrite.write(getName()+": "+getCost()+"\n");
        fileWrite.write("{"+getType()+"\n");
        fileWrite.write(getEquipmentPosition()+"\n");
        String str = "[";
        System.out.println("str: "+str);
        for (int stat:stats) {
            str+=(stat+",");
            System.out.println("str: "+str);
        }
        if(str.length()>1) {
            str = str.substring(0, str.length() - 1);
        }
        System.out.println("str: "+str);
        str+="]}";
        System.out.println("str: "+str);
        fileWrite.write(str+"\n");
    }

    public void Equip(Character target){
        try {
            target.setDefense(target.getDefense() + stats.get(0));
            //target.setDamage(target.getDefense()+stats.get(1));
            target.setMaxHealth(target.getDefense() + stats.get(2));
            target.setCurrentHealth(target.getCurrentHealth() + stats.get(2));
            target.setStrength(target.getDefense() + stats.get(3));
            target.setDexterity(target.getDefense() + stats.get(4));
            target.setAgility(target.getDefense() + stats.get(5));
            target.setWisdom(target.getDefense() + stats.get(6));
            target.setIntelligence(target.getDefense() + stats.get(7));
        }
        catch(Exception e){

        }
    }
}
