package game.states.playable;

import game.GamePanel;
import game.TileMap.Background;
import game.TileMap.TileMap;
import game.gameobjects.Health;
import game.gameobjects.enemies.Enemy;
import game.gameobjects.Hero;
import game.gameobjects.enemies.Fox;
import game.states.GameState;
import game.states.GameStateManager;
import game.util.HUD;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class Level_1 extends GameState {

    private GameStateManager gsm;

    private Hero hero;

    private TileMap tileMap;
    private Background background;
    private HUD hud;

    private ArrayList<Enemy> enemies;
    private ArrayList<Health> healthList;


    public Level_1(GameStateManager gsm) {
        this.gsm = gsm;
        init();
    }

    ////////// GAME STATE METHODS ///////////

    @Override
    public void init() {

        tileMap = new TileMap(32);
        tileMap.loadTiles("/tilesets/level_1.png");
        tileMap.loadMap("/maps/level_1.txt");
        tileMap.setPosition(0,0);
        tileMap.setDrift(0.7);

        background = new Background("/tilesets/Background.png", 0.1);

        // ADD HERO //
        hero = new Hero(tileMap);
        hero.setPosition(100, 305);
        hero.setTempPosition(100, 300);
        hud = new HUD(hero);

        populateEnemies();
        populateHealth();

    }

    @Override
    public void update() {

        // check if player has lost all lives
        if(hero.gameOver()) {
            gsm.setState(GameStateManager.menu, 0, 0);
            return;
        }
        // check if player needs to respawn
        if(hero.isDead()) {
            hero.setPosition(100,305);
            hero.setTempPosition(100, 200);
            hero.loseLife();
            hero.setHealth(5);
            hero.setDead(false);
            return;
        }
        //check if player has reached checkpoint
        if(hero.nextLevel()) {
            gsm.setState(GameStateManager.level_2, hero.getHealth(), hero.getLives());
            return;
        }

        hero.update();


        tileMap.setPosition((GamePanel.FRAME_WIDTH / 2) - hero.getX(), (GamePanel.FRAME_HEIGHT / 2) - hero.getY());
        background.setPosition(tileMap.getX(), tileMap.getY());
        hero.checkAttack(enemies);
        hero.checkCollection(healthList);

        for(int i  = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);

            enemy.checkSight(hero);
            enemy.checkBounds(hero);
            enemy.checkAttack(hero);
            enemy.update();

            if(!enemy.isAlive()) {
                enemies.remove(enemy);
            }
        }

        for(int i  = 0; i < healthList.size(); i++) {
            Health health = healthList.get(i);
            health.update();
            if(health.isCollected()) {
                healthList.remove(health);
            }
        }

        hud.update();
    }

    @Override
    public void draw(Graphics2D g2d) {

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0,0, GamePanel.FRAME_WIDTH, GamePanel.FRAME_HEIGHT);

        background.draw(g2d);
        tileMap.draw(g2d);
        hero.draw(g2d);

        for(int i  = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g2d);
        }

        for(int i  = 0; i < healthList.size(); i++) {
            healthList.get(i).draw(g2d);
        }

        hud.draw(g2d);
    }

    //////////// KEY INPUT //////////////

    @Override
    public void keyPressed(int k) {
        if(k == KeyEvent.VK_UP) hero.setJumping(true);
        if(k == KeyEvent.VK_LEFT) hero.setLeft(true);
        if(k == KeyEvent.VK_RIGHT) hero.setRight(true);
        if(k == KeyEvent.VK_SPACE) hero.setAttacking();
    }

    @Override
    public void keyReleased(int k) {
        if(k == KeyEvent.VK_UP) hero.setJumping(false);
        if(k == KeyEvent.VK_LEFT) hero.setLeft(false);
        if(k == KeyEvent.VK_RIGHT) hero.setRight(false);
    }

    /////// PLAYABLE STATE METHODS ////////

    @Override
    public void setHeroHealth(int health) {
        hero.setHealth(health);
    }

    @Override
    public void setHeroLives(int lives) {
        hero.setLives(lives);
    }

    private void populateEnemies() {
        enemies = new ArrayList<>();

        Fox fox1 = new Fox(tileMap);
        fox1.setPosition(100, 565);
        enemies.add(fox1);

        Fox fox2 = new Fox(tileMap);
        fox2.setPosition(720, 300);
        enemies.add(fox2);

        Fox fox3 = new Fox(tileMap);
        fox3.setPosition(1760, 270);
        enemies.add(fox3);

        Fox fox4 = new Fox(tileMap);
        fox4.setPosition(1860, 560);
        enemies.add(fox4);
    }

    private void populateHealth() {
        healthList = new ArrayList<>();

        Health h1 = new Health(tileMap);
        h1.setPosition(530, 450);
        healthList.add(h1);

        Health h2 = new Health(tileMap);
        h2.setPosition(1020, 241);
        healthList.add(h2);

        Health h3 = new Health(tileMap);
        h3.setPosition(1913, 300);
        healthList.add(h3);

    }

    ///////////////////////////////////////
}
