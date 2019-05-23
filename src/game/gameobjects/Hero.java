package game.gameobjects;

import game.TileMap.TileMap;
import game.gameobjects.enemies.Enemy;
import game.util.Animation;
import game.util.Audio;
import game.util.HeroImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Hero extends GameObject {

    // Attributes
    private int health, maxHealth, flinchCount, lives;
    private boolean dead, flinching, nextLevel, atLantern;

    // Attack
    private boolean attacking;
    private int attackDamage, attackRange, grabRange;

    // Animations
    private ArrayList<BufferedImage[]> animations;

    // Sound Effects
    private Audio audio;

    // Actions
    private static final int
            IDLE = 0,
            RUNNING = 1,
            JUMPING = 2,
            FALLING = 3,
            ATTACK_1 = 4;


    public Hero(TileMap tileMap) {
        super(tileMap);

        // player attribute values
        setWidth(120);
        setHeight(80);
        setCwidth(30);
        setCheight(30);
        setMoveSpeed(10.0);
        setMaxSpeed(3.5);
        setStopSpeed(0.4);
        setFallSpeed(0.15);
        setMaxFallSpeed(10.0);
        setJumpStart(-12.0);
        setStopJumpSpeed(0.3);
        //player bools (direction)
        facingRight = true;
        nextLevel = false;
        // game stat vals
        lives = 2;
        health = maxHealth = 5;
        attackDamage = 8;
        attackRange = 40;
        grabRange = 25;

        // LOAD ANIMATIONS into arraylist
        animations = new ArrayList<>();
        HeroImageLoader imageLoader = new HeroImageLoader();
        animations.add(imageLoader.loadIdle());
        animations.add(imageLoader.loadRun());
        animations.add(imageLoader.loadJumping());
        animations.add(imageLoader.loadFalling());
        animations.add(imageLoader.loadAttack1());
        //animations.add(imageLoader.loadAttack2());
        //animations.add(imageLoader.loadAttack3());

        // LOAD AUDIO SFX
        audio = new Audio();
        audio.init();
        audio.load("/audio/woosh1.wav", "playerAttack");
        audio.load("/audio/footStep01.wav", "playerLand");
        audio.load("/audio/footstep04.wav", "playerJump");

        // set up animation instance
        setAnimation( new Animation() );
        setCurrentAnimation(IDLE);
        this.getAnimation().setFrames(animations.get(IDLE));
        this.getAnimation().setDelay(200);

    }

    public void hit(int damage) {
        if(dead || flinching) return;
        health -= damage;
        if(health < 0) health = 0;
        if(health == 0) dead = true;
        flinching = true;
        flinchCount = 0;
    }

    private boolean rangeCheck(Enemy enemy, boolean right) {
        if(right) {
            return enemy.getX() > this.getX()
                    && enemy.getX() < this.getX() + attackRange
                    && enemy.getY() > this.getY() - this.getHeight() / 2f
                    && enemy.getY() < this.getY() + this.getHeight() / 2f;
        } else {
            return enemy.getX() < this.getX()
                    && enemy.getX() > this.getX() - attackRange
                    && enemy.getY() > this.getY() - this.getHeight() / 2f
                    && enemy.getY() < this.getY() + this.getHeight() / 2f;
        }
    }

    private boolean grabable(Health health) {

         boolean a = health.getX() > this.getX();
         boolean b = health.getX() < this.getX() + grabRange;
         boolean c = health.getY() > this.getY() - this.getHeight() / 2f;
         boolean d = health.getY() < this.getY() + this.getHeight() / 2f;
         boolean e = health.getX() < this.getX();
         boolean f = health.getX() > this.getX() - grabRange;
         boolean g = health.getY() > this.getY() - this.getHeight() / 2f;
         boolean h = health.getY() < this.getY() + this.getHeight() / 2f;

         boolean opt1 = a && b && c && d;
         boolean opt2 = e && f && g && h;

         return opt1 || opt2;
    }

    public void checkCollection(ArrayList<Health> list) {
        for(int i = 0; i < list.size(); i++) {
            if(grabable(list.get(i))) {
                list.get(i).collect();
                health++;
                if(health > maxHealth) {
                    health = maxHealth;
                }
            }
        }
    }

    public void checkAttack(ArrayList<Enemy> enemies) {

        if(attacking) {

            for(int i = 0; i < enemies.size(); i++) {
                Enemy enemy = enemies.get(i);

                if(rangeCheck(enemy, facingRight)) {
                        enemy.hit(attackDamage);
                }
            }
        }
    }


    private void getNextPosition() {

        if(isLeft()) {
            setDX(getDX() - getMaxSpeed());
            if(getDX() < -getMaxSpeed()) {
                setDX(-getMaxSpeed());
            }
        } else if(isRight()) {
                setDX(getDX() + getMaxSpeed());
                if(getDX() > getMaxSpeed()) {
                    setDX(getMaxSpeed());
            }
        } else {
            if(getDX() > 0) {
                setDX(getDX() - getStopSpeed());
                if(getDX() < 0) {
                    setDX(0);
                }
            } else if(getDX() < 0) {
                setDX(getDX() + getStopSpeed());
                if(getDX() > 0) {
                    setDX(0);
                }
            }
        }

        // cannot attack while moving

        if(getCurrentAnimation() == ATTACK_1 && !(isJumping() || isFalling())) {
            setDX(0);
        }

        if(isJumping() && !isFalling()) {
            setDY(getJumpStart());
            setFalling(true);
        }

        if(isFalling()) {
            if(getDY() >= 0) setDY(getDY() + getFallSpeed());
            if(getDY() < 0) setDY(getDY() + getStopSpeed());
            if(getDY() > getMaxFallSpeed()) setDY(getMaxFallSpeed());
        }


    }

    @Override
    public void update() {

        if(flinching) {
            flinchCount++;
            if(flinchCount == 50) flinching = false;
        }
        // get new player position
        getNextPosition();

        // System.out.println(getXtemp() + ", " + getYtemp()); // FOR DEBUGGING BOUNDS
        // check for death
        if(getYtemp() >= 610) {
            dead = true;

        } else if (getXtemp() >= 2024) {
            nextLevel = true;

        } else {

            checkTileMapCollision();
            setPosition(getXtemp(), getYtemp());

        }

        // this check is for level 3, it effects nothing else
        if(getXtemp() >= 1830) {
            atLantern = true;
        } else { atLantern = false; }

        //check if attack has stopped
        if(getCurrentAnimation() == ATTACK_1) {
            if(this.getAnimation().hasPlayedOnce()) {
                attacking = false;
            }
        }

        // set animation
        if(attacking) {
            if (getCurrentAnimation() != ATTACK_1) {
                setCurrentAnimation(ATTACK_1);
                this.getAnimation().setFrames(animations.get(ATTACK_1));
                this.getAnimation().setDelay(75);
                setWidth(120);
                setHeight(80);
                audio.play("playerAttack");
            }
        }

        else if (getDY() > 0) {
            if(getCurrentAnimation() != FALLING) {
                setCurrentAnimation(FALLING);
                this.getAnimation().setFrames(animations.get(FALLING));
                this.getAnimation().setDelay(50);
                setWidth(120);
                setHeight(80);
            }
        }

        else if (getDY() < 0) {
            if(getCurrentAnimation() != JUMPING) {
                setCurrentAnimation(JUMPING);
                this.getAnimation().setFrames(animations.get(JUMPING));
                this.getAnimation().setDelay(100);
                setWidth(120);
                setHeight(80);
                audio.play("playerJump");
            }
        }

        else if (isLeft() || isRight()) {
            if(getCurrentAnimation() != RUNNING) {
                setCurrentAnimation(RUNNING);
                this.getAnimation().setFrames(animations.get(RUNNING));
                this.getAnimation().setDelay(75);
                setWidth(120);
                setHeight(80);
            }
        }

        else {
            if(getCurrentAnimation() != IDLE) {
                setCurrentAnimation(IDLE);
                this.getAnimation().setFrames(animations.get(IDLE));
                this.getAnimation().setDelay(200);
                setWidth(120);
                setHeight(80);
            }
        }

        this.getAnimation().update();

        if(getCurrentAnimation() != ATTACK_1) {
            if(isRight()) facingRight = true;
            if(isLeft()) facingRight = false;
        }

    }

    @Override
    public void draw(Graphics2D g2d) {
        setMapPosition();

        // draw sprite to screen
        super.draw(g2d);

    }

    ///////// GETTERS && SETTERS ////////
    public boolean isDead() { return dead; }
    public boolean nextLevel() { return nextLevel; }
    public int getHealth() { return health; }
    public void setAttacking() { attacking = true; }
    public void setHealth(int health) { this.health = health; }
    public void loseLife() { lives--; }
    public boolean gameOver() { return lives < 0; }
    public void setLives(int lives) { this.lives = lives; }
    public int getLives() { return lives; }
    public void setDead(boolean b) { dead = b; }
    public boolean atLantern() {return atLantern; }

}
