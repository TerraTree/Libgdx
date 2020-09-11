package com.RPGproject.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.ObjectMap;

public class Main extends ApplicationAdapter implements stageInterface {
    UserInterface mainUI;
    Actor actor;
    startStage startStage;
    Texture image;
    mainStage mainStage;
    ObjectMap<Textures, Texture> textureMap;

    public void create(){
        textureMap = Textures.loadAllTextures();
        image = new Texture("character.png");
        startStage = new startStage(image,this);
        image = new Texture("char2.png");
        mainStage = new mainStage(image,this);
        mainUI = new UserInterface();
        actor=new Actor();
        Gdx.input.setInputProcessor(startStage);
    }

    public void resize (int width, int height) {
        // See below for what true means.
        startStage.getViewport().update(width, height, true);
    }

    public void render(){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mainUI.render();
        startStage.draw();
        mainStage.draw();
    }

    public void dispose(){

    }

    @Override
    public void startGame() {
        mainStage.setVisible(true);
        Gdx.input.setInputProcessor(mainStage);
    }

    @Override
    public void returnToStart() {
        startStage.setVisible(true);
        Gdx.input.setInputProcessor(startStage);
    }

    @Override
    public Texture getTexture(Textures texture) {
        return textureMap.get(texture);
    }
}
