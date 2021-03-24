package com.RPGproject.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

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

	public ArrayList<Item> readEquipFile(Scanner scanner, int fileID) {
		ArrayList<Item> itemContent = new ArrayList<>();
		String item;
		boolean reading = false;
		while (scanner.hasNextLine()) {
			item = scanner.nextLine();
			System.out.println("item:" + item);
			if (reading) {
				if(item.equals("")){
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
				if (itemType.equals("consumable")) {
					duration = Integer.parseInt(item);
					item = scanner.nextLine();
					target = item;
				} else if (itemType.equals("equipment")) {
					equipmentPosition = item;
				}
				item = scanner.nextLine();
				String value = "";
				ArrayList<Integer> stats = new ArrayList<>();
				System.out.println(item);
				for (int i = 0; i < Math.max(0, item.length() - 1); i++) {
					System.out.println(i);
					String chr = String.valueOf(item.charAt(i));
					if (!chr.equals("[")) {
						if (chr.equals(",") || chr.equals("]")) {
							stats.add(Integer.parseInt(value));
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
