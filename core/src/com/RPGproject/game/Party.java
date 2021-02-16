package com.RPGproject.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public class Party {
	private int money;
	private Character char1;
	private Character char2;
	private ArrayList<String> items;
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
	public ArrayList<String> getItems() {
		return items;
	}
	public void setItems(ArrayList<String> items) {
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
}
