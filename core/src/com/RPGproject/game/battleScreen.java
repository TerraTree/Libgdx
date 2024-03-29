package com.RPGproject.game;

import java.awt.*;
import java.util.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;


public class battleScreen extends ScreenAdapter {
	Main game;
	UserInterface ui;
	Party mainParty;
	ArrayList<enemy> enemyParty;
	ArrayList<ArrayList<Character>> playerGrid;
	ArrayList<ArrayList<enemy>> enemyGrid;
	ShapeRenderer sr;
	Selector selector;
	BitmapFont font;
	int flag;
	int offsetX;
	int offsetY;
	int turnCount;
	boolean typing;
	boolean attacking;
	boolean items;
	Item item;
	Character selectChar;
	textProcessor tp;
	SpriteBatch batch;
    public Queue<Attack> battleQueue;
    ArrayList<entity> turnOrder;
    ArrayList<Item> battleItems;
    TextureRegion spriteGrid;
    Sprite sprite;

    public battleScreen(Main game,Party mainParty,ArrayList<enemy> enemyParty){
    	this.game = game;
    	this.mainParty=mainParty;
    	this.enemyParty=enemyParty;
    }

    public void turnChecker(){
		int counter=0;
		for (ArrayList<enemy> e: enemyGrid) {
			for(enemy enem: e){
				if (enem!=null){
					counter++;
				}
			}
		}
		if(counter==0){ //all enemies dead
			for (entity e:turnOrder) {
				for(Consumable c:e.getStatusEffects()){
					c.durationEnd(e);
				}
			}
			game.setScreen(new mainScreen(game,mainParty));
		}
		if(turnOrder.size()!=0 || (turnCount==0 && battleQueue.size()>0)) {
			try {
                Vector2 enemPos = new Vector2();
				enemy enem = (enemy) turnOrder.get(turnCount);
				int index=0;
                for (ArrayList<enemy> e:enemyGrid) {
                    if(e.indexOf(enem)!=-1){
                        enemPos.set(index,e.indexOf(enem));
                    }
                    index++;
                }
				Attack enemAttack = enem.turn(enemPos,playerGrid);
                battleQueue.add(enemAttack);
				turnCount++;
				turnChecker();
			} catch (Exception e) {

				if (turnOrder.size()!=turnCount){
					if(!turnOrder.get(turnCount).isActive()) {
						turnCount++;
						turnChecker();
					}
					else{
                        ui.getMainText().input(turnOrder.get(turnCount).getName()+"'s turn");
                        ui.getMenuText().clear();
                        for (String action: ((Character) turnOrder.get(turnCount)).getCharClass().getActions()) {
                            ui.getMenuText().input(action);
                        }
                    }
				}
			}
			if(turnCount==turnOrder.size() || (turnCount==0 && battleQueue.size()>0)){
                battleQueue.peek().attacking(battleQueue,playerGrid,enemyGrid);
				turnCount=0;
				for (entity e:turnOrder) {
					for(Consumable c:e.getStatusEffects()){
						c.setDuration(c.getDuration()-1);
						if(c.getDuration()==0){
							c.durationEnd(e);
							e.getStatusEffects().remove(c);
						}
					}
				}
                if(mainParty.getChar1().getCharClass().getLevelUpChange().size()>0){
                	flag=-1;
                	battleQueue.poll();
				}
                else if(mainParty.getChar2().getCharClass().getLevelUpChange().size()>0){
                	flag=-2;
                	battleQueue.poll();
				}
                else if(playerGrid.get(mainParty.getChar1().getBattlePos().x).get(mainParty.getChar1().getBattlePos().y)==null){
                    if(playerGrid.get(mainParty.getChar2().getBattlePos().x).get(mainParty.getChar2().getBattlePos().y)==null){
						for (entity e:turnOrder) {
							for(Consumable c:e.getStatusEffects()){
								c.durationEnd(e);
							}
						}
                        game.setScreen(new mainScreen(game,mainParty));
                    }
                }
                else{
                	turnChecker();
				}
			}
		}
	}

	public void endTurn(){
		turnCount++;
		typing=true;
		selector.active=false;
		items=false;
		attacking=false;
		turnChecker();
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
		return turnOrder;
	}


