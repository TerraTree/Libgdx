package com.RPGproject.game;

import com.badlogic.gdx.*;


public class startScreen extends ScreenAdapter {

    Main game;

    public startScreen(Main game) {
        this.game = game;
    }

    public void show() {


        Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean keyDown ( int keycode){
                if (Gdx.input.isKeyPressed(Input.Keys.ENTER)){
                    game.setScreen(new mainScreen(game));
                }
                return false;
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

    public void render(){
    }

    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}
