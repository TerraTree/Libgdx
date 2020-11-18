package com.RPGproject.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.RPGproject.game.Main;

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.fullscreen = false;
        config.foregroundFPS = 60;
        config.backgroundFPS = 60;
        config.width = 1920;
        config.height=1080;
        new LwjglApplication(new Main(), config);
    }
}
