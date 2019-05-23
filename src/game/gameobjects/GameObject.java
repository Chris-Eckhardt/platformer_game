package game.gameobjects;

import game.TileMap.TileMap;
import game.util.Animation;

import java.awt.*;

import static game.TileMap.Tile.*;


public abstract class GameObject {

    private TileMap tileMap;
    private int tileSize;
    private double xmap, ymap;

    // position
    private double x, y, dx, dy;

    // Collision & Dimension
    private int height, width;
    private int cwidth, cheight;
    private double xtemp, ytemp;
    private boolean bottomRight, bottomLeft, topRight, topLeft;

    private Animation animation;
    private int currentAnimation;
    protected boolean facingRight;

    private boolean left, right, up, jumping,
            falling;

    private double moveSpeed, maxSpeed, stopSpeed,
            fallSpeed, maxFallSpeed, jumpStart, stopJumpSpeed;


    public GameObject(TileMap tileMap) {
        this.tileMap = tileMap;
        tileSize = tileMap.getTileSize();
    }

    public abstract void update();

    public void draw(Graphics2D g2d) {

        if(facingRight) {

            g2d.drawImage(animation.getImage(),
                    (int) (getX() + getXmap() - getWidth() / 2),
                    (int) (getY() + getYmap() - getHeight() / 2)-6,
                    null);

        } else {

            g2d.drawImage(animation.getImage(),
                    (int) (getX() + getXmap() - getWidth() / 2 + getWidth()),
                    (int) (getY() + getYmap() - getHeight() / 2)-6,
                    -getWidth(),
                    getHeight(),
                    null);

        }

    }

    private void calculateCorners(double x, double y) {
        int leftTile = (int) (x - (cwidth/2)) / tileSize;
        int rightTile = (int) (x + (cwidth/2)-1) / tileSize;
        int topTile = (int) (y - (cwidth/2)) / tileSize;
        int bottomTile = (int) (y + (cwidth/2)-1) / tileSize;

        int tl = tileMap.getType(topTile, leftTile);
        int tr = tileMap.getType(topTile, rightTile);
        int bl = tileMap.getType(bottomTile, leftTile);
        int br = tileMap.getType(bottomTile, leftTile);

        //if (solid object) : true, else: false
        topLeft = (tl == BLOCKED);
        topRight = (tr == BLOCKED);
        bottomLeft = (bl == BLOCKED);
        bottomRight = (br == BLOCKED);
    }

    protected void checkTileMapCollision() {
        int currCol = (int) x / tileSize;
        int currRow = (int) y / tileSize;

        double xdest = x + dx;
        double ydest = y + dy;

        xtemp = x;
        ytemp = y;

        calculateCorners(x, ydest);
        if(dy < 0) {
            if(topLeft || topRight) {
                dy = 0;
                ytemp = currRow * tileSize + cheight / 2;
            } else {
                ytemp += dy;
            }
        }
        if(dy > 0) {
            if(bottomLeft || bottomRight) {
                dy = 0;
                falling = false;
                ytemp = (currRow + 1) * tileSize - cheight / 2;
            } else {
                ytemp += dy;
            }
        }

        calculateCorners(xdest, y);
        if(dx < 0) {
            if(topLeft || bottomLeft) {
                dx = 0;
                xtemp = currCol * tileSize + cwidth / 2;
            } else {
                xtemp += dx;
            }
        }
        if(dx > 0) {
            if(topRight || bottomRight) {
                dx = 0;
                xtemp = (currCol + 1) * tileSize - cwidth / 2;
            } else {
                xtemp += dx;
            }
        }

        if(!falling) {
            calculateCorners(x, ydest + 1);
            if(!bottomLeft && !bottomRight) {
                falling = true;
            }
        }
    }


    //////////////////////////////////////////
    //////////// GETTERS && SETTERS //////////
    //////////////////////////////////////////

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    protected double getDX() { return dx; }

    protected void setDX(double dx) { this.dx = dx; }

    protected double getDY() { return dy; }

    protected void setDY(double dy) { this.dy = dy; }

    private double getXmap() { return xmap; }

    private double getYmap() { return ymap; }

    private int getWidth() {
        return width;
    }

    protected void setWidth(int width) { this.width = width; }

    protected int getHeight() {
        return height;
    }

    protected void setHeight(int height) {
        this.height = height;
    }

    public int getCwidth() {
        return cwidth;
    }

    protected void setCwidth(int cwidth) { this.cwidth = cwidth; }

    protected void setCheight(int cheight) { this.cheight = cheight; }

    protected double getXtemp() { return xtemp; }

    protected double getYtemp() { return ytemp; }

    protected Animation getAnimation() { return animation; }

    protected void setAnimation(Animation animation) { this.animation = animation; }


    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    protected void setMapPosition() {
        xmap = tileMap.getX();
        ymap = tileMap.getY();
    }

    public void setTempPosition(double xtemp, double ytemp) {
        this.xtemp = xtemp;
        this.ytemp = ytemp;
    }

    public void setLeft(boolean b) { left = b; }
    public void setRight(boolean b) { right = b; }
    public void setUp(boolean b) { up = b; }
    public void setJumping(boolean b) { jumping = b; }
    protected int getCurrentAnimation() { return currentAnimation; }
    protected void setCurrentAnimation(int currentAnimation) { this.currentAnimation = currentAnimation; }


    protected boolean isLeft() {
        return left;
    }

    protected boolean isRight() {
        return right;
    }

    boolean isJumping() {
        return jumping;
    }

    protected boolean isFalling() {
        return falling;
    }

    void setFalling(boolean b) { falling = b; }

    protected void setMoveSpeed(double moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    protected double getMaxSpeed() {
        return maxSpeed;
    }

    protected void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    double getStopSpeed() {
        return stopSpeed;
    }

    void setStopSpeed(double stopSpeed) {
        this.stopSpeed = stopSpeed;
    }

    protected double getFallSpeed() {
        return fallSpeed;
    }

    protected void setFallSpeed(double fallSpeed) {
        this.fallSpeed = fallSpeed;
    }

    double getMaxFallSpeed() {
        return maxFallSpeed;
    }

    protected void setMaxFallSpeed(double maxFallSpeed) {
        this.maxFallSpeed = maxFallSpeed;
    }

    double getJumpStart() {
        return jumpStart;
    }

    void setJumpStart(double jumpStart) {
        this.jumpStart = jumpStart;
    }

    void setStopJumpSpeed(double stopJumpSpeed) {
        this.stopJumpSpeed = stopJumpSpeed;
    }
}
