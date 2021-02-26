package com.RPGproject.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;
import java.util.ArrayList;

public class Character extends entity{
    private Weapon weapon;
    private Armour armour;
    private int level;
    private CharClass charClass;
    private String weakness;
    private ArrayList<String> spellList;
    private BitmapFont font;
    private Point battlePos;

    public Point getBattlePos() {
        return battlePos;
    }

    public void setBattlePos(Point battlePos) {
        this.battlePos = battlePos;
    }

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
        //this.setCurrentHealth(this.getMaxHealth());
        this.setDefense(this.armour.getDefense() + this.getDexterity() * 2 + this.getLevel());

    }

    public void renderLevelUp(ShapeRenderer sr, SpriteBatch batch){
        font = new BitmapFont();
        font.getData().setScale(2);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.WHITE);
        sr.rect(100,50,Gdx.graphics.getWidth()-200,Gdx.graphics.getHeight()-100);
        sr.setColor(Color.GRAY);
        for(int i=0;i<6;i++){
            sr.rect(Gdx.graphics.getWidth()/2 -700,900-i*150,500,100);
        }
        sr.rect(Gdx.graphics.getWidth()-500,100,300,100);
        sr.end();
        batch.begin();
        font.setColor(Color.BLACK);
        font.draw(batch,this.getName()+" is now level "+this.getLevel(),Gdx.graphics.getWidth()/2 -700,950);
        font.draw(batch,"Strength: "+this.getStrength(),Gdx.graphics.getWidth()/2 -700,800);
        font.draw(batch,"Dexterity: "+this.getDexterity(),Gdx.graphics.getWidth()/2 -700,650);
        font.draw(batch,"Agility: "+this.getAgility(),Gdx.graphics.getWidth()/2 -700,500);
        font.draw(batch,"Wisdom: "+this.getWisdom(),Gdx.graphics.getWidth()/2 -700,350);
        font.draw(batch,"Intelligence: "+this.getIntelligence(),Gdx.graphics.getWidth()/2 -700,200);
        font.draw(batch,"Enter to continue",Gdx.graphics.getWidth()-500,150);
        batch.end();
    }

    public void turn (){

    }
}