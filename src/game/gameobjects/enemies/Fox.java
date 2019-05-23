package game.gameobjects.enemies;

import game.TileMap.TileMap;
import game.util.Animation;
import game.util.EnemyImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Fox extends Enemy {

    private ArrayList<BufferedImage[]> animations;

    // Actions
    private static final int
            IDLE = 0,
            RUNNING = 1,
            ATTACK = 2;

    public Fox(TileMap tileMap) {
        super(tileMap);

        setMoveSpeed(0.5);
        setMaxSpeed(0.7);
        setFallSpeed(0.2);
        setMaxFallSpeed(10.0);

        setWidth(100);
        setHeight(60);

        setCwidth(20);
        setCheight(20);

        setHealth(10);
        facingRight = true;

        // load enemy sprites here
        animations = new ArrayList<>();
        EnemyImageLoader loader = new EnemyImageLoader();
        animations.add(loader.loadEnemyFoxIdle());
        animations.add(loader.loadEnemyFoxWalk());
        animations.add(loader.loadEnemyFoxAttack());

        this.setAnimation( new Animation() );
        setCurrentAnimation(IDLE);
        this.getAnimation().setFrames(animations.get(getCurrentAnimation()));
        this.getAnimation().setDelay(200);
    }

    private void getNextPosition() {

        // left and right movement
        if(isLeft()) {
            setDX(getDX() - getMaxSpeed());
            if(getDX() < -getMaxSpeed()) {
                setDX(-getMaxSpeed());
            } else {
                setLeft(false);
            }
        } else if(isRight()) {
            setDX(getDX() + getMaxSpeed());
            if(getDX() > getMaxSpeed()) {
                setDX(getMaxSpeed());
            } else {
                setRight(false);
            }
        }

        if(isLeft() && getDX() == 0) {
            setRight(true);
        }

        if(isRight() && getDX() == 0) {
            setLeft(true);
        }

        if(getCurrentAnimation() == ATTACK  && !isFalling()) {
            setDX(0);
        }

        // falling
        if(isFalling()) {
            setDY(getDY() + getFallSpeed());
        }

        // else : just chill
    }

    @Override
    public void update() {

        if(isFlinching()) {
            setFlinchCount(getFlinchCount() + 1);
            if(getFlinchCount() == 15) setFlinching(false);
        }

        if(isDelaying()) {
            setDelayCount(getDelayCount() + 1);
            if(getDelayCount() >= 200) {
                setDelaying(false);
            }
        }

        // update position
        getNextPosition();

        if(getYtemp() >= 610 || getXtemp() >= 2024) {
            setAlive(false);
        }

        checkTileMapCollision();

        setPosition(getXtemp(), getYtemp());

        //check if attack has stopped
        if(getCurrentAnimation() == ATTACK) {
            if(this.getAnimation().hasPlayedOnce()) {
                setAttacking(false);
            }
        }

        // set animation
        if(getAttacking()) {
            setLeft(false); setRight(false);
            if (getCurrentAnimation() != ATTACK) {
                setCurrentAnimation(ATTACK);
                this.getAnimation().setFrames(animations.get(ATTACK));
                this.getAnimation().setDelay(75);
                setWidth(100);
                setHeight(60);
            }
        }

        else if (isLeft() || isRight()) {
            if(getCurrentAnimation() != RUNNING) {
                setCurrentAnimation(RUNNING);
                this.getAnimation().setFrames(animations.get(RUNNING));
                this.getAnimation().setDelay(75);
                setWidth(100);
                setHeight(60);
            }
        }

        else {
            if(getCurrentAnimation() != IDLE) {
                setCurrentAnimation(IDLE);
                this.getAnimation().setFrames(animations.get(IDLE));
                this.getAnimation().setDelay(200);
                setWidth(100);
                setHeight(60);
            }
        }

        // set animation

        this.getAnimation().update();

        if(getCurrentAnimation() != ATTACK) {
            if(isRight()) facingRight = true;
            if(isLeft()) facingRight = false;
        }

    }

    @Override
    public void draw(Graphics2D g2d) {
        //if( notOnScreen() ) return;

        setMapPosition();

        super.draw(g2d);
    }

}
