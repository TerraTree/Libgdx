package com.RPGproject.game;

import com.badlogic.gdx.graphics.Texture;

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

    public Armour getArmour() {
        return armour;
    }

    public void setArmour(Armour armour) {
        this.armour = armour;
    }

    public Character(int str, int dex, int agi, int wis, int intel,Texture texture) {

        this.armour = new Armour();
        this.armour.setDefense(0);
        this.setStrength(str);
        this.setDexterity(dex);
        this.setAgility(agi);
        this.setWisdom(wis);
        this.setIntelligence(intel);
        this.setMaxHealth(level*2);
        this.setCurrentHealth(this.getMaxHealth());
        this.setDefense(this.armour.getDefense()+this.getDexterity()*2+this.getLevel());
        this.setTexture(texture);
    }
}
