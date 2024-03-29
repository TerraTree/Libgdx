package com.RPGproject.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class UserInterface {
    private Textbox mainText;
    private Textbox inputText;
    private Textbox menuText;
    private SpriteBatch batch;
    private BitmapFont font;
    private Scrollbar scrollbar;
    private ShapeRenderer sr;
    private int screenHeight;
    private int screenWidth;

    public UserInterface() {
        sr= new ShapeRenderer(); //Renders Shapes, might be replaced by textures.
        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();
        batch = new SpriteBatch();
        font = new BitmapFont();
        float fontSize = ((screenHeight/5)/ (font.getLineHeight()*6)); //resizes the text to fit the screen size
        font.setColor(Color.WHITE);
        font.getData().setScale(fontSize);
        mainText = new Textbox(6,screenHeight/5,screenWidth/3,fontSize);
        inputText = new Textbox(1,15 * (int) fontSize,screenWidth/3,fontSize);
        menuText = new Textbox(6,screenHeight/5,screenWidth/9,fontSize);
        scrollbar = new Scrollbar(Gdx.graphics.getWidth()*2/3, Gdx.graphics.getWidth()/80, Gdx.graphics.getHeight()/5);
        inputText.setTextContent();
        menuText.input("hello");
    }

    public Textbox getMenuText() {
        return menuText;
    }

    public void setMenuText() {
        if(this.menuText.getTextContent().size()>0){
            for (int i = this.menuText.getTextContent().size()-1; i >= 0; i--) {
                this.menuText.getTextContent().remove(i);
            }
        }
    }

    public Textbox getMainText() {
        return mainText;
    }

    public void setMainText(Textbox mainText) {
        this.mainText = mainText;
    }

    public Textbox getInputText() {
        return inputText;
    }

    public void setInputText(Textbox inputText) {
        this.inputText = inputText;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public BitmapFont getFont() {
        return font;
    }

    public void setFont(BitmapFont font) {
        this.font = font;
    }

    public Scrollbar getScrollbar() {
        return scrollbar;
    }

    public void setScrollbar(Scrollbar scrollbar) {
        this.scrollbar = scrollbar;
    }

    public ShapeRenderer getSr() {
        return sr;
    }

    public void setSr(ShapeRenderer sr) {
        this.sr = sr;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public void render(){
    	
        this.getSr().begin(ShapeRenderer.ShapeType.Filled);
        this.getSr().setColor(Color.GRAY);
        this.getSr().rect(0,0,screenWidth,screenHeight/5);
        this.getScrollbar().adjust(this.getMainText(),this.getSr());
        this.getSr().end();
        int counter=1-(this.inputText.getTextContent().size());
        int countOffset = 0;
        this.menuText.drawText(batch, sr,font, counter, countOffset);
        counter = this.inputText.drawText(batch,sr,font,counter,countOffset);
        countOffset = this.inputText.getTextContent().size()-1;
        this.mainText.drawText(batch,sr,font,counter-1,countOffset);
        
    }
}
