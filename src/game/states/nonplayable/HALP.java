package game.states.nonplayable;

import game.TileMap.Background;
import game.states.GameState;
import game.states.GameStateManager;

import java.awt.*;
import java.awt.event.KeyEvent;

public class HALP extends GameState {

    private GameStateManager gsm;

    private Background background;

    public HALP(GameStateManager gsm) {
        this.gsm = gsm;
        try {
            background = new Background("/tilesets/Background.png", 1);
            background.setVector(-0.4, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        // not needed for non-playable states
    }

    @Override
    public void update() {
        background.update();
    }

    @Override
    public void draw(Graphics2D g2d) {
        // BACKGROUND
        background.draw(g2d);

        // TITLE
        g2d.setColor(new Color(128, 0,0));
        g2d.drawString("CONTROLS", 250,130);
        g2d.drawString("left-arrow : move left", 250,160);
        g2d.drawString("right-arrow : move right", 250,190);
        g2d.drawString("up-arrow : jump", 250,220);
        g2d.drawString("space bar : attack", 250,250);
        g2d.drawString("press ENTER to return to main menu.", 250,500);



        // instructions



    }

    private void enter() {
        gsm.setState(GameStateManager.menu, 0, 0); // START GAME
    }

    @Override
    public void keyPressed(int k) {
        if(k == KeyEvent.VK_ENTER) enter();
    }

    @Override
    public void keyReleased(int k) {

    }

    @Override
    public void setHeroHealth(int health) {

    }

    @Override
    public void setHeroLives(int lives) {

    }
}
