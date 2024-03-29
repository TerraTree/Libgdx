package com.RPGproject.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

public class Textbox {
    private int rows;
    private int y;
    private int x;
    private int percent;
    private float fontSize;
    private Selector textPointer;
    private ArrayList<String> textContent = new ArrayList<>();

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public ArrayList<String> getTextContent() {
        return textContent;
    }

    public void setTextContent() {
        clear();
        //this.textContent = new ArrayList<>();
        if (this.textContent.size()==0){
            textContent.add("");
            this.setPercent(1);
            this.setRows(1);
        }
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
    	this.x=x;
    }

    public Selector getTextPointer() {
        return textPointer;
    }

    public void setTextPointer(Selector textPointer) {
        this.textPointer = textPointer;
    }

    public Textbox(int rows, int y, int x, float fontSize) {
        this.rows = rows;
        this.y = y;
        this.x=x;
        this.fontSize = fontSize;
        Texture texture = new Texture("textPointer.png");
        this.textPointer = new Selector(this.getX(),this.getY()-28,0,0,2);
    }

    public void clear(){
        for (int i = this.textContent.size(); i > 0; i--) {
            this.textContent.remove(i-1);
        }
    }

    public void addChar(char character, BitmapFont font){
        String text = this.getTextContent().get(this.getTextContent().size()-1)+character;
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font,text);
        if (layout.width > Gdx.graphics.getWidth()/3){
            this.setRows(this.getRows()+1);
            this.setPercent(this.getPercent()+1);
            String charString = ""+character;
            this.getTextContent().add(charString);
        }
        else{
            this.getTextContent().set(this.getTextContent().size()-1,text);
        }
    }

    public void removeChar(){
        String text = this.getTextContent().get(0);
        text = text.substring(0,text.length()-1);
        this.getTextContent().set(0,text);
    }

    public String input(Textbox inputText){
        String inputtedText = "";
        for (String t:inputText.getTextContent()) {
            inputtedText+=t;
            System.out.println(inputtedText);
            this.getTextContent().add(t);
        }
        inputText.setTextContent();
        return inputtedText;
    }

    public void input(String string){
        this.getTextContent().add(string);
    }

    public int drawText(SpriteBatch batch, ShapeRenderer sr, BitmapFont font, int counter, int countOffset){
        this.checkText();
        batch.begin();
        int startInt = Math.max(0,countOffset+this.getPercent()-this.getRows());
        for(int i = startInt;i<startInt+Math.min(this.textContent.size(),this.getRows());i++){
            if (counter>6-countOffset){
                break;
            }
            else {
            	//font.draw(batch,this.getTextContent().get(i),
                font.draw(batch, this.getTextContent().get(i), this.getX(), this.getY() - counter * 15 * fontSize);
            }
            counter++;
        }
        if(textPointer.active){
            textPointer.render(sr,batch);
        }
        batch.end();
        return counter;
    }

    public void checkText(){
        if (this.getTextContent().size()>100){
            for (int i = this.getTextContent().size()-101; i >= 0; i--) {
                this.getTextContent().remove(i);
            }
        }
    }

}
