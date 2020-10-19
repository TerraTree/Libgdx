package com.MapEditor.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.RPGproject.game.UserInterface;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Main extends ApplicationAdapter {
    UserInterface ui;
    int yOffset;
    boolean mouseDown;
    boolean typing;
    String fileName;
    textProcessor tp;
    Texture texture;
    SpriteBatch batch;
    String currentTile;
    Camera camera;
    Map currentMap;
    public void create(){
    	currentTile="grass";
        batch = new SpriteBatch();
        tp = new textProcessor();
        currentMap = new Map(tp.fileName);
        ui = new UserInterface();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        tp.tileName="grass";
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
                if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
                    Gdx.app.exit();
                }
            	if (typing) {
            		if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE) && ui.getInputText().getTextContent().get(0).length()>0){
            			ui.getInputText().removeChar();

            		}
            		else if (Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            			String inputString = ui.getMainText().input(ui.getInputText());
            			tp.processing(inputString);
            			if(tp.fileName.length()!=0) {
            			    System.out.println(tp.fileName);
            			    tp.fileChoice(tp.fileName);
            			    tp.fileName="";
            			    tp.fileLoad=false;
                        }
            		}
            		else {
            			ui.getInputText().addChar(character,ui.getFont());
            		}
            	}
            	else {
            		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            			currentMap.setOriginY(currentMap.getOriginY()-5);
            		}
            		else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            			currentMap.setOriginX(currentMap.getOriginX()+5);
            		}
            		else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            			currentMap.setOriginY(currentMap.getOriginY()+5);
            		}
            		else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            			currentMap.setOriginX(currentMap.getOriginX()-5);
            		}
            	}


                return true;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                screenY=ui.getScreenHeight()-screenY;
                if(screenY>ui.getScrollbar().getMaxHeight()) {
                	if(typing) {
                		typing = false;
                	}
                	else {
                		currentMap.tileAdd(screenX,screenY,tp.tileName);
                	}
                }
                else if(screenY<ui.getScrollbar().getMaxHeight() && (!typing)) {
                		typing = true;
               
                }
                else if(screenX>ui.getScrollbar().getX() && screenX<ui.getScrollbar().getX()+ui.getScrollbar().getWidth() && (typing)) {
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

        int column = 0;
        int row =0;
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        camera.update();
        //System.out.println(currentMap.getMapContent());
        for (ArrayList<String> a:currentMap.getMapContent()) {
            for (String s:a) {
            	if(s=="") {
            	}
            	else {
                texture = new Texture(s+".png");
                batch.draw(texture,(column*32)+currentMap.getOriginX(),-currentMap.getOriginY()-(row*32)-32);
                //System.out.println(column*32 +currentMap.getOriginX());
            	}
                column++;
            }
            row++;
            column=0;
        }
        //camera.update();
        batch.end();
        ui.render();
    }

    public void dispose(){
    }
}
