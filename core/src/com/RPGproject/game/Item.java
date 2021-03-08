package com.RPGproject.game;

import java.util.ArrayList;

public class Item {

    private String name;
    private String type;
    private int quantity;
    private boolean magic;
    private int cost;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isMagic() {
        return magic;
    }

    public void setMagic(boolean magic) {
        this.magic = magic;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Item(){

    }

    public void changeStats(entity target, ArrayList<Integer> statChanges){
        target.setDefense(target.getDefense()+statChanges.get(0));
        //target.setDamage(target.getDefense()+statChanges.get(1));
        target.setMaxHealth(target.getDefense()+statChanges.get(2));
        target.setStrength(target.getDefense()+statChanges.get(3));
        target.setDexterity(target.getDefense()+statChanges.get(4));
        target.setAgility(target.getDefense()+statChanges.get(5));
        target.setWisdom(target.getDefense()+statChanges.get(6));
        target.setIntelligence(target.getDefense()+statChanges.get(7));
    }
}
