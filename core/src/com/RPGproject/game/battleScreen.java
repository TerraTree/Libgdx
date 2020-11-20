package com.RPGproject.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;

public class battleScreen extends ScreenAdapter {

    public battleScreen(Main game){

    }

    public void show(){
    }

    public void render(){

    }
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}
