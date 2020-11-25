package com.RPGproject.game;

public class Character extends entity{
    private Weapon weapon;


    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Character() {
    }
}
