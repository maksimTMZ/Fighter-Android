package com.mygdx.game;

public class MyGame extends GameBeta {
    ScreenBeta startScreen;

    @Override
    public void create() {
        super.create();

        startScreen = new StartScreen();

        setActiveScreen(startScreen);
    }
}
