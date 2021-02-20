package com.RPGproject.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public class Character extends entity{
    private Weapon weapon;
    private Armour armour;
    private int level;
    private CharClass charClass;
    private String weakness;
    private ArrayList<String> spellList;

    public CharClass getCharClass() {
        return charClass;
    }

    public void setCharClass(CharClass charClass) {
        this.charClass = charClass;
    }

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

    public Armour getArmour() {
        return armour;
    }

    public void setArmour(Armour armour) {
        this.armour = armour;
    }

    public Character(int str, int dex, int agi, int wis, int intel,int level, Texture texture) {

        this.armour = new Armour();
        this.armour.setDefense(0);
        this.setStrength(str);
        this.setDexterity(dex);
        this.setAgility(agi);
        this.setWisdom(wis);
        this.setIntelligence(intel);
        //this.setMaxHealth(level * 2);
        //this.setCurrentHealth(this.getMaxHealth());
        //this.setDefense(this.armour.getDefense() + this.getDexterity() * 2 + this.getLevel());
        this.setSprite(new Sprite(texture));
        this.level=1;
        this.setActive(true);
        this.charClass= new CharClass("fighter","strength","dexterity",new ArrayList<String>());
        this.charClass.getActions().add("attack");
        this.charClass.getActions().add("items");
        this.charClass.getActions().add("run");
    }
    public Character(int str, int dex, int agi, int wis, int intel,int level,int exp,int currentHp,int maxHp, Texture texture) {

        this.armour = new Armour();
        this.armour.setDefense(0);
        this.setStrength(str);
        this.setDexterity(dex);
        this.setAgility(agi);
        this.setWisdom(wis);
        this.setIntelligence(intel);
        this.setMaxHealth(maxHp);
        this.setCurrentHealth(currentHp);
        this.setDefense(this.armour.getDefense() + this.getDexterity() * 2 + this.getLevel());
        this.setSprite(new Sprite(texture));
        this.level = level;
        this.setExp(exp);
        this.setActive(true);
        this.charClass= new CharClass("fighter","strength","dexterity",new ArrayList<String>());
        this.charClass.getActions().add("attack");
        this.charClass.getActions().add("items");
        this.charClass.getActions().add("run");
    }

    public void updateStats(){
        this.setMaxHealth(level*2+getAgility()*3+10);
        this.setCurrentHealth(this.getMaxHealth());
        this.setDefense(this.armour.getDefense() + this.getDexterity() * 2 + this.getLevel());

    }

    public void turn (){

    }
}