package com.RPGproject.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class CharClass {
    private String className;
    private String primaryStat;
    private String secondaryStat;
    private ArrayList<String> actions;
    private ArrayList<Integer> levelUpChange;

    public CharClass(String className) {
        this.className=className;
        if(this.className.equals("fighter")){
            this.primaryStat="strength";
            this.secondaryStat="dexterity";
            this.actions = new ArrayList<>();
            this.actions.add("attack");
            this.actions.add("items");
            this.actions.add("run");
        }
        this.levelUpChange = new ArrayList<>();
    }

    public ArrayList<Integer> getLevelUpChange() {
        return levelUpChange;
    }

    public void setLevelUpChange(ArrayList<Integer> levelUpChange) {
        this.levelUpChange = levelUpChange;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPrimaryStat() {
        return primaryStat;
    }

    public void setPrimaryStat(String primaryStat) {
        this.primaryStat = primaryStat;
    }

    public String getSecondaryStat() {
        return secondaryStat;
    }

    public void setSecondaryStat(String secondaryStat) {
        this.secondaryStat = secondaryStat;
    }

    public ArrayList<String> getActions() {
        return actions;
    }

    public void setActions(ArrayList<String> actions) {
        this.actions = actions;
    }

    public boolean levelUp(Character character){
        boolean levelUp = false;
        if(character.getExp()>20+Math.pow(2,character.getLevel()-1)){
            levelUp=true;
            levelUpChange=new ArrayList<>(7);
            for (int i = 0; i < 7; i++) {
                levelUpChange.add(0);
            }
            System.out.println("size "+levelUpChange.size());
            character.setLevel(character.getLevel()+1);
            String stat = primaryStat;
            for (int i = 0; i < 3; i++) {
                if(i==1){
                    stat = secondaryStat;
                    System.out.println("stat: "+stat);
                }
                else if(i==2){
                    String oldData[] = {"strength","dexterity","agility","wisdom","intelligence"};
                    ArrayList<String> data = new ArrayList<>();
                    data.addAll(Arrays.asList(oldData));
                    data.remove(data.indexOf(primaryStat));
                    data.remove(data.indexOf(secondaryStat));

                    stat = data.get(ThreadLocalRandom.current().nextInt(3));
                    System.out.println(stat);
                    System.out.println(data);
                }

                int j = Math.max(1,2-i); //change of the stat, minimum 1 for secondary and random stat
                if(stat.equals("strength")){
                    character.setStrength(character.getStrength()+j);
                    levelUpChange.set(0,j);
                }
                else if(stat.equals("dexterity")){
                    character.setDexterity(character.getDexterity()+j);
                    levelUpChange.set(1,j);
                }
                else if(stat.equals("agility")){
                    character.setAgility(character.getAgility()+j);
                    levelUpChange.set(2,j);
                }
                else if(stat.equals("wisdom")){
                    character.setWisdom(character.getWisdom()+j);
                    levelUpChange.set(3,j);
                }
                else if(stat.equals("intelligence")){
                    character.setIntelligence(character.getIntelligence()+j);
                    levelUpChange.set(4,j);
                }


            }
            int oldHp = character.getMaxHealth();
            int oldDefense = character.getDefense();
            character.updateStats();
            levelUpChange.set(5,character.getMaxHealth()-oldHp);
            System.out.println("HEalth increase: "+levelUpChange.get(5));
            levelUpChange.set(6,character.getDefense()-oldDefense);
            character.setCurrentHealth(character.getCurrentHealth()+levelUpChange.get(5));
            if(character.getCurrentHealth()>character.getMaxHealth()) {
            	character.setCurrentHealth(character.getMaxHealth());
            }
        }
        return levelUp;
    }
}
