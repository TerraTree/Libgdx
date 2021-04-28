package com.RPGproject.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class startStage extends Stage {
    private boolean visible = true;

    public startStage(Texture texture, final stageInterface stageInterface){
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        Image image = new Image(stageInterface.getTexture(Textures.PIC1));
        image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
                stageInterface.startGame();
            }
        });
        table.add(image);


        addActor(table);
    }
    public void draw() {
        act(Gdx.graphics.getDeltaTime());
        if (visible) {
            super.draw();
        }
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}
