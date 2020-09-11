package com.MapEditor.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.RPGproject.game.UserInterface;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Main extends ApplicationAdapter {
    UserInterface ui;
    int yOffset;
    boolean mouseDown;
    String fileName;
    textProcessor tp;
    Texture texture;
    SpriteBatch batch;
    public void create(){
        batch = new SpriteBatch();
        tp = new textProcessor();
        ui = new UserInterface();
        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {

                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE) && ui.getInputText().getTextContent().get(0).length()>0){
                    ui.getInputText().removeChar();

                }
                else if (Gdx.input.isKeyPressed(Input.Keys.ENTER)){
                    String inputString = ui.getMainText().input(ui.getInputText());
                    tp.processing(inputString);
                }
                else if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
                    Gdx.app.exit();
                }
                else {
                    ui.getInputText().addChar(character,ui.getFont());
                }
                return true;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                screenY=ui.getScreenHeight()-screenY;
                if(screenX>ui.getScrollbar().getX() && screenX<ui.getScrollbar().getX()+ui.getScrollbar().getWidth()) {
                    yOffset = screenY-ui.getScrollbar().getY();
                    if(yOffset>=0 && yOffset<=ui.getScrollbar().getHeight()){
                        ui.getScrollbar().moveScroll(screenX, screenY-yOffset);
                        mouseDown = true;
                    }
                    else{
                        ui.getScrollbar().moveScroll(screenX, screenY+yOffset);
                    }
                }
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                mouseDown=false;
                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                screenY=ui.getScreenHeight()-screenY;
                if(mouseDown) {
                    ui.getScrollbar().moveScroll(screenX, screenY-yOffset);
                }
                return true;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                ui.getScrollbar().Scrolling(amount,ui.getMainText().getTextContent().size());
                return true;
            }
        });
    }

    public void render(){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ui.render();
        int column = 1;
        int row =30;

        for (ArrayList<String> a:tp.getMapContent()) {
            for (String s:a) {
                texture = new Texture(s+".png");
                batch.begin();
                batch.draw(texture,column*32,row*32);
                batch.end();
                column++;
            }
            row--;
            column=1;
        }
    }

    public void dispose(){
    }
}
