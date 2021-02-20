package com.RPGproject.game;

import java.util.ArrayList;

public class CharClass {
    private String className;
    private String primaryStat;
    private String secondaryStat;
    private ArrayList<String> actions;

    public CharClass(String className, String primaryStat, String secondaryStat,ArrayList<String> actions) {
        this.className=className;
        this.primaryStat = primaryStat;
        this.secondaryStat = secondaryStat;
        this.actions = actions;
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

    public void levelUp(Character character){
        if(character.getExp()==20+Math.pow(2,character.getLevel()-1)){
            character.setLevel(character.getLevel()+1);
            String stat = primaryStat;
            for (int i = 0; i < 2; i++) {
                if(stat.equals("strength")){
                    character.setStrength(character.getStrength()+2-i);
                }
                else if(stat.equals("dexterity")){
                    character.setDexterity(character.getDexterity()+2-i);
                }
                else if(stat.equals("agility")){
                    character.setAgility(character.getAgility()+2-i);
                }
                else if(stat.equals("wisdom")){
                    character.setWisdom(character.getWisdom()+2-i);
                }
                else if(stat.equals("intelligence")){
                    character.setIntelligence(character.getIntelligence()+2-i);
                }
                stat = secondaryStat;
            }
            character.updateStats();
        }
        //character
        //this.se
        //this.secondaryStat++;
    }
}
