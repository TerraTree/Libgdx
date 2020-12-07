package com.RPGproject.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class mainScreen extends ScreenAdapter {
    Main game;
    UserInterface ui;
    Party mainParty;
    Party enemyParty;

    public mainScreen(Main game,Party mainParty){
        this.game = game;
        this.mainParty = mainParty;
    }
    @Override
    public void show() {
    	ui = new UserInterface();
    	Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean keyDown ( int keycode){
                if (keycode == Input.Keys.ENTER){
                    game.setScreen(new battleScreen(game,mainParty,enemyParty));
                    System.out.println("hi");
                }
                
                return true;
            }
    	});
    }

    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
    	ui.render();
    }
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}
