package com.RPGproject.game;

public class entity {
    private String name;
    private int health; //health, pretty self explanatory
    private int armour; //reduces damage taken
    private int strength; //increases damage of attacks
    private int dexterity; //increases armour
    private int agility; //increases health + speed of attacks
    private int wisdom; //increases amount of magic available
    private int intelligence;//increases damage of magic attacks.


    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getArmour() {
        return armour;
    }

    public void setArmour(int armour) {
        this.armour = armour;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public entity() {
    }
}
