package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;



public class StartScreen extends ScreenBeta {
    public DialogScreen dialogScreen;
    Skin neonSkin;
    Button startButton;
    ActorBeta startButtonTxt;
    Button exitButton;
    ActorBeta exitButtonTxt;
    ActorBeta background;

    Music BGmusic;
    Sound clickSFX;

    @Override
    public void initialize() {
        dialogScreen = new DialogScreen();
        neonSkin = new Skin(Gdx.files.internal("neon/skin/neon-ui.json"));
        background = new ActorBeta();
        background.loadTexture("menuBackground.png");
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mainStage.addActor(background);

        startButtonTxt = new ActorBeta();
        startButtonTxt.loadTexture("start.png");
       startButton = new Button(neonSkin);
       startButton.add(startButtonTxt);
       startButton.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
       mainStage.addActor(startButton);

        exitButtonTxt = new ActorBeta();
        exitButtonTxt.loadTexture("exit.png");
        exitButton = new Button(neonSkin);
        exitButton.add(exitButtonTxt);
        exitButton.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 4);
        exitButtonTxt.setSize(startButtonTxt.getWidth(), startButtonTxt.getHeight());
        mainStage.addActor(exitButton);

        BGmusic = Gdx.audio.newMusic(Gdx.files.internal("prodByPoritskyy.mp3"));
        BGmusic.play();
        BGmusic.setLooping(true);

        clickSFX = Gdx.audio.newSound(Gdx.files.internal("click.wav"));

    }

    @Override
    public void update(float dt) {
        if(startButton.isPressed())
        {
            MyGame.setActiveScreen(dialogScreen);
            BGmusic.pause();
            clickSFX.play();
            dialogScreen.bgMusic.play();
        }

        if(exitButton.isPressed())
        {
            clickSFX.play();
            Gdx.app.exit();
        }
    }
}
