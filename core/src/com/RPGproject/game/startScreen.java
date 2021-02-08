package com.RPGproject.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
    int totalStatPoints;
    boolean hover;
    private SpriteBatch batch;
    Character currentChar;
    BitmapFont font;
    ArrayList<Vector2> menuSelecting;
    ArrayList<Sprite> statArray;

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
        statArray=new ArrayList<>();
        menuSelecting.add(new Vector2(Gdx.graphics.getWidth()/2 -200,400));
        font=new BitmapFont();
        font.getData().setScale(4);
        batch = new SpriteBatch();
    	sr = new ShapeRenderer();
    	charParty = new Party();
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
                if(hoverNum==-2){
                    if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE) && currentChar.getName().length()!=0){
                        currentChar.setName(currentChar.getName().substring(0,currentChar.getName().length()-1));
                    }
                    else if(currentChar.getName().length()<=8){
                        currentChar.setName(currentChar.getName()+character);
                    }
                }
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
                            flag=2;
                            totalStatPoints=20;
                            hover=false;
                            hoverNum=-1;
                            menuSelecting.removeAll(menuSelecting);
                            rectWidth=500;
                            rectHeight=100;
                            charParty.setChar1(new Character(1,1,1,1,1,1,new Texture("smile.png")));
                            currentChar=charParty.getChar1();
                            currentChar.setName("");
                            menuSelecting.add(new Vector2(Gdx.graphics.getWidth()/2 -700,800));
                            for(int i=0;i<5;i++){
                                menuSelecting.add(new Vector2(Gdx.graphics.getWidth()/2 -700,650-i*150));
                                Sprite sprite = new Sprite(new Texture("plus.png"));
                                sprite.setX(600);
                                sprite.setY(50+i*150);
                                System.out.println(sprite.getY());
                                statArray.add(sprite);
                                sprite = new Sprite(new Texture("minus.png"));
                                sprite.setY(50+i*150);
                                sprite.setTexture(new Texture("minus.png"));
                                sprite.setX(700);
                                statArray.add(sprite);
                            }
                        }
                        else if(hoverNum==1){
                            //load game
                            flag=4;
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
                    else if(flag==2 || flag==3){
                        int index = 0;
                        int trueIndex;
                        int increment;
                        System.out.println(y);
                        y=Gdx.graphics.getHeight()-y;
                            for (Sprite s : statArray) {
                                if (x >= s.getX() && x <= s.getX() + s.getWidth() && y >= s.getY() && y <= s.getY() + s.getHeight()) {
                                    trueIndex = index / 2;
                                    System.out.println(trueIndex);
                                    if (index % 2 == 0 && totalStatPoints>0) {
                                        increment = 1;
                                    } else if(index % 2 ==1){
                                        increment = -1;
                                    }
                                    else{
                                        increment=0;
                                    }
                                    if (trueIndex == 0) {
                                        currentChar.setIntelligence(Math.max(1, currentChar.getIntelligence() + increment));
                                    } else if (trueIndex == 1) {
                                        currentChar.setWisdom(Math.max(1, currentChar.getWisdom() + increment));
                                    } else if (trueIndex == 2) {
                                        currentChar.setAgility(Math.max(1, currentChar.getAgility() + increment));
                                    } else if (trueIndex == 3) {
                                        currentChar.setDexterity(Math.max(1, currentChar.getDexterity() + increment));
                                    } else if (trueIndex == 4) {
                                        currentChar.setStrength(Math.max(1, currentChar.getStrength() + increment));
                                    }
                                    totalStatPoints = Math.min(20,totalStatPoints-increment);
                                }
                                index++;
                        }
                            Vector2 veccer = menuSelecting.get(0);
                            if(x>=veccer.x && x<=veccer.x + rectWidth && y>=veccer.y && y<=veccer.y+rectHeight){
                                hoverNum=-2;
                            }
                            else if(x>=Gdx.graphics.getWidth()-400 && x<=Gdx.graphics.getWidth()-100 && y>=100 && y<=200){
                                currentChar.updateStats();
                                if(flag==2){
                                    flag++;
                                    charParty.setChar2(new Character(1, 1, 1, 1, 1, 1, new Texture("smile.png")));
                                    currentChar=charParty.getChar2();
                                    currentChar.setName("");
                                    totalStatPoints=20;
                                }
                                else{
                                    game.setScreen(new mainScreen(game, charParty));
                                }
                            }
                            else{
                                hoverNum=-1;
                            }
                    }
                    else if(flag==4){
                        System.out.println("text/save"+(hoverNum+1)+".txt");
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
                if(flag!=2 && flag!=3) {
                    y = Gdx.graphics.getHeight() - y;
                    hoverNum = -1;
                    for (Vector2 clickable : menuSelecting) {
                        if (x >= clickable.x && x <= clickable.x + rectWidth && y >= clickable.y && y <= clickable.y + rectHeight) {
                            sr.setColor(Color.GRAY);
                            hover = true;
                            hoverNum = menuSelecting.indexOf(clickable);
                        } else {
                            sr.setColor(Color.WHITE);
                            hover = false;
                        }
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
            if(file.length()!=0) {
                System.out.println(file.length());
                Scanner scanner = new Scanner(file);
                System.out.println(textFile);
                int fileFlag = 0;
                while (scanner.hasNextLine()) {
                    String text = scanner.nextLine();
                    if (fileFlag == 1 || fileFlag == 3) {
                        if (text.substring(0, text.indexOf(":")).equals("Name")) {
                            fileFlag++;
                            character = new Character(statList.get(2), statList.get(3), statList.get(4), statList.get(5), statList.get(6), statList.get(0), statList.get(1), statList.get(7), statList.get(8), texture);
                            character.setName(text.substring(text.indexOf(":") + 1));
                            if (charParty.getChar1() == null) {
                                charParty.setChar1(character);
                            } else {
                                charParty.setChar2(character);
                            }
                            statList.removeAll(statList);
                        } else {
                            statList.add(Integer.parseInt(text.substring(text.indexOf(":") + 1)));
                            System.out.println(statList);
                        }
                    }
                    if (fileFlag == 6) {
                        charParty.getItems().add(text);
                    }
                    if (text.indexOf("/") == 0) {
                        fileFlag++;
                    }
                }
                game.setScreen(new mainScreen(game, charParty));
            }
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
            batch.begin();
            font.setColor(Color.BLACK);
            font.draw(batch,"New Game",Gdx.graphics.getWidth()/2 -150,870);
            font.draw(batch,"Load Game",Gdx.graphics.getWidth()/2 -150,670);
            font.draw(batch,"Exit",Gdx.graphics.getWidth()/2 -60,470);
            batch.end();
        }
        else if(flag==2 || flag==3){
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.rect(Gdx.graphics.getWidth()-700,750,600,100);
            sr.rect(Gdx.graphics.getWidth()-400,100,300,100);
            sr.end();
            batch.begin();
            font.setColor(Color.BLACK);
            font.draw(batch,"Name: "+currentChar.getName(),Gdx.graphics.getWidth()/2 -700,850);

            font.draw(batch,"Strength: "+currentChar.getStrength(),Gdx.graphics.getWidth()/2 -700,700);
            font.draw(batch,"Dexterity: "+currentChar.getDexterity(),Gdx.graphics.getWidth()/2 -700,550);
            font.draw(batch,"Agility: "+currentChar.getAgility(),Gdx.graphics.getWidth()/2 -700,400);
            font.draw(batch,"Wisdom: "+currentChar.getWisdom(),Gdx.graphics.getWidth()/2 -700,250);
            font.draw(batch,"Intelligence: "+currentChar.getIntelligence(),Gdx.graphics.getWidth()/2 -700,100);
            font.draw(batch,"Points Left: "+ totalStatPoints,Gdx.graphics.getWidth()-700,800);
            for (Sprite s:statArray) {
                s.draw(batch);
            }
            batch.end();
        }
        else if(flag==4){

        }
    }

    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}
