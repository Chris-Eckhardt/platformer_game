package game.keyInput;

import game.states.GameStateManager;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class KeyInput extends KeyAdapter {

    private GameStateManager gsm;

    public KeyInput(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public void keyPressed(KeyEvent e) {
        gsm.keyPressed(e.getKeyCode());
    }

    public void keyReleased(KeyEvent e) {
        gsm.keyReleased(e.getKeyCode());
    }
}
