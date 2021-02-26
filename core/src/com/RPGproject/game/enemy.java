package com.RPGproject.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class enemy extends entity{
	
	int aiStrategy;
	int state;
    Character targetChar;
    private Vector2 battlePos;

    public enemy(int str, int dex, int agi, int wis, int intel,int health,int defense,int level,int exp) {

        this.setStrength(str);
        this.setDexterity(dex);
        this.setAgility(agi);
        this.setWisdom(wis);
        this.setIntelligence(intel);
        this.setMaxHealth(health);
        this.setDefense(defense);
        this.setExp(exp);
        this.setCurrentHealth(this.getMaxHealth());
        this.setActive(true);
        aiStrategy=1;
    }
	
	public Attack turn(Vector2 position, ArrayList<ArrayList<Character>> playerGrid){
        int i = 0;
        int j = 0;
        Vector2 playerPos = new Vector2();
        for (ArrayList<Character> p:playerGrid) {
            for(Character c: p){
                if (c!=null){
                    targetChar = c;
                    if(c.getCurrentHealth()!=0) {
                        playerPos = new Vector2(i, j);
                    }
                }
                i++;
            }
            j++;
            i = 0;
        }
        int damage=this.getStrength();
        Attack enemAction = new Attack(this,position,targetChar,playerPos,false,"bashers",damage);
        return enemAction;
    }
	
	
}
