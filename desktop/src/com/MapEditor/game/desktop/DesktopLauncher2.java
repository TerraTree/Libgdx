package com.MapEditor.game.desktop;

import com.MapEditor.game.Main;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher2 {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.fullscreen = true    ;
        config.foregroundFPS = 60;
        config.backgroundFPS = 60;
        config.width = 1920;
        config.height=1080;
        new LwjglApplication(new Main(), config);
    }
}
