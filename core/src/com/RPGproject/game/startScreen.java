package com.RPGproject.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class startScreen extends ScreenAdapter {

    Main game;
    ShapeRenderer sr;
    Party charParty;
    int flag;
    int rectWidth;
    int rectHeight;
    int hoverNum;
    boolean hover;
    private SpriteBatch batch;
    BitmapFont font;
    ArrayList<Vector2> menuSelecting;


    public startScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        hover=false;
        hoverNum=-1;
        flag=0;
        rectWidth=400;
        rectHeight=100;
        menuSelecting=new ArrayList<>();
        menuSelecting.add(new Vector2(Gdx.graphics.getWidth()/2 -200,400));
        font=new BitmapFont();
        font.getData().setScale(4);
        batch = new SpriteBatch();
    	sr = new ShapeRenderer();
    	charParty = new Party();
    	loadFile("notFile");
        Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean keyDown ( int keycode){
                if (keycode == Input.Keys.ENTER){
                    game.setScreen(new mainScreen(game,charParty));
                }
                return true;
            }

            public boolean keyUp ( int keycode){
                return false;
            }

            public boolean keyTyped ( char character){
                return false;
            }

            public boolean touchDown ( int x, int y, int pointer, int button){
                if(hover)
                    if(flag==0){
                        flag=1;
                        hover=false;
                        hoverNum=-1;
                        sr.setColor(Color.WHITE);
                        menuSelecting.removeAll(menuSelecting);
                        menuSelecting.add(new Vector2(Gdx.graphics.getWidth()/2 -200,800));
                        menuSelecting.add(new Vector2(Gdx.graphics.getWidth()/2 -200,600));
                        menuSelecting.add(new Vector2(Gdx.graphics.getWidth()/2 -200,400));
                    }
                    if(flag==1){
                        if(hoverNum==0){
                            //newGame();
                            flag=2;
                            hover=false;
                            menuSelecting.removeAll(menuSelecting);
                        }
                        else if(hoverNum==1){
                            //load game
                            flag=3;
                            menuSelecting.removeAll(menuSelecting);
                            rectWidth=1000;
                            rectHeight=200;
                            menuSelecting.add(new Vector2(Gdx.graphics.getWidth()/2 -500,700));
                            menuSelecting.add(new Vector2(Gdx.graphics.getWidth()/2 -500,400));
                            menuSelecting.add(new Vector2(Gdx.graphics.getWidth()/2 -500,100));
                            hover=false;
                        }
                        else if(hoverNum==2){
                            Gdx.app.exit();
                        }
                    }
                    else if(flag==3){
                            loadFile("text/save"+(hoverNum+1)+".txt");
                    }
                return false;
            }

            public boolean touchUp ( int x, int y, int pointer, int button){
                return false;
            }

            public boolean touchDragged ( int x, int y, int pointer){
                return false;
            }

            public boolean mouseMoved ( int x, int y) {
                y = Gdx.graphics.getHeight() - y;
                hoverNum=-1;
                for (Vector2 clickable:menuSelecting) {
                    if(x>=clickable.x && x<=clickable.x+rectWidth && y >= clickable.y && y <= clickable.y+rectHeight){
                        sr.setColor(Color.GRAY);
                        hover=true;
                        hoverNum=menuSelecting.indexOf(clickable);
                    }
                    else{
                        sr.setColor(Color.WHITE);
                        hover=false;
                    }
                }
                return false;
            }
        });
    }

    public void loadFile(String textFile) {
        try {
            ArrayList<Integer> statList = new ArrayList<>();
            Character character;
            Texture texture = new Texture("smile.png");
            File file = new File(textFile);
            System.out.println(file.length());
            Scanner scanner = new Scanner(file);
            System.out.println(textFile);
            int fileFlag=0;
            while(scanner.hasNextLine()){
                String text = scanner.nextLine();
                if(fileFlag==1 || fileFlag==3){
                    if(text.substring(0,text.indexOf(":")).equals("Name")){
                        fileFlag++;
                        character = new Character(statList.get(2),statList.get(3),statList.get(4),statList.get(5),statList.get(6),statList.get(0),statList.get(1),statList.get(7),statList.get(8),texture);
                        character.setName(text);
                        if(charParty.getChar1()==null){
                            charParty.setChar1(character);
                        }
                        else{
                            charParty.setChar2(character);
                        }
                        statList.removeAll(statList);
                    }
                    else {
                        statList.add(Integer.parseInt(text.substring(text.indexOf(":") + 1)));
                        System.out.println(statList);
                    }
                }
                if(fileFlag==6){
                    charParty.getItems().add(text);
                }
                if(text.indexOf("/")==0){
                    fileFlag++;
                }
            }
            game.setScreen(new mainScreen(game,charParty));

            //charParty.setChar1(new Character(1, 1, 5, 1, 1, texture));
            //charParty.setChar2(new Character(2, 2, 3, 2, 2, texture));
            //charParty.getItems().add("3x Potion");
            //charParty.getItems().add("3x Big Potion");
            //charParty.getItems().add("3x Trash aka Locky");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        for (Vector2 rect:menuSelecting) {
            if(hoverNum != -1 && hoverNum == menuSelecting.indexOf(rect)){
                sr.setColor(Color.GRAY);
            }
            else{
                sr.setColor(Color.WHITE);
            }
            sr.rect(rect.x,rect.y,rectWidth,rectHeight);
        }
        sr.end();
        if(flag==0){
            batch.begin();
            font.setColor(Color.WHITE);
            font.draw(batch,"Controls:",200,900);
            font.draw(batch,"- W A S D  for moving around the world and selecting in menus",200,820);
            font.draw(batch,"- Esc  for exiting the game",200,740);
            font.draw(batch,"- Mouse when creating characters/levelling up and scrollbars",200,660);
            font.setColor(Color.BLACK);
            font.draw(batch,"Menu",Gdx.graphics.getWidth()/2 -80,480);
            batch.end();
        }
        else if(flag==1){

        }
        else if(flag==2){

        }
        else if(flag==3){

        }
    }

    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}
