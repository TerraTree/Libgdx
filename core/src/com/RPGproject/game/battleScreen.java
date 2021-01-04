package com.RPGproject.game;

import java.util.*;

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
import com.badlogic.gdx.math.Vector2;

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
	int turnCount;
	boolean typing;
	Character selectChar;
	textProcessor tp;
	SpriteBatch batch;
    public Queue<Attack> battleQueue;
    ArrayList<entity> turnOrder;

    public battleScreen(Main game,Party mainParty,Party enemyParty){
    	this.game = game;
    	this.mainParty=mainParty;
    	this.enemyParty=enemyParty;
    }

    public ArrayList<entity> mergeSort(ArrayList<entity> turnOrder){
    	int mid = turnOrder.size()/2;
    	ArrayList<entity> arr1 = new ArrayList<>();
		ArrayList<entity> arr2 = new ArrayList<>();
		ArrayList<entity> arr3 = new ArrayList<>();
		if(turnOrder.size()>1) {
			for (int i = 0; i < mid; i++) {
				arr1.add(turnOrder.get(i));
				arr1=mergeSort(arr1);
			}
			for (int i = mid; i < turnOrder.size(); i++) {
				arr2.add(turnOrder.get(i));
				arr2=mergeSort(arr2);
			}
            for (int i = 0; i < arr1.size(); i++) {
                for (int j = 0; j < arr2.size(); j++) {
                    if (arr2.get(j).getAgility()<arr1.get(i).getAgility()){
                        arr3.add(arr2.get(j));
                        arr2.remove(j);
                        j--;
                    }
                }
                arr3.add(arr1.get(i));
                arr1.remove(i);
                i--;
            }
            for (entity e: arr2) {
                arr3.add(e);
            }
            arr2.removeAll(arr2);
            turnOrder=arr3;
            }
		else{
		    return turnOrder;
        }
		return turnOrder;
	}


    public void show(){

        //INITIALISATION
        typing=false;
		offsetX = Gdx.graphics.getWidth()/6;
		offsetY = Gdx.graphics.getHeight()/2 + 150;
		turnCount=0;
    	enemy enem1 = new enemy(1,1,2,4,1);
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
        enemy enem2 = new enemy(1,1,9,4,1);
        enem2.setTexture(texture);
        enemyGrid.get(0).set(0, enem2);

    	sr = new ShapeRenderer();
    	selector = new Selector(offsetX,offsetY,100,100); //sets position and size of selector
    	tp = new textProcessor();
    	batch = new SpriteBatch();
        ui.getMainText().input("hi"); //temp, ensures text in main text
        battleQueue = new LinkedList<>(); //init queue
        turnOrder = new ArrayList<>();

        //INPUT PROCESSOR
    	Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean keyDown ( int keycode){
				if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
					Gdx.app.exit();
				}
            	if(selector.active) {
                    if (keycode == Input.Keys.LEFT) {
                        selector.xOffset = 0;
                    } else if (keycode == Input.Keys.RIGHT) {
                        selector.xOffset = 100;
                    } else if (keycode == Input.Keys.UP && selector.yOffset < 0) {
                        selector.yOffset += 100;
                    } else if (keycode == Input.Keys.DOWN && selector.yOffset > -200) {
                        selector.yOffset -= 100;
                    }
                }

            		if(keycode==Input.Keys.ENTER ) {
            			if(ui.getMainText().getTextContent().get(ui.getMainText().getTextContent().size()-1).equals("attack")){
							if(enemyGrid.get(-selector.yOffset / 100).get(selector.xOffset / 100)!=null && selector.active==true){
								Vector2 enemyPos= new Vector2(-selector.yOffset / 100,selector.xOffset / 100);
								Vector2 playerPos = new Vector2(0,0); //will be implemented elsewhere
								int damage = 1; //damage based on weapon and character selected, not implemented yet
								Attack charAttack = new Attack(enemyPos,playerPos,true,"sword",damage);
								System.out.println("battleQueue added");
								battleQueue.add(charAttack);
							}
//							else if(selector.active!=true){
//							    System.out.println("selector active");
//                                selector.active=true;
//                                selector.xOffset=ui.getScreenWidth()-offsetX;
//                                System.out.println(selector.xOffset);
//                                System.out.println(selector.yOffset);
//                                System.out.println("flag: "+ flag);
//                                //selector.yOffset=offsetY+110;
//							}
						}
            			else {
							playerGrid.get(-selector.yOffset / 100).set(selector.xOffset / 100, selectChar); //set player character to grid
							flag++;
							if (flag == 1) {
								selectChar = mainParty.getChar2();
							} else if (flag == 2) {
                                selector.active = false;

                                for (ArrayList<Character> g : playerGrid) {
                                    for (Character e : g) {
                                        if (e != null) {
                                            turnOrder.add(e);
                                        }
                                    }
                                }
                                for (ArrayList<enemy> g : enemyGrid) {
                                    for (enemy e : g) {
                                        if (e != null) {
                                            turnOrder.add(e);
                                        }
                                    }
                                }
                                System.out.println(turnOrder.size());
                                turnOrder=mergeSort(turnOrder);
                                System.out.println(turnOrder);
                                typing=true;
                            }
						}
            		}
                return true;
            }
            public boolean keyTyped(char character) {

            	if(typing){
					if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE) && ui.getInputText().getTextContent().get(0).length()>0){
						ui.getInputText().removeChar();
					}
					else if (Gdx.input.isKeyPressed(Input.Keys.ENTER)){
						String inputString = ui.getMainText().input(ui.getInputText());
						ArrayList<String> menuContent = ui.getMenuText().getTextContent();
						int a = tp.processing(inputString,menuContent);
						System.out.println("a:"+a);
						if(a==-1){ //temporary placement
						    System.out.println("typing stopped");
						    flag++;
						    typing=false;
                            selector.active=true;
                            selector.setX(ui.getScreenWidth()-offsetX);
                            selector.setWidth(-selector.getWidth());
                            selector.xOffset=-selector.xOffset;
                            System.out.println(selector.xOffset);
                        }
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
    		if(flag==0){

            }
    		if(flag>=2) { //display health bars
                column = 1;
                row = 0;
                sr.begin(ShapeRenderer.ShapeType.Filled);
                for (ArrayList<enemy> a : enemyGrid) {
                    for (enemy e : a) {
                        if (e != null) {
                            sr.setColor(Color.RED);
                            sr.rect(ui.getScreenWidth() - offsetX + 2 - column * 100, offsetY + 110 - row * 100, 96, 10);
                            sr.setColor(Color.GREEN);
                            sr.rect(ui.getScreenWidth() - offsetX + 2 - column * 100, offsetY + 110 - row * 100, (int) (e.getCurrentHealth() * 96) / e.getMaxHealth(), 10);
                        }
                        column++;
                    }
                    row++;
                    column = 1;
                }
                column = 0;
                row = 0;
                for (ArrayList<Character> a : playerGrid) {
                    for (Character c : a) {
                        if (c != null) {
                            sr.setColor(Color.RED);
                            sr.rect(offsetX + 2 + column * 100, offsetY + 110 - row * 100, 96, 10);
                            sr.setColor(Color.GREEN);
                            sr.rect(offsetX + 2 + column * 100, offsetY + 110 - row * 100, (int) (c.getCurrentHealth() * 96) / c.getMaxHealth(), 10);
                        }
                        column++;
                    }
                    row++;
                    column = 0;
                }
                sr.end();
            }
    	selector.render(sr);

    }
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}
