package com.RPGproject.game;

import com.badlogic.gdx.math.Vector2;

public class Attack {
    Vector2 enemy;
    Vector2 character;
    boolean isPlayerAttacking;
    String damageType;
    int damage;

    public Attack(Vector2 enemy, Vector2 character, boolean isPlayerAttacking, String damageType, int damage) {
        this.enemy = enemy;
        this.character = character;
        this.isPlayerAttacking = isPlayerAttacking;
        this.damageType = damageType;
        this.damage = damage;
    }

    public Vector2 getEnemy() {
        return enemy;
    }

    public void setEnemy(Vector2 enemy) {
        this.enemy = enemy;
    }

    public Vector2 getCharacter() {
        return character;
    }

    public void setCharacter(Vector2 character) {
        this.character = character;
    }

    public boolean isPlayerAttacking() {
        return isPlayerAttacking;
    }

    public void setPlayerAttacking(boolean playerAttacking) {
        isPlayerAttacking = playerAttacking;
    }

    public String getDamageType() {
        return damageType;
    }

    public void setDamageType(String damageType) {
        this.damageType = damageType;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
