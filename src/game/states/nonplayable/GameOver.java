package game.states.nonplayable;

import game.TileMap.Background;
import game.states.GameState;
import game.states.GameStateManager;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameOver extends GameState {

    private GameStateManager gsm;

    private Background background;
    private Color titleColor;
    private Font titleFont;
    private Font font;
    private String button = "Press ENTER to return to main menu";

    public GameOver(GameStateManager gsm) {
        this.gsm = gsm;
        try {
            background = new Background("/tilesets/Background.png", 1);
            background.setVector(-0.4, 0);

            titleColor = new Color(128, 0, 0);
            titleFont = new Font("Century Gothic", Font.PLAIN, 36);

            font = new Font("Arial", Font.PLAIN, 24);

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
        g2d.setColor(titleColor);
        g2d.setFont(titleFont);
        g2d.drawString("Congratulations!", 180, 160);

        // BUTTONS
        g2d.setFont(font);

        g2d.drawString(button, 160, 200);

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
