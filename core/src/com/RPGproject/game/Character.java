package com.RPGproject.game;

public class Character extends entity{
    private Weapon weapon;
    private Armour armour;
    private int level;
    private int exp;

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public int getLevel() {
    	return level;
    }
    public void setLevel(int level) {
    	this.level = level;
    }
    
    public int getExp() {
    	return exp;
    }
    public void setExp(int exp) {
    	this.exp = exp;
    }
    
    
    public Character(int str,int dex,int agi,int wis, int intel) {
    	setStrength(str);
    	this.setDexterity(dex);
    	this.setAgility(agi);
    	this.setWisdom(wis);
    	this.setIntelligence(intel);
    	this.setMaxHealth(level*2);
    }
}
