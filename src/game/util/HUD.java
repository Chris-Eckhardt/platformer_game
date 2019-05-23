package game.util;

import game.gameobjects.Hero;

import java.awt.*;
import java.awt.image.BufferedImage;


public class HUD {

    private Hero hero;
    private BufferedImage shadow;
    private BufferedImage fill;
    private int currentHealth;
    private int currentLives;
    private Font font;
    private Color color;
    private static final int x = 20;
    private static final int y = 0;
    private static final int height = 60;
    private BufferedImage[] healthBars;

    public HUD(Hero hero) {
        this.hero = hero;

        color = new Color(150, 150, 150);
        font = new Font("Century Gothic", Font.PLAIN, 24);

        // load healthbar images
        ImageLoader loader = new ImageLoader();
        shadow = loader.loadImage("/ui/Empty Health.png");
        fill = loader.loadImage("/ui/Full Health.png");

        healthBars = new BufferedImage[6];
        healthBars[0] = loader.loadImage("/ui/Empty Health.png");
        healthBars[1] = loader.loadImage("/ui/Full Health.png");
        healthBars[2] = fill.getSubimage(0,0, 130, height);
        healthBars[3] = fill.getSubimage(0,0, 115, height);
        healthBars[4] = fill.getSubimage(0,0, 95, height);
        healthBars[5] = fill.getSubimage(0,0, 75, height);

    }

    public void update() {
        currentHealth = hero.getHealth();
        currentLives = hero.getLives();
    }

    public void draw(Graphics2D g) {

        /////////// HEALTH BAR ////////////////
        if(currentHealth == 5) {
            g.drawImage(healthBars[1], x, y, null);
        } else if(currentHealth == 4) {
            g.drawImage(healthBars[0], x, y, null);
            g.drawImage(healthBars[2], x, y, null);
        } else if(currentHealth == 3) {
            g.drawImage(healthBars[0], x, y, null);
            g.drawImage(healthBars[3], x, y, null);
        } else if(currentHealth == 2) {
            g.drawImage(healthBars[0], x, y, null);
            g.drawImage(healthBars[4], x, y, null);
        } else if(currentHealth == 1) {
            g.drawImage(healthBars[0], x, y, null);
            g.drawImage(healthBars[5], x, y, null);
        } else {
            g.drawImage(healthBars[0], x, y, null);
        }

        ////////// DRAW HERO LIVES ///////////
        g.setColor(color);
        g.setFont(font);
        g.drawString("Lives : " + currentLives,
                x + 500, y + 35);

        ////////// DEBUG | HERO POSITION ///////////
        // DEBUG POSITION
        // g.setColor(Color.WHITE);
        //g.drawString((int)hero.getX() + ", " + (int)hero.getY(), x + 500, y + 100);

    }
}
