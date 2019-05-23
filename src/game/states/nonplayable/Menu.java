package game.states.nonplayable;

import game.TileMap.Background;
import game.states.GameState;
import game.states.GameStateManager;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Menu extends GameState {

    private GameStateManager gsm;

    private Background background;
    private Color titleColor;
    private Font titleFont;
    private Font font;

    private int userInput = 0;
    private String[] buttons = {
            "Start", "Help", "Quit"
    };

    public Menu(GameStateManager gsm) {
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
        // Not needed for menu.
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
        g2d.drawString("Ninja Game", 250, 120);

        // BUTTONS
        g2d.setFont(font);
        for (int i = 0; i < buttons.length; i++) {
            if (i == userInput) {
                g2d.setColor(Color.BLACK);
            } else {
                g2d.setColor(titleColor);
            }
            g2d.drawString(buttons[i], 310, 160 + i * 40);
        }


    }

    private void enter() {
        if(userInput == 0) {
            gsm.setState(GameStateManager.level_1, 5, 2); // START GAME
            return;
        }
        if(userInput == 1) {
            gsm.setState(GameStateManager.help, 0, 0);
            return;
        }
        if(userInput == 2) {
            System.exit(0); // QUIT
        }

    }

    @Override
    public void keyPressed(int k) {
        if(k == KeyEvent.VK_ENTER) enter();
        if(k == KeyEvent.VK_UP) {
            userInput--;
            if(userInput < 0) {
                userInput = buttons.length-1;
            }
        }
        if(k == KeyEvent.VK_DOWN) {
            userInput++;
            if(userInput > 2) {
                userInput = 0;
            }
        }


    }

    @Override
    public void keyReleased(int k) {
        // Not needed in menu.
    }

    @Override
    public void setHeroHealth(int health) {
        // not needed in menu, i probably could abstract this out
    }

    @Override
    public void setHeroLives(int lives) {

    }

}
