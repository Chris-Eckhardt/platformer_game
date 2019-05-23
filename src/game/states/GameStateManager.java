package game.states;


import game.states.nonplayable.GameOver;
import game.states.nonplayable.HALP;
import game.states.nonplayable.Menu;
import game.states.playable.Level_1;
import game.states.playable.Level_2;
import game.states.playable.Level_3;

import java.awt.*;
import java.util.ArrayList;


public class GameStateManager {

    private ArrayList<GameState> gameStates;
    private int currentState;

    //////////////////////////////////////
    /////////// GAMESTATE CODES //////////
    //////////////////////////////////////
    public static final int menu = 0;
    public static final int level_1 = 1;
    public static final int level_2 = 2;
    public static final int level_3 = 3;
    public static final int game_over = 4;
    public static final int help = 5;
    /////////////////////////////////////

    public GameStateManager() {
        // audio must init before gameStates are created because
        // they contain objects that will call it upon init
        //Audio.init();
        //Audio.load("/audio/TimeDriving.wav", "musicLoop");
        //Audio.loop("musicLoop");

        // create GameStates
        gameStates = new ArrayList<>();
        gameStates.add(new Menu(this));
        gameStates.add(new Level_1(this));
        gameStates.add(new Level_2(this));
        gameStates.add(new Level_3(this));
        gameStates.add(new GameOver(this));
        gameStates.add(new HALP(this));
    }

    public void setState(int state, int health, int lives) {
        // must call init before state is set to
        // currentState, data may exist from previous play through.
        gameStates.get(state).init();
        currentState = state;
        // now we must transfer the player info from old instance to new
        // I could probably just transfer the hero instance itself for efficiency
        // but that can be done later.
        gameStates.get(currentState).setHeroHealth(health);
        gameStates.get(currentState).setHeroLives(lives);
    }

    /////////////////////////////////////////
    //////////// UPDATE && DRAW /////////////
    /////////////////////////////////////////

    public void update() {
        gameStates.get(currentState).update();
    }

    public void draw(Graphics2D g2d) {
        gameStates.get(currentState).draw(g2d);
    }

    /////////////////////////////////////////
    /////////// PASS KEY PRESSES ////////////
    /////////////////////////////////////////

    public void keyPressed(int k) {
        gameStates.get(currentState).keyPressed(k);
    }

    public void keyReleased(int k) {
        gameStates.get(currentState).keyReleased(k);
    }


}
