package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameScreen extends ScreenBeta {
    ActorBeta backgroundImage;
    Player player;
    Enemy enemy;
    Skin neonSkin;
    ActorBeta playerHealthBack;
    ActorBeta playerHealthFront;
    ActorBeta enemyHealthBack;
    ActorBeta enemyHealthFront;
    ActorBeta ground;

    Button moveRightButton;
    ActorBeta rightButtonTxt;
    Button moveLeftButton;
    ActorBeta leftButtonTxt;
    Button jumpButton;
    ActorBeta jumpButtonTxt;
    Button crouchButton;
    ActorBeta crouchButtonTxt;
    Button punchButton;
    ActorBeta punchButtonTxt;
    Button blockButton;
    ActorBeta blockButtonTxt;

    Label timerLabel;
    float timeLeft;

    GameOverScreen gameOverScreen;
    WinScreen winScreen;

    Sound fightSound;
    Sound loseSound;
    Sound winSound;
    Sound doDamage;
    Sound takeDamage;

    static float WIDTH = Gdx.graphics.getWidth();
    static float HEIGHT = Gdx.graphics.getHeight();

    String[] playerPunch = {"playerSprites/tile038.png", "playerSprites/tile037.png", "playerSprites/tile039.png", "playerSprites/tile037.png"};


    @Override
    public void initialize() {
        CreateLevel();
    }

    @Override
    public void update(float dt) {

        timeLeft -= dt;
        timerLabel.setText(Float.toString(Math.round(timeLeft)).substring(0, 3));


        if (moveRightButton.isPressed()) {
            player.moveBy(300 * Gdx.graphics.getDeltaTime(), 0);
            String[] playerWalk = {"playerSprites/tile029.png", "playerSprites/tile030.png", "playerSprites/tile031.png",
                    "playerSprites/tile032.png", "playerSprites/tile033.png", "playerSprites/tile034.png"};
            player.setAnimation(player.loadAnimationFromFiles(playerWalk, 0.3f, true), Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 4);
        }
        else if (moveLeftButton.isPressed()) {
            player.moveBy(-300 * Gdx.graphics.getDeltaTime(), 0);
            String[] playerWalkBackward = {"playerSprites/tile034.png", "playerSprites/tile033.png", "playerSprites/tile032.png",
                     "playerSprites/tile031.png", "playerSprites/tile030.png", "playerSprites/tile029.png"};
            player.setAnimation(player.loadAnimationFromFiles(playerWalkBackward, 0.3f, true), Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 4);
        }
        else if(jumpButton.isPressed()) {
            player.jump();
        }
        else if(crouchButton.isPressed()){
            player.crouch();
        }
        else if(punchButton.isPressed()) {
           // player.setAnimation(player.loadAnimationFromFiles(playerPunch, 0.2f, true), player.getWidth(), player.getHeight());
            player.punch();
            DamageEnemy();
        }
        else if(blockButton.isPressed()){
            player.block();
        }
        else {
            String[] playerIdle = {"playerSprites/tile008.png", "playerSprites/tile009.png", "playerSprites/tile010.png"};
            //String[] test = {"flappybird_1.png"};
            player.setAnimation(player.loadAnimationFromFiles(playerIdle, 0.3f, true), Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 4);
            player.isBlocking = false;
        }

        CheckPlayerX();

        CheckCollisionWithAi();

        CheckGroundCollision();

        CheckTime();


       /* if (moveLeftButton.isPressed()) {
            player.moveBy(-300 * Gdx.graphics.getDeltaTime(), 0);
            String[] playerWalk = {"playerSprites/tile029.png", "playerSprites/tile030.png", "playerSprites/tile031.png",
                    "playerSprites/tile032.png", "playerSprites/tile033.png", "playerSprites/tile034.png"};
            player.setAnimation(player.loadAnimationFromFiles(playerWalk, 0.3f, true));
        } else {
            String[] playerIdle = {"playerSprites/tile008.png", "playerSprites/tile009.png", "playerSprites/tile010.png"};
            player.setAnimation(player.loadAnimationFromFiles(playerIdle, 0.3f, true));
        }*/
    }

    void CreateLevel(){
        neonSkin = new Skin(Gdx.files.internal("neon/skin/neon-ui.json"));
        ActorBeta.setWorldBounds(WIDTH, HEIGHT);

        backgroundImage = new ActorBeta();
        backgroundImage.loadTexture("backgroundImage.jpg");
        backgroundImage.setSize(WIDTH, HEIGHT);
        backgroundImage.setPosition(0, 0);
        mainStage.addActor(backgroundImage);


        // player
        player = new Player();
        player.setPosition(WIDTH / 5, HEIGHT / 5);
        mainStage.addActor(player);

        playerHealthBack = new ActorBeta();
        playerHealthBack.loadTexture("HealthBack.png");
        playerHealthBack.setPosition(WIDTH/WIDTH, HEIGHT - HEIGHT / 14);
        mainStage.addActor(playerHealthBack);

        playerHealthFront = new ActorBeta();
        playerHealthFront.loadTexture("HealthFront.png");
        playerHealthFront.setPosition(WIDTH/WIDTH, HEIGHT - HEIGHT / 14);
        mainStage.addActor(playerHealthFront);


        // enemy
        enemy = new Enemy();
        enemy.loadTexture("enemySprites/tile017.png");
        // enemy.setScale(8);
        enemy.setPosition(WIDTH - WIDTH / 3, HEIGHT / 5);
        mainStage.addActor(enemy);

        enemyHealthBack = new ActorBeta();
        enemyHealthBack.loadTexture("HealthBack.png");
        enemyHealthBack.setPosition(WIDTH - enemyHealthBack.getWidth(), HEIGHT - HEIGHT / 14);
        mainStage.addActor(enemyHealthBack);

        enemyHealthFront = new ActorBeta();
        enemyHealthFront.loadTexture("HealthFront.png");
        enemyHealthFront.setPosition(WIDTH - enemyHealthBack.getWidth(), HEIGHT - HEIGHT / 14);
        mainStage.addActor(enemyHealthFront);


        // ground
        ground = new ActorBeta();
        ground.loadTexture("ground.png");
        ground.setScale(4);
        ground.setPosition(0, HEIGHT / 3);
        ground.setColor(0, 0, 0, 0);
        ground.setBoundaryRectangle();
        //mainStage.addActor(ground);


        //buttons
        moveRightButton = new Button(neonSkin);
        rightButtonTxt = new ActorBeta();
        rightButtonTxt.loadTexture("rightButton.png");
        rightButtonTxt.setScale(3);
        moveRightButton.add(rightButtonTxt);
        moveRightButton.setPosition(WIDTH / 15, HEIGHT / 12);
        mainStage.addActor(moveRightButton);

        moveLeftButton = new Button(neonSkin);
        leftButtonTxt = new ActorBeta();
        leftButtonTxt.loadTexture("leftButton.png");
        leftButtonTxt.setScale(3);
        moveLeftButton.add(leftButtonTxt);
        moveLeftButton.setPosition(WIDTH / WIDTH, HEIGHT / 12);
        mainStage.addActor(moveLeftButton);

        jumpButton = new Button(neonSkin);
        jumpButtonTxt = new ActorBeta();
        jumpButtonTxt.loadTexture("upButton.png");
        jumpButtonTxt.setScale(3);
        jumpButton.add(jumpButtonTxt);
        jumpButton.setPosition(moveRightButton.getX() / 2, HEIGHT / 8);
        mainStage.addActor(jumpButton);

        crouchButton = new Button(neonSkin);
        crouchButtonTxt = new ActorBeta();
        crouchButtonTxt.loadTexture("downButton.png");
        crouchButtonTxt.setScale(3);
        crouchButton.add(crouchButtonTxt);
        crouchButton.setPosition(moveRightButton.getX() / 2, jumpButton.getY() - moveLeftButton.getY());
        mainStage.addActor(crouchButton);

        punchButton = new Button(neonSkin);
        punchButtonTxt = new ActorBeta();
        punchButtonTxt.loadTexture("attackButton.png");
        punchButtonTxt.setScale(4);
        punchButton.add(punchButtonTxt);
        punchButton.setPosition(WIDTH - WIDTH / 16, HEIGHT / 7);
        mainStage.addActor(punchButton);

        blockButton = new Button(neonSkin);
        blockButtonTxt = new ActorBeta();
        blockButtonTxt.loadTexture("blockButton.png");
        blockButtonTxt.setScale(4);
        blockButton.add(blockButtonTxt);
        blockButton.setPosition(WIDTH - WIDTH / 9, HEIGHT / 12);
        mainStage.addActor(blockButton);

        //timer
        timerLabel = new Label("180", neonSkin);
        timerLabel.setFontScale(3);
        timerLabel.setPosition(WIDTH / 2, HEIGHT - HEIGHT / 14);
        timeLeft = 180;
        mainStage.addActor(timerLabel);

        gameOverScreen = new GameOverScreen();
        winScreen = new WinScreen();

        fightSound =  Gdx.audio.newSound(Gdx.files.internal("fight.wav"));
        winSound =  Gdx.audio.newSound(Gdx.files.internal("winSFX.wav"));
        loseSound =  Gdx.audio.newSound(Gdx.files.internal("loseSFX.wav"));
        doDamage =  Gdx.audio.newSound(Gdx.files.internal("doDamage.wav"));
        takeDamage =  Gdx.audio.newSound(Gdx.files.internal("takeDamage.wav"));


    }

    void CheckPlayerX()
    {
        if(player.getX() >= enemy.getX())
            player.setX(enemy.getX());
    }

    void CheckCollisionWithAi()
    {
        if (enemy.enemyState.equals(Enemy.EnemyEnum.Punch) && !player.isBlocking)
        {
            if (player.getX() + player.getWidth() > enemy.getX() + enemy.getWidth() / 2 + enemy.getWidth() / 4) {
                player.moveBy(-1000, 0);
                // player.accelerateAtAngle(-180);
                TakeDamage();
            }
        }
    }

    void TakeDamage()
    {
        if(player.playerHealth != 0) {
            player.playerHealth -= 50;
            playerHealthFront.moveBy(-142f, 0);
            takeDamage.play();
        } else {
            MyGame.setActiveScreen(gameOverScreen);
            loseSound.play();
        }
    }

   void CheckGroundCollision()
    {
        if(player.getY() >= ground.getY())
        {
            player.setY(ground.getY());
        }
    }

    void DamageEnemy(){
        if (enemy.enemyHealth >= 0) {

            if (player.getX() + player.getWidth() >= enemy.getX() + enemy.getWidth() / 2) {
                enemy.enemyHealth -= 1;
                enemyHealthFront.moveBy(2.84f, 0);
                doDamage.play();
            }
        } else {
            MyGame.setActiveScreen(winScreen);
            winSound.play();
        }
    }

    void CheckTime(){
        if(timeLeft <= 0){
            if(enemy.enemyHealth > player.playerHealth) {
                MyGame.setActiveScreen(gameOverScreen);
                loseSound.play();
            }
            else {
                MyGame.setActiveScreen(winScreen);
                winSound.play();
            }
        }
    }
}
