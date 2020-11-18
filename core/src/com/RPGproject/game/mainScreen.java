package com.RPGproject.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class mainScreen extends ScreenAdapter {
    Main game;
    String hello;

    public mainScreen(Main game){
        this.game = game;
    }
    public void show() {
    hello = "hi";
    }

    public void render(){

    }
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}
