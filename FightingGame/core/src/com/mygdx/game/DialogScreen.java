package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class DialogScreen extends ScreenBeta {
    ActorBeta backgroundImage;
    ActorBeta player;
    ActorBeta enemy;
    ActorBeta dialogUI;
    Skin neonSkin;
    Label dialog;
    int numOfTouches;
    GameScreen gameScreen;
    Music bgMusic;

    static float WIDTH = Gdx.graphics.getWidth();
    static float HEIGHT = Gdx.graphics.getHeight();

    @Override
    public void initialize() {
        neonSkin = new Skin(Gdx.files.internal("neon/skin/neon-ui.json"));

        backgroundImage = new ActorBeta();
        backgroundImage.loadTexture("backgroundImage.jpg");
        backgroundImage.setSize(WIDTH, HEIGHT);
        backgroundImage.setPosition(0, 0);
        mainStage.addActor(backgroundImage);

        player = new ActorBeta();
        player.loadTexture("playerSprites/tile000.png");
        player.setScale(8);
        player.setPosition(WIDTH / 5, HEIGHT / 6);
        mainStage.addActor(player);

        enemy = new ActorBeta();
        enemy.loadTexture("enemySprites/tile017.png");
        enemy.setScale(8);
        enemy.setPosition(WIDTH - WIDTH / 3, HEIGHT / 6);
        mainStage.addActor(enemy);

        dialogUI = new ActorBeta();
        dialogUI.loadTexture("dialogUInew.png");
        dialogUI.setSize(WIDTH, HEIGHT / 8);
        dialogUI.setPosition(0, HEIGHT - HEIGHT / 8);
        mainStage.addActor(dialogUI);

        dialog = new Label("Touch to begin..", neonSkin);
        dialog.setPosition(WIDTH / WIDTH, HEIGHT - HEIGHT / 10);
        dialog.setFontScale(6);
        mainStage.addActor(dialog);

        numOfTouches = 0;
        gameScreen = new GameScreen();

        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("prodByPoritskyy2.mp3"));
        //bgMusic.play();
        bgMusic.setLooping(true);

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(numOfTouches == 0) {
            dialog.setText("Your skin will make fine leather.");
            numOfTouches++;
            enemy.moveBy(-50, 0);
        } else if (numOfTouches == 1) {
            dialog.setText("It better, I paid enough for it.");
            numOfTouches++;
            enemy.moveBy(50, 0);
            player.moveBy(50, 0);
        } else if (numOfTouches == 2) {
            dialog.setText("I'll enjoy wearing you.");
            enemy.moveBy(-50, 0);
            player.moveBy(-50, 0);
            numOfTouches++;
        } else if (numOfTouches == 3) {
            dialog.setText("Where are your weapons?");
            //dialog.setFontScale(4);
            enemy.moveBy(50, 0);
            player.moveBy(50, 0);
            numOfTouches++;
        } else if (numOfTouches == 4) {
            dialog.setText("My body is my weapon, stoopid.");
            //dialog.setFontScale(4);
            enemy.moveBy(-50, 0);
            player.moveBy(-50, 0);
            numOfTouches++;
        } else if (numOfTouches == 5) {
            dialog.setText("Ha! Then you fight unarmed.");
            enemy.moveBy(50, 0);
            player.moveBy(50, 0);
            numOfTouches++;
        } else if (numOfTouches == 6) {
            MyGame.setActiveScreen(gameScreen);
            gameScreen.fightSound.play();
        }



        return super.touchDown(screenX, screenY, pointer, button);
    }
}