    public void show(){
        font = new BitmapFont();
    	sprite = new Sprite();
    	attacking=false;
    	items=false; //using items against enemies
        //spriteGrid = new TextureRegion();

        //INITIALISATION
    	battleItems = new ArrayList<>();
        typing=false;
		offsetX = Gdx.graphics.getWidth()/6;
		offsetY = Gdx.graphics.getHeight()/2 + 150;
		turnCount=0;
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
    	enemyGrid.get(1).set(1, enemyParty.get(0));
        enemyGrid.get(0).set(0, enemyParty.get(1));

    	sr = new ShapeRenderer();
    	selector = new Selector(offsetX,offsetY,100,100,1); //sets position and size of selector
    	tp = new textProcessor();
    	batch = new SpriteBatch();
        ui.getMainText().input("Choose tiles for characters"); //temp, ensures text in main text
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
				if(ui.getMenuText().getTextPointer().active) {
					Selector textPointer = ui.getMenuText().getTextPointer();
					if (keycode == Input.Keys.UP && textPointer.yOffset > 0) {
						textPointer.yOffset -= 1;
						textPointer.sprite.setY(textPointer.getY()-textPointer.yOffset*30);

					} else if (keycode == Input.Keys.DOWN && textPointer.yOffset < ui.getMenuText().getTextContent().size()-1) {
						textPointer.yOffset += 1;
						textPointer.sprite.setY(textPointer.getY()-textPointer.yOffset*30);
					}
				}
            		if(keycode==Input.Keys.ENTER ) {
            			if(ui.getMenuText().getTextPointer().active){
            				ui.getMenuText().getTextPointer().active=false;
            				item = battleItems.get(ui.getMenuText().getTextPointer().yOffset);
                            System.out.println(item.getQuantity());
            				item.setQuantity(item.getQuantity()-1);
            				if(item.getQuantity()==0){
            					mainParty.getItems().remove(mainParty.getItems().indexOf(item));
            					battleItems.remove(battleItems.indexOf(item));
							}
							ui.getMenuText().clear();

            				if(item.getType().equals("consumable")){
            					System.out.println("working");
            					Consumable usedItem= (Consumable) item;
            					items=true;
            					selector.active=true;
            					if(usedItem.getTarget().equals("enemy")){
									selector.setX(ui.getScreenWidth()-offsetX-200);
								}
            					else if(usedItem.getTarget().equals("player")){
            						selector.setX(Gdx.graphics.getWidth()/6);
								}
							}
							else{
								endTurn();

							}
						}
            			else if(attacking){
            			    enemy currentEnemy = enemyGrid.get(-selector.yOffset / 100).get(selector.xOffset / 100);
							if(currentEnemy!=null && selector.active==true){
								Vector2 enemyPos= new Vector2(-selector.yOffset / 100,selector.xOffset / 100);
								Vector2 playerPos = new Vector2(0,0);
								Character currentChar = (Character) turnOrder.get(turnCount);
								int index=0;
								for (ArrayList<Character> c:playerGrid) {
									if(c.indexOf(currentChar)!=-1){
										playerPos.set(c.indexOf(currentChar),index);
									}
									index++;
								}
								int damage;
								if(currentChar.getCharEquip().get(0).getStats().size()>=2){
									damage=currentChar.getDexterity()/4+2+currentChar.getCharEquip().get(0).getStats().get(1);
								}
								else{
									damage=currentChar.getDexterity()/4+2;
								}
								Attack charAttack = new Attack(currentEnemy,enemyPos,currentChar,playerPos,true,"sword",damage);
								battleQueue.add(charAttack);
								endTurn();
							}
						}
            			else if(items){
							entity target=null;
							Consumable usedItem = (Consumable) item;
							System.out.println("item: "+usedItem);
							//System.out.println(usedItem.getTarget());
            				if(usedItem.getTarget().equals("player")) {
								 target = playerGrid.get(-selector.yOffset / 100).get(selector.xOffset / 100);
								//currentChar.setCurrentHealth(Math.min(currentChar.getCurrentHealth() + 50, currentChar.getMaxHealth()));
							}

            				else if(usedItem.getTarget()=="enemy"){
								target = playerGrid.get(-selector.yOffset / 100).get(selector.xOffset / 100);
							}
							usedItem.Consume(target);
							endTurn();
						}
            			else if(flag >=0 && flag<=2) {
							selectChar.getSprite().setX(offsetX+selector.xOffset);
            				selectChar.getSprite().setY(offsetY+selector.yOffset);
            				selectChar.setBattlePos(new Point(-selector.yOffset /100,selector.xOffset/100));
							playerGrid.get(selectChar.getBattlePos().x).set(selectChar.getBattlePos().y, selectChar); //set player character to grid
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
                                turnOrder=mergeSort(turnOrder);
								turnChecker();
                                typing=true;
                            }
						}
            			else if(flag==-1){
            				mainParty.getChar1().getCharClass().setLevelUpChange(new ArrayList<Integer>());
            				if(mainParty.getChar2().getCharClass().getLevelUpChange().size()>0) {
								flag--;
							}
            				else{
								turnChecker();
            					flag=3;
							}
						}
            			else if(flag==-2){
							mainParty.getChar2().getCharClass().setLevelUpChange(new ArrayList<Integer>());
            				turnChecker();
            				flag=3;
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
						int a = tp.processing(inputString,((Character) turnOrder.get(turnCount)).getCharClass().getActions());
						if(a==1){ //temporary placement
						    System.out.println("typing stopped");
						    flag++;
						    typing=false;
                            selector.active=true;
                            attacking=true;
                            selector.setX(ui.getScreenWidth()-offsetX-200);
                        }
						else if(a==2){ //items
							battleItems = new ArrayList<>();
							for(Item i: mainParty.getItems()) {
								if(i.getType().equals("consumable")) {
									battleItems.add(i);
								}
							}
						    if(battleItems.size()>0) {
								typing = false;
								ui.getMenuText().getTextPointer().active = true;
								for (int i = ui.getMenuText().getTextContent().size(); i > 0; i--) {
									ui.getMenuText().getTextContent().remove(i - 1);
								}
								for (Item item : battleItems) {
									ui.getMenuText().getTextContent().add(item.getQuantity()+"x "+item.getName());
								}
							}
						    else {
						    	ui.getMainText().input("no items to choose");
							}
                        }
						else if(a==3){
                            playerGrid.get(((Character) turnOrder.get(turnCount)).getBattlePos().x).set(((Character) turnOrder.get(turnCount)).getBattlePos().y,null);
						    turnOrder.get(turnCount);
						    turnOrder.remove(turnCount);
						    turnCount--;
						    endTurn();
                        }
						//tp.menuDialogue(ui);
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
    				if(c.getCurrentHealth()==0){
						c.getSprite().setRotation(90);
					}
					c.getSprite().draw(batch);
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
//    			sr.begin(ShapeRenderer.ShapeType.Filled);
    			if(e == null) {
    			    sr.begin(ShapeRenderer.ShapeType.Filled);
    				sr.setColor(Color.WHITE);
        			sr.rect(ui.getScreenWidth()-offsetX-300+column*100,offsetY-row*100,100,100);
        			sr.end();
        			sr.begin(ShapeRenderer.ShapeType.Line);
        			sr.setColor(Color.BLUE);
        			sr.rect(ui.getScreenWidth()-offsetX-300+column*100,offsetY-row*100,100,100);

    			}
    			else {
    				//e.getSprite().draw(batch);
					batch.begin();
    				batch.draw(e.getSprite().getTexture(),ui.getScreenWidth()-offsetX-300+column*100,offsetY-row*100,100,100);
    				batch.end();
    			}
    			sr.end();
    			column++;
    		}
    		row++;
    		column=1;
    	}
    	if(flag==-1){
    	    //Character currentChar = playerGrid.get();
    	    Character currentChar = mainParty.getChar1();
    	    currentChar.renderLevelUp(sr,batch);
        }
    	if(flag==-2){
    	    Character currentChar = mainParty.getChar2();
    	    currentChar.renderLevelUp(sr,batch);
        }

    	if(flag==0){

            }
    		if(flag>=2) { //display health bars
                column = 1;
                row = 0;
                sr.begin(ShapeRenderer.ShapeType.Filled);
                for (ArrayList<enemy> a : enemyGrid) {
                    for (enemy e : a) {
                        if (e!=null) {
                            sr.setColor(Color.RED);
                            sr.rect(ui.getScreenWidth() - offsetX-300 + 2 + column * 100, offsetY + 110 - row * 100, 96, 20);
                            sr.setColor(Color.GREEN);
                            sr.rect(ui.getScreenWidth() - offsetX-300 + 2 + column * 100, offsetY + 110 - row * 100, (int) (e.getCurrentHealth() * 96) / e.getMaxHealth(), 20);
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
                            sr.rect(offsetX + 2 + column * 100, offsetY + 110 - row * 100, 96, 20);
                            sr.setColor(Color.GREEN);
                            sr.rect(offsetX + 2 + column * 100, offsetY + 110 - row * 100, (int) (c.getCurrentHealth() * 96) / c.getMaxHealth(), 20);
                        }
                        column++;
                    }
                    row++;
                    column = 0;
                }
                sr.end();
            }
    	selector.render(sr,batch);

    }
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}
