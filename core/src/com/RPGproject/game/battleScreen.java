package com.RPGproject.game;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class battleScreen extends ScreenAdapter {
	Main game;
	UserInterface ui;
	Party mainParty;
	Party enemyParty;
	ArrayList<ArrayList<Character>> playerGrid;
	ArrayList<ArrayList<enemy>> enemyGrid;
	ShapeRenderer sr;
	Selector selector;
	int flag;
	int offsetX;
	int offsetY;
	Character selectChar;
	textProcessor tp;
	SpriteBatch batch;

    public battleScreen(Main game,Party mainParty,Party enemyParty){
    	this.game = game;
    	this.mainParty=mainParty;
    	this.enemyParty=enemyParty;
    }

    public void show(){
		offsetX = Gdx.graphics.getWidth()/6;
		offsetY = Gdx.graphics.getHeight()/2 + 150;
    	enemy enem1 = new enemy(1,1,1,1,1);
    	Texture texture = new Texture("evil.png");
    	enem1.setTexture(texture);
    	flag = 0;
    	ui = new UserInterface();
    	playerGrid = new ArrayList<ArrayList<Character>>(); //creates empty grid
    	for (int i=0;i<3;i++) {
    		playerGrid.add(new ArrayList<Character>());
    		playerGrid.get(i).add(null);
    		playerGrid.get(i).add(null);
    	}
    	selectChar=mainParty.getChar1();
    	enemyGrid = new ArrayList<ArrayList<enemy>>(); //creates an empty grid
    	for (int i=0;i<3;i++) {
    		enemyGrid.add(new ArrayList<enemy>());
    		enemyGrid.get(i).add(null);
    		enemyGrid.get(i).add(null);
    	}
    	enemyGrid.get(1).set(1, enem1);
    	sr = new ShapeRenderer();
    	selector = new Selector(offsetX,offsetY,100,100); //sets position and size of selector
    	tp = new textProcessor();
    	batch = new SpriteBatch();
    	
    	Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean keyDown ( int keycode){
				if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
					Gdx.app.exit();
				}
            	if(flag == 0 || flag == 1) {
            		if(keycode==Input.Keys.LEFT) {
            			selector.xOffset=0;
            		}
            		else if(keycode==Input.Keys.RIGHT) {
            			selector.xOffset=100;
            		}
            		else if(keycode==Input.Keys.UP && selector.yOffset<0) {
            			selector.yOffset+=100;
            		}
            		else if(keycode==Input.Keys.DOWN && selector.yOffset>-200) {
            			selector.yOffset-=100;
            		}
            		if(keycode==Input.Keys.ENTER) {
            			playerGrid.get(playerGrid.size()-1-selector.yOffset/-100).set(selector.xOffset/100, mainParty.getChar1()); //set player character to grid
            			flag++;
            			if(flag==1) {
            				selectChar = mainParty.getChar2();
            			}
            			else if (flag==2) {
            				selector.active=false;
            			}
            		}
            	}                
                return true;
            }
            public boolean keyTyped(char character) {
            	
            	if(flag==2){
					if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE) && ui.getInputText().getTextContent().get(0).length()>0){
						ui.getInputText().removeChar();
					}
					else if (Gdx.input.isKeyPressed(Input.Keys.ENTER)){
						System.out.println("output");
						String inputString = ui.getMainText().input(ui.getInputText());
						ArrayList<String> menuContent = ui.getMenuText().getTextContent();
						tp.processing(inputString,menuContent);
						tp.menuDialogue(ui);

						
						}
					else {
						ui.getInputText().addChar(character,ui.getFont());
					}
            	}
            	return true;
            	
            }
            
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                ui.getScrollbar().setScrolling(false);
                return true;
            }
            
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            	screenY=ui.getScreenHeight()-screenY;
                if(screenX>ui.getScrollbar().getX() && screenX<ui.getScrollbar().getX()+ui.getScrollbar().getWidth()) {
                    int yOffset = screenY-ui.getScrollbar().getY();
                    if(yOffset>=0 && yOffset<=ui.getScrollbar().getHeight()){
                        ui.getScrollbar().moveScroll(screenX, screenY-yOffset);
                        ui.getScrollbar().setScrolling(true);
                    }
                    else{
                        ui.getScrollbar().moveScroll(screenX, screenY+yOffset);
                    }
                }
                
                return true;
            }
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                screenY=ui.getScreenHeight()-screenY;
                if(ui.getScrollbar().isScrolling()) {
                    ui.getScrollbar().moveScroll(screenX, screenY-ui.getScrollbar().getYOffset());
                    
                }
                return true;
            }
    	});
    }

    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);    
    	ui.render();
    	int column = 0;
    	int row = 0;

    	for(ArrayList<Character> a:playerGrid) {
    		for(Character c:a) {
    			sr.begin(ShapeRenderer.ShapeType.Filled);
    			if(c == null) {
    				sr.setColor(Color.WHITE);
        			sr.rect(offsetX+ column*100,offsetY-row*100,100,100);
        			sr.end();
        			sr.begin(ShapeRenderer.ShapeType.Line);
        			sr.setColor(Color.BLUE);
        			sr.rect(offsetX+column*100,offsetY-row*100,100,100);
        			
    			}
    			else {
    				batch.begin();
    				batch.draw(c.getTexture(),offsetX+column*100,offsetY-row*100,100,100);
    				batch.end();
    			}
    			sr.end();
    			column++;
    		}
    		row++;
    		column=0;
    	}
    	row=0;
    	column=1;
    	for(ArrayList<enemy> a:enemyGrid) {
    		for(enemy e:a) {
    			sr.begin(ShapeRenderer.ShapeType.Filled);
    			if(e == null) {
    				sr.setColor(Color.WHITE);
        			sr.rect(ui.getScreenWidth()-offsetX-column*100,offsetY-row*100,100,100);
        			sr.end();
        			sr.begin(ShapeRenderer.ShapeType.Line);
        			sr.setColor(Color.BLUE);
        			sr.rect(ui.getScreenWidth()-offsetX-column*100,offsetY-row*100,100,100);
        			
    			}
    			else {
    				batch.begin();
    				batch.draw(e.getTexture(),ui.getScreenWidth()-offsetX-column*100,offsetY-row*100,100,100);
    				batch.end();
    			}
    			sr.end();
    			column++;
    		}
    		row++;
    		column=1;
    	}
    	
    	row=1;
    	sr.begin(ShapeRenderer.ShapeType.Filled);
    	for(ArrayList<enemy> a:enemyGrid) {
    		for (enemy e:a) {
    			if (e!= null) {
    				sr.setColor(Color.RED);
    				sr.rect(600+column*100,110+row*100,96,10);
    				sr.setColor(Color.GREEN);
    				System.out.println((int)(e.getCurrentHealth()*100)/e.getMaxHealth());
    				sr.rect(600+2+column*100,110+row*100,(int)(e.getCurrentHealth()*96)/e.getMaxHealth(),10);
    			}
    			column++;
    		}
			row++;
			column=1;
    	}
    	row = 1;
    	for(ArrayList<Character> a:playerGrid) {
    		for(Character c:a) {
    			if(c != null) {
    				sr.setColor(Color.RED);
    				sr.rect(2+column*100,110+row*100,96,10);
    				sr.setColor(Color.GREEN);
    				System.out.println((int)(c.getCurrentHealth()*100)/c.getMaxHealth());
    				sr.rect(2+column*100,110+row*100,(int)(c.getCurrentHealth()*96)/c.getMaxHealth(),10);
    			}
    			
    			column++;
    		}
    		row++;
    		column=1;
    	}
    	sr.end();
    	switch(flag){
    		case 0:
    			
    			break;
    		case 2:
    			//health bars
    			break;
    	}
    	selector.render(sr,offsetX,offsetY);

    }
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}
