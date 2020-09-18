package com.RPGproject.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class UserInterface {
    private Textbox mainText;
    private Textbox inputText;
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
        mainText = new Textbox(6,screenHeight/5,fontSize);
        inputText = new Textbox(1,15 * (int) fontSize,fontSize);
        scrollbar = new Scrollbar(Gdx.graphics.getWidth()*2/3, Gdx.graphics.getWidth()/80, Gdx.graphics.getHeight()/5);
        inputText.setTextContent();
        //mainText.randomText();
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
        counter = this.inputText.drawText(this.getBatch(),this.getFont(),counter,countOffset);
        countOffset = this.inputText.getTextContent().size()-1;
        this.mainText.drawText(batch,font,counter-1,countOffset);
    }
}
