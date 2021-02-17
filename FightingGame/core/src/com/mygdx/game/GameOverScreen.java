package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameOverScreen extends ScreenBeta {
    ActorBeta background;
    Skin neonSkin;
    ActorBeta restartButtonTxt;
    Button restartButton;

    Sound clickSound;

    @Override
    public void initialize() {
        background = new ActorBeta();
        background.loadTexture("gameOver.png");
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mainStage.addActor(background);

        neonSkin = new Skin(Gdx.files.internal("neon/skin/neon-ui.json"));

        restartButtonTxt = new ActorBeta();
        restartButtonTxt.loadTexture("restart button.png");
        restartButton = new Button(neonSkin);
        restartButton.add(restartButtonTxt);
        restartButton.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 22);
        mainStage.addActor(restartButton);

        clickSound = Gdx.audio.newSound(Gdx.files.internal("click.wav"));

    }

    @Override
    public void update(float dt) {
        if(restartButton.isPressed()) {
            clickSound.play();
            MyGame.setActiveScreen(new DialogScreen());
        }
    }
}
