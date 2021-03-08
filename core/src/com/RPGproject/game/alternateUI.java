package com.RPGproject.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class alternateUI extends ScreenAdapter {
    Main game;
    Party charParty;
    int uiType;
    ArrayList<Item> itemContent;
    ArrayList<Item> active;
    ArrayList<String> buttons;
    ShapeRenderer sr;
    SpriteBatch batch;
    BitmapFont font;
    Character currentChar;
    int buying;

    public alternateUI(Main game, Party mainParty,int uiType,int fileID,String fileContents){
        this.game = game;
        this.charParty=mainParty;
        this.uiType=uiType;
        if(uiType==1) {
            itemContent=new ArrayList<>();
            try {
                String item;
                boolean reading = false;
                File file = new File(fileContents + ".txt");
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    item = scanner.nextLine();
                    if(reading==true) {
                        String target="";
                        int duration=0;
                        String equipmentPosition="";
                        String itemName = item.substring(0, item.indexOf(":") - 1);
                        item = item.substring(item.indexOf(":") + 1);
                        int cost = Integer.parseInt(item.substring(0, item.indexOf(" ")));
                        item = scanner.nextLine();
                        String itemType = item.substring(item.indexOf("{"));
                        item = scanner.nextLine();
                        if (itemType == "consumable") {
                            duration = Integer.parseInt(item);
                            item=scanner.nextLine();
                            target = item;
                        } else if (itemType == "equipment") {
                            equipmentPosition = item;
                        }
                        item = scanner.nextLine();
                        String value = "";
                        ArrayList<Integer> stats = new ArrayList<>();
                        for (int i = 0; i < item.length()-2; i++) {
                            String chr = String.valueOf(item.charAt(i));
                            if (!chr.equals("[")) {
                                if (chr.equals(",")) {
                                    stats.add(Integer.parseInt(value));
                                    value = "";
                                }
                            } else {
                                value += chr;
                            }
                        }
                        if(itemType=="consumable"){
                            itemContent.add(new Consumable(itemName,stats,target,duration,cost));
                        }
                        else if(itemType=="equipment"){
                            itemContent.add(new Equipment(equipmentPosition,itemType,itemName,stats,cost));
                        }

                    }
                    if (reading == false) {
                        if (item.indexOf("id") == 0) {
                            if ((Integer.getInteger(item.substring(item.length() - 1))) == fileID) {
                                reading = true;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("file not read, loading temp shop instead");
                ArrayList<Integer> stat = new ArrayList<>();
                stat.add(0);
                stat.add(0);
                stat.add(20);
                itemContent.add(new Consumable("Potion",stat,"player",0,10));

            }
            buying=0;
        }
        //turn fileName into textContent
    }

    public alternateUI(Main game, Party mainParty,int uiType){
        this.game = game;
        this.charParty=mainParty;
        this.uiType=uiType;
    }

    public void show(){
        buttons = new ArrayList<>();
        sr = new ShapeRenderer();
        batch = new SpriteBatch();
        font=new BitmapFont();
        font.getData().setScale(4);
        font.setColor(Color.BLACK);
        if(uiType==1){ //shop ui
            buttons.add("Buy");
            buttons.add("Sell");
        }
        if(uiType==2){//viewCharacters
            buttons.add(charParty.getChar1().getName());
            buttons.add(charParty.getChar2().getName());
        }
        buttons.add("Exit");
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                int index=-1;
                screenY=Gdx.graphics.getHeight()-screenY;
                if(screenY>=Gdx.graphics.getHeight()-150 && screenY<=Gdx.graphics.getHeight()-50){
                    index=(screenX-10)/((Gdx.graphics.getWidth()-20)/3);
                    System.out.println(index);
                }
                if(uiType==1){
                    if(index == 0){
                        buying=1;
                    }
                    else if(index==1){
                        buying=2;
                    }
                    else if(index == 2){
                        game.setScreen(new mainScreen(game,charParty));
                    }
                    int x=0;
                    for (int i = 0; i < active.size()-1; i++) {
                        if(screenY>=Gdx.graphics.getHeight() - 300 - 50 * i && screenY<=Gdx.graphics.getHeight()-200-(50*i)){
                            if((screenX>=50 && screenX<=650 && i%2==0)||(screenX>=Gdx.graphics.getWidth()/2 +50 && screenX<=Gdx.graphics.getWidth()/2 +650)){
                                if(buying==1){
                                    charParty.getItems()
                                }
                            }
                            else if(screenX>=Gdx.graphics.getWidth()/2 +50 && screenX<=Gdx.graphics.getWidth()/2 +650){

                            }
                        }
                    }
                }
                if(uiType==2) {
                    if (index == 0) {
                        currentChar=charParty.getChar1();
                    }
                    else if(index == 1){
                        currentChar=charParty.getChar2();
                    }
                    else if(index == 2){
                        game.setScreen(new mainScreen(game,charParty));
                    }
                }
                return false;
            }
        });
    }

    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        int counter=0;
        font.setColor(Color.BLACK);
        font.getData().setScale(4);
        for (String button:buttons) {
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.rect(10+counter*(Gdx.graphics.getWidth()-20)/buttons.size(),Gdx.graphics.getHeight()-150,(Gdx.graphics.getWidth()-20)/buttons.size(),100);
            sr.end();
            batch.begin();
            font.draw(batch,button,10+counter*(Gdx.graphics.getWidth()-10)/buttons.size(),Gdx.graphics.getHeight()-100);
            batch.end();
            counter++;
        }

        if(uiType==1){

            // active=null;
            if(buying==1) {
                active = itemContent;
            }
            else if(buying==2){
                active = charParty.getItems();
            }
            if(active!=null) {
                System.out.println("running");
                for (int i = 0; i < Math.max(active.size() -1, 2); i += 2) {
                    if(active.size()!=0) {
                        sr.begin(ShapeRenderer.ShapeType.Filled);
                        sr.setColor(Color.WHITE);
                        sr.rect(50, Gdx.graphics.getHeight() - 300 - 50 * i, 600, 100);
                        sr.end();
                        batch.begin();
                        font.draw(batch, active.get(i).getName() + ": " + active.get(i).getCost() + "gp", 50, Gdx.graphics.getHeight() - 200 - 50 * i);
                        if (i + 1 <= active.size() - 1) {
                            font.draw(batch, active.get(i + 1).getName(), Gdx.graphics.getWidth() / 2 + 50, Gdx.graphics.getHeight() - 200 - 50 * i);
                        }
                        batch.end();
                    }
                }
            }
        }
        else if(uiType==2){
            if(currentChar!=null){
                font.getData().setScale(2);
                font.setColor(Color.WHITE);
                batch.begin();
                font.draw(batch,"Strength: "+currentChar.getStrength(),50,Gdx.graphics.getHeight()-200);
                font.draw(batch,"Dexterity: "+currentChar.getDexterity(),50,Gdx.graphics.getHeight()-250);
                font.draw(batch,"Agility: "+currentChar.getAgility(),50,Gdx.graphics.getHeight()-300);
                font.draw(batch,"Wisdom: "+currentChar.getWisdom(),50,Gdx.graphics.getHeight()-350);
                font.draw(batch,"Intelligence: "+currentChar.getIntelligence(),50,Gdx.graphics.getHeight()-400);
                font.draw(batch,"Health: "+ currentChar.getCurrentHealth() +"/"+currentChar.getMaxHealth(),Gdx.graphics.getWidth()/2 + 50,Gdx.graphics.getHeight()-200);
                font.draw(batch,"Defense: " + currentChar.getDefense(),Gdx.graphics.getWidth()/2 +50,Gdx.graphics.getHeight()-250);
                font.draw(batch,"Level: "+currentChar.getLevel(),Gdx.graphics.getWidth()/2 +50,Gdx.graphics.getHeight()-300);
                font.draw(batch,"Exp: "+(int) (currentChar.getExp()-20*(currentChar.getLevel()-1)-Math.pow(2,currentChar.getLevel()-2))+"/"+(int) (20+Math.pow(2,currentChar.getLevel()-1)),Gdx.graphics.getWidth()/2 +50,Gdx.graphics.getHeight()-350);
                batch.end();
//                for (int i = 0; i < ; i++) {
//
//                }
            }
        }
    }
}
