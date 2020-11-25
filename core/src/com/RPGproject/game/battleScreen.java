package com.RPGproject.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;

public class battleScreen extends ScreenAdapter {
	Main game;
	UserInterface ui;

    public battleScreen(Main game){
    	this.game = game;
    }

    public void show(){
    	ui = new UserInterface();
    }

    public void render(){
    	ui.render();
    }
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}
