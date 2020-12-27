package com.RPGproject.game;

public class enemy extends entity{
	
	int aiStrategy;
	int state;
	
	public enemy(int str, int dex, int agi, int wis, int intel) {

        this.setStrength(str);
        this.setDexterity(dex);
        this.setAgility(agi);
        this.setWisdom(wis);
        this.setIntelligence(intel);
        this.setMaxHealth(10);
        this.setCurrentHealth(this.getMaxHealth());
        this.setDefense(2);
    }
	
	
	
	
}
