package game.gameobjects;

import game.TileMap.TileMap;
import game.util.Animation;
import game.util.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Health extends GameObject{

    private boolean collected;


    public Health(TileMap tileMap) {
        super(tileMap);
        facingRight = true;
        collected = false;
        this.setAnimation( new Animation() );
        this.getAnimation().setFrames(loadHealthImages());
        this.getAnimation().setDelay(200);
    }

    public void update() {
        this.getAnimation().update();
    }

    public void draw(Graphics2D g2d) {
        this.setMapPosition();
        super.draw(g2d);
    }

    public void collect() {
        collected = true;
    }

    public boolean isCollected() {
        return collected;
    }

    private BufferedImage[] loadHealthImages() {
        BufferedImage[] images = new BufferedImage[8];
        ImageLoader loader = new ImageLoader();
        images[0] = loader.loadImage("/ninja_stars/ninja_star_0.png");
        images[1] = loader.loadImage("/ninja_stars/ninja_star_1.png");
        images[2] = loader.loadImage("/ninja_stars/ninja_star_2.png");
        images[3] = loader.loadImage("/ninja_stars/ninja_star_3.png");
        images[4] = loader.loadImage("/ninja_stars/ninja_star_4.png");
        images[5] = loader.loadImage("/ninja_stars/ninja_star_5.png");
        images[6] = loader.loadImage("/ninja_stars/ninja_star_6.png");
        images[7] = loader.loadImage("/ninja_stars/ninja_star_7.png");
        return images;
    }

}
