package com.RPGproject.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class startScreen extends ScreenAdapter {

    Main game;
    ShapeRenderer sr;
    Party charParty;
    int flag;
    boolean hover;
    private SpriteBatch batch;
    BitmapFont font;

    public startScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        hover=false;
        flag=0;
        font=new BitmapFont();
        font.getData().setScale(4);
        batch = new SpriteBatch();
    	sr = new ShapeRenderer();
    	charParty = new Party();
    	loadFile();
        Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean keyDown ( int keycode){
                if (keycode == Input.Keys.ENTER){
                    game.setScreen(new mainScreen(game,charParty));
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
                if(hover)
                    if(flag==0){
                        flag=1;
                    }
                return false;
            }

            public boolean touchUp ( int x, int y, int pointer, int button){
                return false;
            }

            public boolean touchDragged ( int x, int y, int pointer){
                return false;
            }

            public boolean mouseMoved ( int x, int y) {
                y = Gdx.graphics.getHeight() - y;
                if (flag == 0) {
                    if (x >= Gdx.graphics.getWidth() / 2 - 200 && x <= Gdx.graphics.getWidth() / 2 + 200 && y >= 400 && y <= 500) {
                        sr.setColor(Color.GRAY);
                        hover=true;
                    } else {
                        sr.setColor(Color.WHITE);
                        hover=false;
                    }
                }
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
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(flag==0){
            sr.begin(ShapeRenderer.ShapeType.Filled);
            //sr.setColor(Color.WHITE);
            sr.rect(Gdx.graphics.getWidth()/2 -200,400,400,100);
            sr.end();
            batch.begin();
            font.setColor(Color.WHITE);
            font.draw(batch,"Controls:",200,900);
            font.draw(batch,"- W A S D  for moving around the world and selecting in menus",200,820);
            font.draw(batch,"- Esc  for exiting the game",200,740);
            font.draw(batch,"- Mouse when creating characters/levelling up and scrollbars",200,660);
            font.setColor(Color.BLACK);
            font.draw(batch,"Menu",Gdx.graphics.getWidth()/2 -80,480);
            batch.end();
        }
        else if(flag==1){

        }
    }

    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}
