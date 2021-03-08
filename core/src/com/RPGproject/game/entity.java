package com.RPGproject.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public class entity {
    private String name;

    private String icon;
    private Sprite sprite;
    private int maxHealth; //max health, can't be healed over that amount
    private int currentHealth; //current health, pretty self explanatory
    private int defense; //reduces damage taken
    private int strength; //increases damage of attacks
    private int dexterity; //increases armour, hit chance
    private int agility; //increases health + order during battle
    private int wisdom; //increases amount of magic available
    private int intelligence;//increases damage of magic attacks.
    private int exp;
    private boolean active;
    private ArrayList<String> actions;
    private ArrayList<Consumable> statusEffects;

    public ArrayList<String> getActions() {
        return actions;
    }

    public void setActions(ArrayList<String> actions) {
        this.actions = actions;
    }

    private String weakness;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeakness() {
        return weakness;
    }

    public void setWeakness(String weakness) {
        this.weakness = weakness;
    }

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public entity() {
    }

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

    public ArrayList<Consumable> getStatusEffects() {
        return statusEffects;
    }

    public void setStatusEffects(ArrayList<Consumable> statusEffects) {
        this.statusEffects = statusEffects;
    }
}
