package com.RPGproject.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

public class alternateUI extends ScreenAdapter {
    Main game;
    Party charParty;
    int uiType;
    ArrayList<String> textContent;
    ArrayList<String> buttons;
    ShapeRenderer sr;
    SpriteBatch batch;
    BitmapFont font;
    Character currentChar;

    public alternateUI(Main game, Party mainParty,int uiType,ArrayList<String> textList){
        this.game = game;
        this.charParty=mainParty;
        this.uiType=uiType;
        this.textContent = textList;
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
            for (int i = 0; i < textContent.size()-1; i+=2) {
                font.draw(batch,textContent.get(i),50,Gdx.graphics.getHeight()-200-50*i);
                font.draw(batch,textContent.get(i),Gdx.graphics.getWidth()/2 + 50,Gdx.graphics.getHeight()-200-50*i);
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
