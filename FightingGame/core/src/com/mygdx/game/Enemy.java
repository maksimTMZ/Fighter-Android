package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


import java.util.Random;

public class Enemy extends ActorBeta {

    public int enemyHealth = 100;

    String[] enemyIdle = {"enemySprites/tile008.png", "enemySprites/tile009.png", "enemySprites/tile017.png"};
    String[] enemyPunch = {"enemySprites/tile052.png", "enemySprites/tile051.png"};
    String[] enemyWalkFront = {"enemySprites/tile002.png", "enemySprites/tile003.png", "enemySprites/tile004.png", "enemySprites/tile005.png"};
    String[] enemyWalkBack = {"enemySprites/tile005.png", "enemySprites/tile004.png", "enemySprites/tile003.png", "enemySprites/tile002.png"};
    String[] enemyDeath = {"enemySprites/tile062.png", "enemySprites/tile061.png", "enemySprites/tile064.png"};

    Animation<TextureRegion> idleAnim;
    Animation<TextureRegion> punchAnim;
    Animation<TextureRegion> walkFrontAnim;
    Animation<TextureRegion> walkBackAnim;
    Animation<TextureRegion> deathAnim;

    enum EnemyEnum
    {
        Idle,
        Punch,
        WalkFront,
        WalkBack,
        Death
    }

    public EnemyEnum enemyState;

    boolean Roll = true;
    public boolean isAlive;
    int rollResult;
    float aiTimer;

    static float WIDTH = Gdx.graphics.getWidth();
    static float HEIGHT = Gdx.graphics.getHeight();

    Enemy()
    {
        setMotionAngle(180);

        idleAnim = loadAnimationFromFiles(enemyIdle, 0.5f, true);
        punchAnim = loadAnimationFromFiles(enemyPunch, 0.2f, true);
        walkFrontAnim = loadAnimationFromFiles(enemyWalkFront, 0.2f, true);
        walkBackAnim = loadAnimationFromFiles(enemyWalkBack, 0.2f, true);
        deathAnim = loadAnimationFromFiles(enemyDeath, 1f, false);

        enemyState = EnemyEnum.Idle;
       // this.setAnimation(loadAnimationFromFiles(enemyIdle, 0.3f, true), Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 4);

        isAlive = true;
        this.setBoundaryRectangle();




    }

    @Override
    public void act(float dt) {
        super.act(dt);

        setAcceleration(3000);
        accelerateAtAngle(270);
        applyPhysics(dt);
        boundToWorld();

        switch (enemyState)
        {
            case Idle:
                Idle();
                break;

            case Punch:
                Punch();
                break;

            case WalkFront:
                WalkFront();
                break;

            case WalkBack:
                WalkBack();
                break;

            case Death:
                Death();
                break;

                default:
                    break;
        }

        aiTimer -= dt;
        if(aiTimer <= 0 && enemyState != EnemyEnum.Death)
        {
            Random randomInt = new Random();
            rollResult = randomInt.nextInt(4);
            aiTimer = 2f;
        }

        if(rollResult == 0)
            enemyState = EnemyEnum.Idle;

        if(rollResult == 1)
        {
            enemyState = EnemyEnum.WalkFront;
        }

        if(rollResult == 2)
        {
            enemyState = EnemyEnum.WalkBack;
        }

        if(rollResult == 3)
        {
            enemyState = EnemyEnum.Punch;
        }
    }

    void Idle()
    {
        this.setAnimation(idleAnim, Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 4);
    }

    void Punch()
    {

        this.setAnimation(punchAnim, Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 4);
    }

    void WalkFront()
    {
        moveBy(-2, 0);
        this.setAnimation(walkFrontAnim, Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 4);
    }

    void WalkBack()
    {
        moveBy(2, 0);
        this.setAnimation(walkBackAnim, Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 4);
    }

    void Death()
    {
        this.setAnimation(deathAnim, Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 4);
    }
}
