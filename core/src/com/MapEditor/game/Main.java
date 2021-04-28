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
    //Texture texture;
    SpriteBatch batch;
    String currentTile;
    Camera camera;
    Map currentMap;
    int menuTier;
    boolean fileRead;

    public void create(){
    	currentTile="grass";
        batch = new SpriteBatch();
        tp = new textProcessor();
        currentMap = new Map(tp.getFileName());
        ui = new UserInterface();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        tp.setTileName("grass");
        menuTier = 1;
        tp.menuDialogue(menuTier,ui);
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
            			fileRead=false;
            			String inputString = ui.getMainText().input(ui.getInputText());
                        ArrayList<String> menuContent = ui.getMenuText().getTextContent();
                        if(tp.isFileLoad()==true) {
            				currentMap=tp.fileChoice(inputString,currentMap);
            				//tp.setFileName();
            				tp.setFileLoad(false);
            				fileRead=true;
            			}
            			else if (tp.isFileSaving()==true) {
            			    if(!inputString.equals("save")){
            			        currentMap.setFileName(inputString+".txt");
            			        System.out.println("fileName:" +currentMap.getFileName());
            			        tp.setFileSaving(false);
            			        fileRead=true;
                            }
            				tp.fileSave(currentMap,ui);
            			}
                        if(fileRead==false) {
                        	menuTier = tp.processing(inputString,menuTier,menuContent);
            				tp.menuDialogue(menuTier,ui);
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
                		currentMap.tileAdd(screenX,screenY,tp.getTileName());
                	}
                }
                else if(screenY<ui.getScrollbar().getMaxHeight() && (!typing)) {
                		typing = true;
               
                }
                else if(screenX>ui.getScrollbar().getX() && screenX<ui.getScrollbar().getX()+ui.getScrollbar().getWidth() && (typing)) {
                    ui.getScrollbar().setYOffset(screenY-ui.getScrollbar().getY());
                    if(ui.getScrollbar().getYOffset()>=0 && yOffset<=ui.getScrollbar().getHeight()){
                        ui.getScrollbar().moveScroll(screenX, screenY-ui.getScrollbar().getYOffset());
                        mouseDown = true;
                    }
                    else{
                        ui.getScrollbar().moveScroll(screenX, screenY+ui.getScrollbar().getYOffset());
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
                    ui.getScrollbar().moveScroll(screenX, screenY-ui.getScrollbar().getYOffset());
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
        currentMap.render(batch);
        ui.render();
    }

    public void dispose(){
    }
}
