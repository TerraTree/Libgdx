package com.RPGproject.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Character extends entity{
    private ArrayList<Equipment> charEquip; //order is: weapon, off hand, helmet, chestplate,leggings
    private int level;
    private CharClass charClass;
    private String weakness;
    private ArrayList<String> spellList;
    private BitmapFont font;
    private Point battlePos;

    public ArrayList<Equipment> getCharEquip(){
    	return charEquip;
    }
    
    public void setCharEquip(ArrayList<Equipment> charEquip) {
    	this.charEquip=charEquip;
    }
    
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Character(int str, int dex, int agi, int wis, int intel, int level, Texture texture) {
        charEquip=new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            charEquip.add(null);
        }
        this.setStatusEffects(new ArrayList<Consumable>());
        this.setStrength(str);
        this.setDexterity(dex);
        this.setAgility(agi);
        this.setWisdom(wis);
        this.setIntelligence(intel);
        this.setCharEquip(new ArrayList<Equipment>());
		this.getCharEquip().add(new Equipment("Fists","weapon","Bare Hands",new ArrayList<Integer>(),0));
		this.getCharEquip().add(new Equipment("off hand",null,"empty",new ArrayList<Integer>(),0));
		this.getCharEquip().add(new Equipment("helmet","equipment","Cloth Cap",new ArrayList<Integer>(),5));
		this.getCharEquip().add(new Equipment("chestplate","equipment","Cloth Shirt",new ArrayList<Integer>(),5));
		this.getCharEquip().add(new Equipment("leggings","equipment","Cloth Trousers",new ArrayList<Integer>(),5));
        int equipDefense=0;
        for (Equipment e:charEquip) {
            if(e.getStats().size()!=0) {
                equipDefense += e.getStats().get(0);
            }
        }
        this.setDefense(equipDefense + this.getDexterity() * 2 + this.getLevel());
        this.setSprite(new Sprite(texture));
        this.level=1;
        this.setActive(true);
        this.charClass= new CharClass("fighter");
        this.charClass.getActions().add("attack");
        this.charClass.getActions().add("items");
        this.charClass.getActions().add("run");
    }
    public Character(int str, int dex, int agi, int wis, int intel,int level,int exp,int currentHp,int maxHp, Texture texture) {

        //charEquip=new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            charEquip.add(null);
//        }
        this.setStatusEffects(new ArrayList<Consumable>());
        this.setCharEquip(new ArrayList<Equipment>());
        this.setStrength(str);
        this.setDexterity(dex);
        this.setAgility(agi);
        this.setWisdom(wis);
        this.setIntelligence(intel);
        this.setMaxHealth(maxHp);
        this.setCurrentHealth(currentHp);
        int equipDefense=0;
//        for (Equipment e:charEquip) {
//            if(e!=null) {
//                equipDefense += e.getStats().get(0);
//            }
//        }
        this.setDefense(equipDefense + this.getDexterity() * 2 + this.getLevel());
        this.setSprite(new Sprite(texture));
        this.level = level;
        this.setExp(exp);
        this.setActive(true);
        this.charClass= new CharClass("fighter");
        this.charClass.getActions().add("attack");
        this.charClass.getActions().add("items");
        this.charClass.getActions().add("run");
    }

    public void updateStats(){

        //this.setCurrentHealth(this.getMaxHealth());
        int equipDefense=0;
        int equipHealth=0;
        for (Equipment e:charEquip) {
            if(e.getStats().size()!=0) {
                equipDefense += e.getStats().get(0);
                if(e.getStats().size()>2) {
                	equipHealth+=e.getStats().get(2);
                }
            }
        }
        this.setDefense(equipDefense + this.getDexterity() * 2 + this.getLevel());
        this.setMaxHealth((level*2)+getAgility()*2+10 + equipHealth);
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