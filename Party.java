package com.RPGproject.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Party {
	private int money;
	private Character char1;
	private Character char2;
	private ArrayList<Item> items;
	private int xCoord;
	private int yCoord;
	private String mapName;
	private Sprite sprite;

	public Party() {
		items = new ArrayList<>();
		money=0;
		sprite=new Sprite(new Texture("smile.png"));
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public Character getChar1() {
		return char1;
	}

	public void setChar1(Character char1) {
		this.char1 = char1;
	}

	public Character getChar2() {
		return char2;
	}

	public void setChar2(Character char2) {
		this.char2 = char2;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

	public int getxCoord() {
		return xCoord;
	}

	public void setxCoord(int xCoord) {
		this.xCoord = xCoord;
	}

	public int getyCoord() {
		return yCoord;
	}

	public void setyCoord(int yCoord) {
		this.yCoord = yCoord;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public void loadFile(){}

	public void saveFile(String fileName){
		try {
			FileWriter fileWrite = new FileWriter(fileName);
			ArrayList<Character> characters = new ArrayList<>();
			characters.add(char1);
			characters.add(char2);
			fileWrite.write("/visual information"+"\n");
			for (Character c:characters) {
				fileWrite.write(c.getName()+"\n");
				fileWrite.write(c.getLevel()+"\n");
				fileWrite.write(""+"\n");
			}
			fileWrite.write(money+"\n");
			fileWrite.write(""+"\n");
			int index=1;
			for (Character c:characters) {
				fileWrite.write("/CHAR"+index+"\n");
				fileWrite.write("Level:"+c.getLevel()+"\n");
				fileWrite.write("EXP:"+c.getExp()+"\n");
				fileWrite.write("Strength:"+c.getStrength()+"\n");
				fileWrite.write("Dexterity:"+c.getDexterity()+"\n");
				fileWrite.write("Agility:"+c.getAgility()+"\n");
				fileWrite.write("Wisdom:"+c.getWisdom()+"\n");
				fileWrite.write("Intelligence:"+c.getIntelligence()+"\n");
				fileWrite.write("CurrentHealth:"+c.getCurrentHealth()+"\n");
				fileWrite.write("MaxHealth:"+c.getMaxHealth()+"\n");
				fileWrite.write("Name:"+c.getName()+"\n");
				fileWrite.write("Class:"+c.getCharClass().getClassName()+"\n");
				fileWrite.write("Equipment:"+"\n");
				for (Equipment e:c.getCharEquip()) {
					e.save(fileWrite);
				}
				fileWrite.write(""+"\n");
				index++;
			}
			fileWrite.write("/LOCATION DATA"+"\n");
			fileWrite.write("Map:"+mapName+"\n");
			fileWrite.write("X:"+xCoord+"\n");
			fileWrite.write("Y:"+yCoord+"\n");
			fileWrite.write(""+"\n");
			fileWrite.write("/INVENTORY"+"\n");
			for (Item i:items) {
				i.save(fileWrite);

			}
			fileWrite.close();
		}
		catch(Exception e){

		}
	}

	public ArrayList<Item> readEquipFile(Scanner scanner, int fileID) {
		System.out.println("yo");
		ArrayList<Item> itemContent = new ArrayList<>();
		String item;
		boolean reading = false;
		if(fileID==-1){
			reading = true;
		}
		while (scanner.hasNextLine()) {
			item = scanner.nextLine();
			if (reading) {
				if(item.equals("")){
					System.out.println("read items");
					break;
				}
				String target = "";
				int duration = 0;
				String equipmentPosition = "";
				String itemName = item.substring(0, item.indexOf(":"));
				item = item.substring(item.indexOf(":") + 2);
				System.out.println(item);
				int cost = Integer.parseInt(item);
				System.out.println(cost);
				item = scanner.nextLine();
				String itemType = item.substring(item.indexOf("{") + 1);

				item = scanner.nextLine();
				System.out.println("item:" + item);
				if (itemType.equals("consumable")) {
					duration = Integer.parseInt(item);
					item = scanner.nextLine();
					System.out.println("item:" + item);
					target = item;
				} else if (itemType.equals("equipment")) {
					equipmentPosition = item;
				}
				item = scanner.nextLine();
				System.out.println("item:" + item);
				String value = "";
				ArrayList<Integer> stats = new ArrayList<>();
				System.out.println("newt:"+item);
				for (int i = 0; i < Math.max(0, item.length() - 1); i++) {
					System.out.println(i);
					String chr = String.valueOf(item.charAt(i));
					if (!chr.equals("[")) {
						if (chr.equals(",") || chr.equals("]")) {
							if(value!=""){
							stats.add(Integer.parseInt(value));
							}
							value = "";
						} else {
							value += chr;
						}
					}
				}
				if (itemType.equals("consumable")) {
					itemContent.add(new Consumable(itemName, itemType, stats, target, duration, cost));
					System.out.println("yoooo");
				} else if (itemType.equals("equipment")) {
					itemContent.add(new Equipment(equipmentPosition, itemType, itemName, stats, cost));
				}
			} else {
				if (item.indexOf("id") == 0) {
					if (Integer.parseInt(item.substring(item.length() - 1)) == fileID) {
						reading = true;
					}
				} else if (fileID == -1) {
					reading = true;
				}
			}
		}
		return itemContent;
	}
}
