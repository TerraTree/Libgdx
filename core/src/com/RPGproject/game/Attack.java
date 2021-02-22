package com.RPGproject.game;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Attack {
    private Vector2 enemyPos;
    private Vector2 characterPos;
    private enemy enemy;
    private Character character;
    private boolean isPlayerAttacking;
    private String damageType;
    private int damage;

    public Attack(enemy enemy, Vector2 enemyPos,Character character, Vector2 characterPos, boolean isPlayerAttacking, String damageType, int damage) {
        this.enemy = enemy;
        this.enemyPos = enemyPos;
        this.character = character;
        this.characterPos = characterPos;
        this.isPlayerAttacking = isPlayerAttacking;
        this.damageType = damageType;
        this.damage = damage;
    }

    public entity damage(entity attacker,entity defender,Queue<Attack> battleQueue) {
        if (attacker.isActive()) {
            if (defender.getWeakness() == damageType) {
                damage *= 2;
            }
            defender.setCurrentHealth(defender.getCurrentHealth() - damage);
            System.out.println("damage: "+damage);
            if(defender.getCurrentHealth()<=0){
                defender.setCurrentHealth(0);
                defender.setActive(false);
            }
        }
        return defender;
    }

    public void attacking(Queue<Attack> battleQueue,ArrayList<ArrayList<Character>> playerGrid, ArrayList<ArrayList<enemy>> enemyGrid){
        boolean interruption = false;
        if(isPlayerAttacking){
            enemy= (enemy) damage(character,enemy,battleQueue);
            if(enemy.getCurrentHealth()<=0){
                for (ArrayList<Character> ar: playerGrid) {
                    for(Character c:ar){
                        if(c!=null){
                            if(c.getCurrentHealth()>0) {
                                c.setExp(c.getExp()+enemy.getExp());
                                System.out.println("exp time for "+c.getName() + ": " +c.getExp());
                                interruption = c.getCharClass().levelUp(c);
                            }
                        }
                    }
                }
                enemyGrid.get((int) enemyPos.x).set((int) enemyPos.y,null);
            }
            else{
                enemyGrid.get((int) enemyPos.x).get((int) enemyPos.y).setCurrentHealth(enemy.getCurrentHealth());
            }
        }
        else{
            character = (Character) damage(enemy,character,battleQueue);
            playerGrid.get((int) characterPos.y).get((int) characterPos.x).setCurrentHealth(character.getCurrentHealth());

        }
        if(interruption==false){
        battleQueue.poll();
        }
        if(battleQueue.size()>0 && interruption == false) {

            battleQueue.peek().attacking(battleQueue, playerGrid, enemyGrid);
        }
    }

    public Vector2 getEnemyPos() {
        return enemyPos;
    }

    public void setEnemyPos(Vector2 enemyPos) {
        this.enemyPos = enemyPos;
    }

    public Vector2 getCharacterPos() {
        return characterPos;
    }

    public void setCharacterPos(Vector2 characterPos) {
        this.characterPos = characterPos;
    }

    public Character getCharacter() {
        return character;
    }

    public enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(enemy enemy) {
        this.enemy = enemy;
    }

    public Character setCharacter(Character character) {
        return character;
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
