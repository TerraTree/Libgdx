package com.RPGproject.game;

public class entity {
    private String name;

    private String icon;
    private int maxHealth;
    private int currentHealth; //health, pretty self explanatory
    private int defense; //reduces damage taken
    private int strength; //increases damage of attacks
    private int dexterity; //increases armour
    private int agility; //increases health + speed of attacks
    private int wisdom; //increases amount of magic available
    private int intelligence;//increases damage of magic attacks.
    private int exp;
    

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int offset) {
    	this.maxHealth=this.agility+offset;
    }
    
    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
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

    public int getExp() {
    	return exp;
    }
    public void setExp(int exp) {
    	this.exp = exp;
    }

    public entity() {
    }
}
