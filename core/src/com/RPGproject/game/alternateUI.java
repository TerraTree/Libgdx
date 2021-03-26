package com.RPGproject.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class alternateUI extends ScreenAdapter {
    Main game;
    Party charParty;
    int uiType;
    ArrayList<Item> itemContent;
    ArrayList<Item> active;
    ArrayList<String> buttons;
    ShapeRenderer sr;
    SpriteBatch batch;
    BitmapFont font;
    Character currentChar;
    Equipment activeEquip;
    //ArrayList<Equipment> viewedEquipment;
    int buying;
    int index;

    public alternateUI(Main game, Party mainParty,int uiType,int fileID,String fileContents){
        this.game = game;
        this.charParty=mainParty;
        this.uiType=uiType;
        if(uiType==1) {
            itemContent=new ArrayList<>();
            try {
                String item;
                boolean reading = false;
                File file = new File("text/"+fileContents + ".txt");
                Scanner scanner = new Scanner(file);

                while (scanner.hasNextLine()) {
                    item = scanner.nextLine();
                    System.out.println("item:" + item);
                    if(reading) {
                        String target="";
                        int duration=0;
                        String equipmentPosition="";
                        String itemName = item.substring(0, item.indexOf(":"));
                        item = item.substring(item.indexOf(":") + 2);
                        System.out.println(item);
                        int cost = Integer.parseInt(item);
                        System.out.println(cost);
                        item = scanner.nextLine();
                        String itemType = item.substring(item.indexOf("{")+1);

                        item = scanner.nextLine();
                        if (itemType.equals("consumable")) {
                            duration = Integer.parseInt(item);
                            item=scanner.nextLine();
                            target = item;
                        } else if (itemType.equals("equipment")) {
                            equipmentPosition = item;
                        }
                        item = scanner.nextLine();
                        String value = "";
                        ArrayList<Integer> stats = new ArrayList<>();
                        System.out.println(item);
                        for (int i = 0; i < Math.max(0,item.length()-1); i++) {
                            System.out.println(i);
                            String chr = String.valueOf(item.charAt(i));
                            if (!chr.equals("[")) {
                                if (chr.equals(",") || chr.equals("]")) {
                                    stats.add(Integer.parseInt(value));
                                    value = "";
                                }
                                else {
                                    value += chr;
                                }
                            }
                        }
                        if(itemType.equals("consumable")){
                            itemContent.add(new Consumable(itemName,itemType,stats,target,duration,cost));
                            System.out.println("yoooo");
                        }
                        else if(itemType.equals("equipment")){
                            itemContent.add(new Equipment(equipmentPosition,itemType,itemName,stats,cost));
                        }

                    }
                    else if (!reading) {
                        if (item.indexOf("id") == 0) {
                            if (Integer.parseInt(item.substring(item.length() - 1))==fileID) {
                                reading = true;
                            }
                        }
                    }
                }
                System.out.println("size"+itemContent.size());
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("file not read, loading temp shop instead");
                ArrayList<Integer> stat = new ArrayList<>();
                stat.add(0);
                stat.add(0);
                stat.add(20);
                itemContent.add(new Consumable("Potion","consumable",stat,"player",0,10));

            }
            buying=0;
        }

        //turn fileName into textContent
    }

    public alternateUI(Main game, Party mainParty,int uiType){
        this.game = game;
        this.charParty=mainParty;
        this.uiType=uiType;
    }

    public void show(){
        index=-1;
        buttons = new ArrayList<>();
        sr = new ShapeRenderer();
        batch = new SpriteBatch();
        font=new BitmapFont();
        font.getData().setScale(4);
        font.setColor(Color.BLACK);
        active = new ArrayList<>();
        if(uiType==1){ //shop ui
            buttons.add("Buy");
            buttons.add("Sell");
        }
        if(uiType==2 || uiType==3){//viewCharacters
            buttons.add(charParty.getChar1().getName());
            buttons.add(charParty.getChar2().getName());
        }
        if(uiType==4){
            buttons.add("Save");
        }
        buttons.add("Exit");
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                int index = -1;
                screenY = Gdx.graphics.getHeight() - screenY;
                if (screenY >= Gdx.graphics.getHeight() - 150 && screenY <= Gdx.graphics.getHeight() - 50) {
                    index = (screenX - 10) / ((Gdx.graphics.getWidth() - 20) / 3);
                    System.out.println(index);
                }
                if (uiType == 1) {
                    if (index == 0) {
                        active = itemContent;
                    } else if (index == 1) {
                        active = charParty.getItems();
                    } else if (index == 2) {
                        game.setScreen(new mainScreen(game, charParty));
                    }
                    int x = 0;
                    for (int i = 0; i < active.size(); i++) {
                        int height = i;
                        if (i % 2 == 1) {
                            height = i - 1;
                        }
                        if (screenY >= Gdx.graphics.getHeight() - 300 - 60 * height && screenY <= Gdx.graphics.getHeight() - 200 - (60 * height)) {
                            if ((screenX >= 50 && screenX <= 650 && i % 2 == 0) || (screenX >= Gdx.graphics.getWidth() / 2 + 50 && screenX <= Gdx.graphics.getWidth() / 2 + 650 && i % 2 == 1)) {
                                if (active == itemContent) {
                                    boolean buy = false;
                                    for (Item j : charParty.getItems()) {
                                        if (active.get(i).getName().equals(j.getName())) {
                                            j.setQuantity(j.getQuantity() + 1);
                                            buy = true;
                                        }
                                    }
                                    if (buy == false) {
                                        charParty.getItems().add(active.get(i));
                                    }
                                } else if (active == charParty.getItems()) {
                                    active.get(i).setQuantity(active.get(i).getQuantity() - 1);
                                    if (active.get(i).getQuantity() == 0) {
                                        active.get(i).setQuantity(1);
                                        active.remove(i);
                                        break;
                                    }
                                }
                            } else if (screenX >= Gdx.graphics.getWidth() / 2 + 50 && screenX <= Gdx.graphics.getWidth() / 2 + 650) {

                            }
                        }
                    }
                } else if (uiType == 2 || uiType == 3) {
                    if (index == 0) {
                        currentChar = charParty.getChar1();
                        active = new ArrayList<>();
                        for (Equipment e : currentChar.getCharEquip()) {
                            active.add(e);
                        }
                    } else if (index == 1) {
                        currentChar = charParty.getChar2();
                        active = new ArrayList<>();
                        for (Equipment e : currentChar.getCharEquip()) {
                            active.add(e);
                        }
                    } else if (index == 2) {
                        game.setScreen(new mainScreen(game, charParty));
                    }
                    if (uiType == 3) {
                        screenY = Gdx.graphics.getHeight() - screenY;
                        screenY = screenY - 180;
                        if (screenX >= 50 && screenX <= 650) {
                            System.out.println(screenY % 120);
                            if (screenY % 120 >= 20) {
                                System.out.println("yo2");
                                index = screenY / 120;
                                ArrayList<Item> swapEquip = new ArrayList<>();
                                if (index < active.size() && index != 7) {
                                    if (!active.equals(currentChar.getCharEquip())) {
                                        for (Equipment e : currentChar.getCharEquip()) {
                                            if (e.getEquipmentPosition().equals(((Equipment) active.get(index)).getEquipmentPosition())) { //equipping an item
                                                Equipment newEquip = (Equipment) active.get(index);
                                                int i = charParty.getItems().indexOf(active.get(index));
                                                charParty.getItems().get(i).setQuantity(charParty.getItems().get(i).getQuantity() - 1);
                                                if (charParty.getItems().get(i).getQuantity() == 0) {
                                                    charParty.getItems().remove(i);
                                                }
                                                boolean inBag = false;
                                                for (Item item : charParty.getItems()) {
                                                    if (e.getName().equals(item.getName())) {
                                                        item.setQuantity(item.getQuantity() + 1);
                                                        inBag = true;
                                                        break;
                                                    }
                                                }
                                                if (inBag == false) {
                                                    charParty.getItems().add(e);
                                                }
                                                newEquip.Equip(currentChar);
                                                currentChar.getCharEquip().set(currentChar.getCharEquip().indexOf(e), newEquip);
                                                active = new ArrayList<>();
                                                for (Equipment e2 : currentChar.getCharEquip()) {
                                                    active.add(e2);
                                                }
                                                //currentChar.updateStats();
                                            }
                                        }

                                    } else {
                                        for (Item i : charParty.getItems()) {
                                            if (i.getType().equals(currentChar.getCharEquip().get(index).getType())) {
                                                Equipment e = currentChar.getCharEquip().get(index);
                                                if (((Equipment) i).getEquipmentPosition().equals(e.getEquipmentPosition())) {
                                                    swapEquip.add(i);
                                                }

                                            }
                                        }
                                        active = swapEquip;
                                    }
                                } else if (index == active.size()) {
                                    if (active.equals(currentChar.getCharEquip())) {
                                        currentChar = null;
                                    } else {
                                        active = new ArrayList<>();
                                        for (Equipment e : currentChar.getCharEquip()) {
                                            active.add(e);
                                        }
                                    }
                                } else if (active.size() > 6 && index == 7) {
                                    if (screenX <= 50 + Gdx.graphics.getWidth() / 4 - 10) {
                                    } else if (screenX > 50 + Gdx.graphics.getWidth() / 4 + 10) {
                                        if (active.equals(currentChar.getCharEquip())) {
                                            currentChar = null;
                                        } else {
                                            active = new ArrayList<>();
                                            for (Equipment e : currentChar.getCharEquip()) {
                                                active.add(e);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (uiType == 4) {
                    System.out.println("yo");
                    if (screenX >= (Gdx.graphics.getWidth() / 2) - 500 && screenX <= (Gdx.graphics.getWidth() / 2) + 500) {
                        screenY = Gdx.graphics.getWidth() - screenY - 700;
                        int i = -1;
                        if (screenY % 300 >= 100) {
                            i = screenY / 300;
                        }
                        System.out.println("i" + i);
                        if (i != -1) {
                            charParty.saveFile("text/" + "save" + 3 + ".txt");
                        }
                    }
                    //sr.rect(Gdx.graphics.getWidth()/2 -500,700-i*300,1000,200);
                }
                return false;
            }
            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                if (uiType == 3) {
                    screenY = screenY - 180;
                    if (currentChar != null) {
                        int index;
                        for (int i = 0; i < active.size(); i++) {
                            if (screenX >= 50 && screenX <= 650) {
                                if (screenY % 120 >= 20) {
                                    index = screenY / 120;
                                    if(index>=active.size()){
                                        activeEquip=null;
                                    }
                                    else if(index<active.size()){
                                        activeEquip = (Equipment) active.get(index);
                                    }
                                    else{
                                        activeEquip = null;
                                    }
                                } else {
                                    activeEquip = null;
                                }
                            }
                        }
                    }
                }
                return true;
            }
        });
    }

    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        int counter=0;
        font.setColor(Color.BLACK);
        font.getData().setScale(4);
        for (String button:buttons) {
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.rect(10+counter*(Gdx.graphics.getWidth()-20)/buttons.size(),Gdx.graphics.getHeight()-150,(Gdx.graphics.getWidth()-20)/buttons.size(),100);
            sr.end();
            batch.begin();
            font.draw(batch,button,10+counter*(Gdx.graphics.getWidth()-10)/buttons.size(),Gdx.graphics.getHeight()-100);
            batch.end();
            counter++;
        }

        if(uiType==1){ //renders shop ui
            if(active!=null) {
                for (int i = 0; i < Math.max(active.size(), 2); i += 2) {
                    if(active.size()!=0) {
                        sr.begin(ShapeRenderer.ShapeType.Filled);
                        sr.setColor(Color.WHITE);
                        sr.rect(50, Gdx.graphics.getHeight() - 300 - 60 * i, 600, 100);
                        sr.end();
                        batch.begin();
                        String str;
                        if(active==charParty.getItems()){
                            str = active.get(i).getQuantity()+"x "+active.get(i).getName() + ": " + active.get(i).getCost() + "gp";
                        }
                        else{
                            str = active.get(i).getName() + ": " + active.get(i).getCost() + "gp";
                        }
                        font.draw(batch, str, 50, Gdx.graphics.getHeight() - 200 - 60 * i);
                        batch.end();
                        if (i + 1 <= active.size() - 1) {
                            if(active==charParty.getItems()){
                                str = active.get(i+1).getQuantity()+"x "+active.get(i+1).getName() + ": " + active.get(i+1).getCost() + "gp";
                            }
                            else{
                                str = active.get(i+1).getName() + ": " + active.get(i+1).getCost() + "gp";
                            }
                            sr.begin(ShapeType.Filled);
                            sr.setColor(Color.WHITE);
                            sr.rect(Gdx.graphics.getWidth()/2+50, Gdx.graphics.getHeight() - 300 -60*i, 600, 100);
                            sr.end();
                            batch.begin();
                            font.draw(batch, str, Gdx.graphics.getWidth() / 2 + 50, Gdx.graphics.getHeight() - 200 - 60 * i);
                            batch.end();
                        }
                        
                    }
                }
            }
        }
        else if(uiType==2){//renders character stat ui
            if(currentChar!=null){
                font.getData().setScale(2);
                font.setColor(Color.WHITE);
                batch.begin();
                ArrayList<Integer> totalStats = new ArrayList<>();
                for(int j=0;j<7;j++) {
                	totalStats.add(0);
                }
                int i;
                for(Equipment e:currentChar.getCharEquip()) {
                	i=0;
                	for(int j = 0;j<Math.min(e.getStats().size(), 7);j++) {
                		
                		totalStats.set(i,totalStats.get(i)+e.getStats().get(j));
                		i++;
                	}
                }
                font.draw(batch,"Strength: "+(currentChar.getStrength()+totalStats.get(0)),50,Gdx.graphics.getHeight()-200);
                font.draw(batch,"Dexterity: "+(currentChar.getDexterity()+totalStats.get(1)),50,Gdx.graphics.getHeight()-250);
                font.draw(batch,"Agility: "+(currentChar.getAgility()+totalStats.get(2)),50,Gdx.graphics.getHeight()-300);
                font.draw(batch,"Wisdom: "+(currentChar.getWisdom()+totalStats.get(3)),50,Gdx.graphics.getHeight()-350);
                font.draw(batch,"Intelligence: "+(currentChar.getIntelligence()+totalStats.get(4)),50,Gdx.graphics.getHeight()-400);
                font.draw(batch,"Health: "+ (currentChar.getCurrentHealth()+totalStats.get(5)) +"/"+currentChar.getMaxHealth(),Gdx.graphics.getWidth()/2 + 50,Gdx.graphics.getHeight()-200);
                font.draw(batch,"Defense: " + (currentChar.getDefense()+totalStats.get(6)),Gdx.graphics.getWidth()/2 +50,Gdx.graphics.getHeight()-250);
                font.draw(batch,"Level: "+currentChar.getLevel(),Gdx.graphics.getWidth()/2 +50,Gdx.graphics.getHeight()-300);
                font.draw(batch,"Exp: "+(int) (currentChar.getExp()-20*(currentChar.getLevel()-1)-Math.pow(2,currentChar.getLevel()-2))+"/"+(int) (20+Math.pow(2,currentChar.getLevel()-1)),Gdx.graphics.getWidth()/2 +50,Gdx.graphics.getHeight()-350);
                batch.end();
//                for (int i = 0; i < ; i++) {
//
//                }
            }
        }
        else if(uiType==3){//renders equipment ui
        	if(currentChar!=null) {
        	int i=0;
        	if(activeEquip!=null){
        	    Equipment e =  activeEquip;
        	    batch.begin();
        	    font.setColor(Color.WHITE);
        	    try {
        	        int y=Gdx.graphics.getHeight()-160;
                    font.draw(batch, e.getName(), Gdx.graphics.getWidth() / 2 + 100, y);
                    font.draw(batch, e.getEquipmentPosition(), Gdx.graphics.getWidth() / 2 + 100, y-50);
                    font.draw(batch, e.getType(), Gdx.graphics.getWidth() / 2 + 100, y-100);
                    font.draw(batch, "Defense " + e.getStats().get(0), Gdx.graphics.getWidth() / 2 + 100, y-150);
                    font.draw(batch, "Attack " + e.getStats().get(1), Gdx.graphics.getWidth() / 2 + 100, y-200);
                    font.draw(batch, "Health " + e.getStats().get(2), Gdx.graphics.getWidth() / 2 + 100, y-250);
                    font.draw(batch, "Strength " + e.getStats().get(3), Gdx.graphics.getWidth() / 2 + 100, y-300);
                    font.draw(batch, "Dexterity " + e.getStats().get(4), Gdx.graphics.getWidth() / 2 + 100, y-350);
                    font.draw(batch, "Agility " + e.getStats().get(5), Gdx.graphics.getWidth() / 2 + 100, y-400);
                    font.draw(batch, "Wisdom " + e.getStats().get(6), Gdx.graphics.getWidth() / 2 + 100, y-450);
                    font.draw(batch, "Intelligence " + e.getStats().get(7), Gdx.graphics.getWidth() / 2 + 100, y-500);
                }
        	    catch(Exception ex){
                }
                batch.end();
            }
        	font.setColor(Color.BLACK);
        	for(Item item:active) {
        	    Equipment e = (Equipment) item;
                sr.begin(ShapeType.Filled);
                if(i>=7){
                    sr.rect(50,Gdx.graphics.getHeight()-300 - 120 * i,Gdx.graphics.getWidth()/4 - 10,100);
                    sr.rect(70+Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()-300 - 120*i,Gdx.graphics.getWidth()/4 -10,100);
                    sr.end();
                    batch.begin();
                    font.draw(batch,"More",50,Gdx.graphics.getHeight() - 250 - 120 * i);
                    font.draw(batch,"Back",70+Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight() - 250 - 120 * i);
                    batch.end();
                    break;
                }
                else {
                    sr.rect(50, Gdx.graphics.getHeight() - 300 - 120 * i, Gdx.graphics.getWidth() / 2 - 50, 100);
                    sr.end();
                    if (e != null) {
                        batch.begin();
                        font.draw(batch, e.getEquipmentPosition() + ": " + e.getName(), 50, Gdx.graphics.getHeight() - 250 - 120 * i);
                        batch.end();
                    }
                }
        		i++;
        	}
        	if(i<=7){
        	    sr.begin(ShapeType.Filled);
        	    sr.rect(50, Gdx.graphics.getHeight() - 300 - 120 * i, Gdx.graphics.getWidth() / 2 - 50, 100);
        	    sr.end();
                batch.begin();
                font.draw(batch, "Back", 50, Gdx.graphics.getHeight() - 250 - 120 * i);
                batch.end();
            }
        	}
        }
        if(uiType==4){
            for (int i = 0; i < 3; i++) {
                sr.begin(ShapeType.Filled);
                sr.rect(Gdx.graphics.getWidth()/2 -500,700-i*300,1000,200);
                sr.end();
            }
        }

    }
}
