package game.states;

import java.awt.*;


public abstract class GameState {

    public GameState() {}

    public abstract void init();
    public abstract void update();
    public abstract void draw(Graphics2D g2d);

    public abstract void keyPressed(int k);
    public abstract void keyReleased(int k);

    public abstract void setHeroHealth(int health);
    public abstract void setHeroLives(int lives);
}
