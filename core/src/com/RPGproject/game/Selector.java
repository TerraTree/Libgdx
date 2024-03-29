package com.RPGproject.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Selector {
	
	private int x;
	private int y;
	private int width;
	private int height;
	int xOffset;
	int yOffset;
	int counter;
	boolean active;
	int type;
	Sprite sprite;
	
	
	public Selector(int xPos,int yPos,int width,int height,int type) {
		if(type==1) {
			this.setWidth(width);
			this.setHeight(height);
			active = true;
		}
		else if(type == 2){
			Texture texture = new Texture("textPointer.png");
			sprite = new Sprite(texture);
			active = false;
			sprite.setX(xPos-36);
			sprite.setY(yPos);
		}
		counter = 0;
		this.type = type;
		this.setX(xPos);
		this.setY(yPos);
		this.yOffset = 0;
		this.xOffset = 0;
	}



	public int getHeight() {
		return height;
	}



	public void setHeight(int height) {
		this.height = height;
	}



	public int getWidth() {
		return width;
	}



	public void setWidth(int width) {
		this.width = width;
	}



	public int getY() {
		return y;
	}



	public void setY(int y) {
		this.y = y;
	}



	public int getX() {
		return x;
	}



	public void setX(int x) {
		this.x = x;
	}

	public void render(ShapeRenderer sr, SpriteBatch batch) {
		if(active) {
			if (counter<30) {
				if(type==1) { //tile selector
					sr.setColor(Color.RED);
					int totalX = x + xOffset;
					int totalY = y + yOffset;
					sr.begin(ShapeRenderer.ShapeType.Filled);
					sr.rect(totalX - 5, totalY, 5, height);
					sr.rect(totalX + width, totalY, 5, height);
					sr.rect(totalX, totalY - 5, width, 5);
					sr.rect(totalX, totalY + height, width, 5);
					sr.end();
				}
				else if(type == 2){
					sprite.draw(batch);
				}
				counter++;
			}
			
			else if(counter>50) {
				counter=0;
			}
			else {
				counter++;
			}
		}
	}
}
