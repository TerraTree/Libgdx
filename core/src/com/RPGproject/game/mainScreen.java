package com.RPGproject.game;

import com.MapEditor.game.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class mainScreen extends ScreenAdapter {
    Main game;
    UserInterface ui;
    Party mainParty;
    ArrayList<enemy> enemyParty;
    Map map;
    SpriteBatch batch;
    textProcessor tp;
    boolean typing;

    public mainScreen(Main game,Party mainParty){
        this.game = game;
        this.mainParty = mainParty;
    }

    public enemy loadEnemy(String textFile){
        enemy newEnemy = new enemy(1,1,1,1,1,1,1,1,1);
        try {
            String name;
            Texture texture;
            ArrayList<Integer> statList = new ArrayList<>();
            File file = new File(textFile);
            Scanner scanner = new Scanner(file);
            int fileFlag = 0;
            while (scanner.hasNextLine()) {
                String text = scanner.nextLine();
                if (fileFlag == 1) {
                    System.out.println("fileFLAG");
                    if (text.equals("/Enemy Info")) {
                        newEnemy = new enemy(statList.get(0), statList.get(1), statList.get(2), statList.get(3), statList.get(4), statList.get(5), statList.get(6), statList.get(7), statList.get(8));
                        statList.removeAll(statList);
                    } else {
                        statList.add(Integer.parseInt(text.substring(text.indexOf(":") + 1)));
                    }
                }
                else if (fileFlag == 2) {
                    newEnemy.setName(text.substring(text.indexOf(":")+1));
                    fileFlag++;
                }
                else if(fileFlag == 3){
                    try {
                        //texture= new Texture("slime.png");
                        texture = new Texture(text.substring(text.indexOf(":")+1));
                    }
                    catch(Exception f){
                        //System.out.println(f);
                        texture = new Texture("evil.png");
                    }
                    newEnemy.setSprite(new Sprite(texture));
                    fileFlag++;
                }
                if (text.indexOf("/") == 0) {
                    fileFlag++;
                }
            }
        }
            catch(Exception e){
            System.out.println(e);
            newEnemy.setName("unknown");
            newEnemy.setSprite(new Sprite(new Texture("evil.png")));
        }
        return newEnemy;
    }

    public void updatePlayerPos(Vector2 movement){
        mainParty.setxCoord((int) (mainParty.getxCoord()-movement.x));
        mainParty.setyCoord((int) (mainParty.getyCoord()+movement.y));
        map.setOriginY((int) (map.getOriginY()+movement.y));
        map.setOriginX((int) (map.getOriginX()+movement.x));
        int x = mainParty.getxCoord()/32;
        int y = mainParty.getyCoord()/32;
        if(x < map.getMapContent().get(0).size() && x>=0 && y < map.getMapContent().size() && y>=0){
            System.out.println(map.getMapContent().get(y).get(x));
            if(map.getMapContent().get(y).get(x).equals("grass")){
                double encounter = Math.random();
                if(encounter<= 0.1){
                    enemy newEnemy = loadEnemy("enemies/slime.txt");
                    enemyParty.add(newEnemy);
                    enemy enemy2 = loadEnemy("enemies/slime.txt");
                    enemyParty.add(enemy2);
                    game.setScreen(new battleScreen(game,mainParty,enemyParty));
                }
            }
            else if(map.getMapContent().get(y).get(x).equals("")){

            }
        }
    }
    @Override
    public void show() {
        tp = new textProcessor();
        batch=new SpriteBatch();
    	ui = new UserInterface();
    	typing = false;
    	map=tp.fileChoice(mainParty.getMapName(),map);
    	enemyParty=new ArrayList<>();
        mainParty.getSprite().setScale(0.3f);
    	mainParty.getSprite().setX(Gdx.graphics.getWidth()/2 - mainParty.getSprite().getWidth()/2);
        mainParty.getSprite().setY(Gdx.graphics.getHeight()/2 + mainParty.getSprite().getHeight()/2);
    	//map=new Map(mainParty.getMapName());
        map.setOriginX(Gdx.graphics.getWidth()/2 - mainParty.getxCoord());
        map.setOriginY(Gdx.graphics.getHeight()/2 + mainParty.getyCoord());
    	Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean keyDown ( int keycode){
                if(keycode==Input.Keys.ESCAPE) {
                    Gdx.app.exit();
                }
                return true;
            }
            public boolean keyTyped (char character){
                if(typing==false) {
                    if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                        updatePlayerPos(new Vector2(0, -5));
                    } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                        updatePlayerPos(new Vector2(5, 0));
                    } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                        updatePlayerPos(new Vector2(0, 5));
                    } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                        updatePlayerPos(new Vector2(-5, 0));
                    }
                    else if(Gdx.input.isKeyPressed(Input.Keys.T)){
                        typing=true;
                    }
                    System.out.println(mainParty.getyCoord());
                }
                else if(typing){
                    if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE) && ui.getInputText().getTextContent().get(0).length()>0){
                        ui.getInputText().removeChar();
                    }
                    else if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
                        String inputString = ui.getMainText().input(ui.getInputText());
                        if(inputString.equals("shop")){//temp location for shop, will not be accessible by command
                            int shopID=1;

                            game.setScreen(new alternateUI(game,mainParty,1,shopID,"shopContents"));
                        }
                        else if(inputString.equals("characters")){//view character stats
                            game.setScreen(new alternateUI(game,mainParty,2));
                        }
                        else if(inputString.equals("equipment")){//change equipment on characters
                            game.setScreen(new alternateUI(game,mainParty,3));
                        }
                    }
                    else {
                        ui.getInputText().addChar(character,ui.getFont());
                    }
                }
                return true;
            }
    	});
    }

    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    	ui.render();
        map.render(batch);
        batch.begin();
        mainParty.getSprite().draw(batch);
        batch.end();
    }
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}
