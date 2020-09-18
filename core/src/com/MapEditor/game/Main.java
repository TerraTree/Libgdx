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
import com.badlogic.gdx.math.Vector3;

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
    public void create(){
    	texture = new Texture("char2.png");
        batch = new SpriteBatch();
        tp = new textProcessor();
        ui = new UserInterface();
        camera = new OrthographicCamera(1920,1080);
        
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
            		}
            		else {
            			ui.getInputText().addChar(character,ui.getFont());
            		}
            	}
            	else {
                    System.out.println(Gdx.graphics.getDeltaTime());
            		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            			camera.translate(0, -5, 0);
            			System.out.println("camera moved");
            			camera.update();
            		}
            		else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            			camera.translate(5,0,0);
                        System.out.println("camera moved");
            			camera.update();
            		}
            		else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            			camera.translate(0,5,0);
                        System.out.println("camera moved");
            			camera.update();
            		}
            		else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            			camera.translate(-5,0,0);
                        System.out.println("camera moved");
            			camera.update();
            		}
            	}


                return true;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                System.out.println(ui.getScreenHeight()-screenY);
                System.out.println(ui.getScreenHeight());
                System.out.println(screenY);
                screenY=ui.getScreenHeight()-screenY;
                if(screenY>ui.getScrollbar().getMaxHeight()) {
                	if(typing) {
                		typing = false;
                	}
                	else {
                        Vector3 cameraCoord = camera.position;
                        int newX= (int) (screenX-cameraCoord.x);
                		//tp.tileAdd(currentTile,screenX,screenY);
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
        ui.render();
        int column = 1;
        int row =30;
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        camera.update();
        for (ArrayList<String> a:tp.getMapContent()) {
            for (String s:a) {
                texture = new Texture(s+".png");
                //System.out.println(texture.toString());
                batch.draw(texture,-200+(row*32),-200+(column*32));
                column++;
            }
            row--;
            column=1;
        }

        //camera.update();

        //batch.draw(texture,200,200);
        batch.end();
    }

    public void dispose(){
    }
}
