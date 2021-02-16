package com.RPGproject.game;

import com.MapEditor.game.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class mainScreen extends ScreenAdapter {
    Main game;
    UserInterface ui;
    Party mainParty;
    Party enemyParty;
    Map map;
    SpriteBatch batch;
    textProcessor tp;

    public mainScreen(Main game,Party mainParty){
        this.game = game;
        this.mainParty = mainParty;
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
    	map=tp.fileChoice(mainParty.getMapName(),map);
        mainParty.getSprite().setScale(0.3f);
    	mainParty.getSprite().setX(Gdx.graphics.getWidth()/2 - mainParty.getSprite().getWidth()/2);
        mainParty.getSprite().setY(Gdx.graphics.getHeight()/2 + mainParty.getSprite().getHeight()/2);
    	//map=new Map(mainParty.getMapName());
        map.setOriginX(Gdx.graphics.getWidth()/2 - mainParty.getxCoord());
        map.setOriginY(Gdx.graphics.getHeight()/2 + mainParty.getyCoord());
        System.out.println(map.getOriginX());
        System.out.println(map.getOriginY());
    	Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean keyDown ( int keycode){
                if (keycode == Input.Keys.ENTER){
                    game.setScreen(new battleScreen(game,mainParty,enemyParty));
                }
                return true;
            }
            public boolean keyTyped (char character){
                if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                    updatePlayerPos(new Vector2(0,-5));
                }
                else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                    updatePlayerPos(new Vector2(5,0));
                }
                else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                    updatePlayerPos(new Vector2(0,5));
                }
                else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                    updatePlayerPos(new Vector2(-5,0));
                }
                System.out.println(mainParty.getyCoord());
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
