package com.RPGproject.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class startScreen extends ScreenAdapter {

    Main game;
    ShapeRenderer sr;
    Party charParty;

    public startScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
    	sr = new ShapeRenderer();
    	charParty = new Party();
    	loadFile();
        Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean keyDown ( int keycode){
                if (keycode == Input.Keys.ENTER){
                    game.setScreen(new mainScreen(game,charParty));
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
        //temp setup to make game work.
    	Texture texture = new Texture("smile.png");
    	charParty.setChar1(new Character(1,1,5,1,1,texture));
        charParty.setChar2(new Character(2,2,3,2,2,texture));
        charParty.getItems().add("3x Potion");
        charParty.getItems().add("3x Big Potion");
        charParty.getItems().add("3x Trash aka Locky");
    }
    
    public void render(float delta){
    }

    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}
