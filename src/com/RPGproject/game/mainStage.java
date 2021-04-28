package com.RPGproject.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class mainStage extends Stage {
    private boolean visible = false;

    public mainStage(Texture texture, final stageInterface main){
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        Image image = new Image(texture);

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
