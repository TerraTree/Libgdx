package com.RPGproject.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ObjectMap;

public enum Textures {
    PIC1("button1.png"),
    PIC2("button2.png");


    private String filename = null;

    Textures(String filename) {
        this.filename = filename;
    }

    public Texture get() {
        if (filename != null) {
            return new Texture(filename);
        } else {
            throw new IllegalArgumentException("No filename for this texture.");
        }
    }
    public static ObjectMap<Textures, Texture> loadAllTextures() {
        ObjectMap<Textures, Texture> textureMap = new ObjectMap<Textures, Texture>();
        for (Textures t : Textures.values()) {
            textureMap.put(t, t.get());
        }
        return textureMap;
    }
}
