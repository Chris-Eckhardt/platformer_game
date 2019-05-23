package game.gameobjects.enemies;

import game.TileMap.TileMap;
import game.gameobjects.GameObject;
import game.gameobjects.Hero;

// The purpose of this abstract class is so I can continue building onto this game after
// the semester is over, I will probably add more enemies and maybe a boss.

public abstract class Enemy extends GameObject {

    private int health,
            flinchCount,
            sightRange,
            attackRange,
            attackDamage,
            delayCount;

    private boolean alive,
            flinching,
            attacking,
            delay;

    Enemy(TileMap tileMap) {
        super(tileMap);
        alive = true;
        sightRange = 200;
        attackRange = 30;
        attackDamage = 1;
    }

    public void hit(int damage) {
        if(!alive || flinching) return;
        health -= damage;
        if(health <= 0) alive = false;
        flinching = true;
        flinchCount = 0;
    }

    private boolean rangeCheck(Hero hero, boolean right) {

        if(right) {
            return hero.getX() > this.getX()
                    && hero.getX() < this.getX() + attackRange
                    && hero.getY() > this.getY() - this.getHeight() / 2f
                    && hero.getY() < this.getY() + this.getHeight() / 2f;
        } else {
            return hero.getX() < this.getX()
                    && hero.getX() > this.getX() - attackRange
                    && hero.getY() > this.getY() - this.getHeight() / 2f
                    && hero.getY() < this.getY() + this.getHeight() / 2f;
        }
    }

    public void checkAttack(Hero hero) {

            if (rangeCheck(hero, facingRight)) {
                this.attacking = true;
                    hero.hit(attackDamage);
                    delay = true;
                    delayCount = 0;
            }
    }

    public void checkSight(Hero hero) {

        if(hero.getX() > this.getX()
                && hero.getX() < this.getX() + sightRange
                && hero.getY() > this.getY() - this.getHeight() / 2f
                && hero.getY() < this.getY() + this.getHeight() / 2f)
        {
            this.setRight(true);
            facingRight = true;
        }

        if(hero.getX() < this.getX()
                && hero.getX() > this.getX() - sightRange
                && hero.getY() > this.getY() - this.getHeight() / 2f
                && hero.getY() < this.getY() + this.getHeight() / 2f)
        {
            this.setLeft(true);
            facingRight = false;
        }

    }

    public void checkBounds(Hero hero) {
        if( (this.getX() + this.getCwidth()) >= hero.getX()) {
            setRight(false);
        }
        if( this.getX() <= (hero.getX() + hero.getCwidth()) ) {
            setLeft(false);
        }
    }

    public boolean isAlive() { return alive; }

    void setAlive(boolean alive) {
        this.alive = alive;
    }

    boolean isFlinching() { return flinching; }

    void setFlinching(boolean flinching) { this.flinching = flinching; }

    void setFlinchCount(int flinchCount) { this.flinchCount = flinchCount; }

    int getFlinchCount() { return flinchCount; }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    void setAttacking(boolean b) { attacking = b; }

    boolean getAttacking() { return attacking; }

    void setDelaying(boolean b) { this.delay = b; }

    boolean isDelaying() { return delay; }

    void setDelayCount(int count) { this.delayCount = count; }

    int getDelayCount() { return delayCount; }
}
