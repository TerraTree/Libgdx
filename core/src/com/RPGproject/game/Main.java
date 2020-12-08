package com.RPGproject.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.ObjectMap;

public class Main extends Game {


    @Override
    public void create(){
        setScreen(new startScreen(this));
    }
    public void dispose(){
    }

}
