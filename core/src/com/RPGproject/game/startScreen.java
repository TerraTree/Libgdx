package com.RPGproject.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class startScreen extends ScreenAdapter {

    Main game;
    ShapeRenderer sr;
    Character character;

    public startScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
    	sr = new ShapeRenderer();
    	loadFile();
        Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean keyDown ( int keycode){
                if (keycode == Input.Keys.ENTER){
                    game.setScreen(new mainScreen(game));
                    System.out.println("hi");
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
                return false;
            }

            public boolean touchUp ( int x, int y, int pointer, int button){
                return false;
            }

            public boolean touchDragged ( int x, int y, int pointer){
                return false;
            }

            public boolean mouseMoved ( int x, int y){
                return false;
            }

        });
    }
    
    public void loadFile() {
    	
    }
    
    public void render(float delta){

    }

    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}
