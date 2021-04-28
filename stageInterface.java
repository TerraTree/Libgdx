package com.RPGproject.game;

import com.badlogic.gdx.graphics.Texture;

public interface stageInterface {
    void startGame();
    void returnToStart();
    Texture getTexture(Textures texture);
}
