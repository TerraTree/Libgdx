package com.RPGproject.game;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class battleScreen extends ScreenAdapter {
	Main game;
	UserInterface ui;
	Party mainParty;
	Party enemyParty;
	ArrayList<ArrayList<Character>> playerGrid;
	ArrayList<enemy> enemyGrid;
	ShapeRenderer sr;
	Selector selector;
	int flag;
	Character selectChar;
	

    public battleScreen(Main game,Party mainParty,Party enemyParty){
    	this.game = game;
    	this.mainParty=mainParty;
    	this.enemyParty=enemyParty;
    }

    public void show(){
    	flag = 0;
    	ui = new UserInterface();
    	playerGrid = new ArrayList<ArrayList<Character>>();
    	for (int i=0;i<3;i++) {
    		playerGrid.add(new ArrayList<Character>());
    		playerGrid.get(i).add(null);
    		playerGrid.get(i).add(null);
    	}
    	selectChar=mainParty.getChar1();
    	enemyGrid = new ArrayList<enemy>();
    	sr = new ShapeRenderer();
    	selector = new Selector(100,300,100,100);
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
            			playerGrid.get(playerGrid.size()-1-selector.yOffset/-100).set(selector.xOffset/100, mainParty.getChar1());
            			flag++;
            			if(flag==1) {
            				selectChar = mainParty.getChar2();
            			}
            			else if (flag==2) {
            				selector.active=false;
            			}
            		}
            	}
            	else if(flag==3){
					if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE) && ui.getInputText().getTextContent().get(0).length()>0){
						ui.getInputText().removeChar();

					}
					else if (Gdx.input.isKeyPressed(Input.Keys.ENTER)){
						String inputString = ui.getMainText().input(ui.getInputText());
						ArrayList<String> menuContent = ui.getMenuText().getTextContent();
						menuTier = tp.processing(inputString,menuTier,menuContent);
						tp.menuDialogue(menuTier,ui);
						if(tp.getFileName().length()!=0) {
							currentMap = tp.fileChoice(tp.getFileName(),currentMap);
							tp.setFileName("");
							tp.setFileLoad(false);
						}
						if (tp.setFileSaving(true)) {
							if(!inputString.equals("save")){
								currentMap.setFileName(inputString+".txt");
								tp.setFileSaving(false);
							}
							tp.fileSave(currentMap,ui);
						}
					}
					else {
						ui.getInputText().addChar(character,ui.getFont());
					}
				}
                
                return true;
            }
    	});
    }

    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);    
    	ui.render();
    	int column = 1;
    	int row = 1;
    	for(ArrayList<Character> a:playerGrid) {
    		for(Character c:a) {
    			sr.begin(ShapeRenderer.ShapeType.Filled);
    			if(c == null) {
    				sr.setColor(Color.WHITE);
    			}
    			else {
    				sr.setColor(Color.GRAY);
    			}
    			sr.rect(column*100,row*100,100,100);
    			sr.end();
    			sr.begin(ShapeRenderer.ShapeType.Line);
    			sr.setColor(Color.BLUE);
    			sr.rect(column*100,row*100,100,100);
    			sr.end();
    			column++;
    		}
    		row++;
    		column=1;
    	}
    	switch(flag){
    		case 0:
    			
    			break;
    		case 2:
    			//health bars
    			break;
    	}
    	selector.render(sr);

    }
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}
