package com.RPGproject.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Scrollbar {
    private int x;
    private int y;
    private int height;
    private int width;
    private int maxHeight;
    private int yOffset;
    private boolean scrolling;

    public Scrollbar(int x, int width, int maxHeight) {
        this.x = x;
        this.width = width;
        this.maxHeight = maxHeight;
        this.setHeight(-Gdx.graphics.getHeight()/5);
        this.setY(0);
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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

	public int getYOffset() {
		return yOffset;
	}

	public void setYOffset(int yOffset) {
		this.yOffset = yOffset;
	}
    
	public boolean isScrolling() {
		return scrolling;
	}

	public void setScrolling(boolean scrolling) {
		this.scrolling = scrolling;
	}
	
    public void Scrolling(double amount, int rows){
        int multipliedIncrease = (int) (amount*Math.round(this.getMaxHeight()/(double) rows));
        if(this.getY()+this.getHeight()+multipliedIncrease > this.getMaxHeight()){
            this.setY(this.getMaxHeight()-this.getHeight());
        }
        else if(this.getY()+multipliedIncrease < 0){
            this.setY(0);
        }
        else{
            this.setY(this.getY() + multipliedIncrease);
        }
    }
    public void moveScroll(int mouseX,int mouseY){
        if(mouseY>this.getMaxHeight()-this.getHeight()){
            this.setY(this.getMaxHeight()-this.getHeight());
        }
        else if (mouseY<0){
            this.setY(0);
        }

        else {
            this.setY(mouseY);
        }
    }

    public void adjust(Textbox textbox, ShapeRenderer sr){
        double division = textbox.getRows()/ (double) textbox.getTextContent().size();
        textbox.setPercent((int) Math.round((this.getMaxHeight()-this.getY())/ (double) this.getMaxHeight() *  (double)textbox.getTextContent().size()));
        this.setHeight((int) Math.round(division * this.getMaxHeight()));
        sr.setColor(Color.BLUE);
        if(textbox.getTextContent().size()>textbox.getRows()) {
            sr.rect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        }
    }




}
