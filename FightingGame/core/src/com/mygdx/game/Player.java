package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;

import sun.rmi.runtime.Log;

public class Player extends ActorBeta {
    public int playerHealth = 100;
    public boolean isBlocking;

    String[] playerJump = {"playerSprites/tile051.png"};
    String[] playerCrouch = {"playerSprites/tile017.png"};
    String[] playerPunch = {"playerSprites/tile038.png", "playerSprites/tile037.png", "playerSprites/tile039.png", "playerSprites/tile037.png"};
    String[] playerBlock = {"playerSprites/tile083.png"};

    Player()
    {
        this.setBoundaryRectangle();
    }

    @Override
    public void act(float dt) {
        super.act(dt);

        setAcceleration(800);
        accelerateAtAngle(270);
        applyPhysics(dt);


        boundToWorld();
    }

    public void jump() {
        setSpeed(800);
        setMotionAngle(90);

        this.setAnimation(loadAnimationFromFiles(playerJump, 0.5f, false),  this.getWidth(), this.getHeight());
    }

    public void crouch(){

        this.setAnimation(loadAnimationFromFiles(playerCrouch, 0.5f, false),  this.getWidth(), this.getHeight());
    }

    public void punch(){
        this.setAnimation(loadAnimationFromFiles(playerPunch, 0.2f, true), this.getWidth(), this.getHeight());

    }

    public void block(){
        this.setAnimation(loadAnimationFromFiles(playerBlock, 0.5f, true), this.getWidth(), this.getHeight());
        isBlocking = true;
    }
}
