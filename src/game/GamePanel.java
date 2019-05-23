package game;

import game.keyInput.KeyInput;
import game.states.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class GamePanel extends JPanel implements Runnable {

    public static final int WORLD_WIDTH = 2048;
    public static final int WORLD_HEIGHT = 640;

    public static final int FRAME_WIDTH = 692;
    public static final int FRAME_HEIGHT = 640;

    private Thread thread;
    private GameStateManager gsm;
    private boolean running;

    private BufferedImage world;
    private Graphics2D g2d;

    //////////////////////////////////////
    //////////// CONSTRUCTOR /////////////
    //////////////////////////////////////

    GamePanel() {
        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setFocusable(true);
        requestFocus();
    }

    public void addNotify() {
        super.addNotify();
        if(thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    //////////////////////////////////////
    //////////////// INIT ////////////////
    //////////////////////////////////////

    private void init() {
        world = new BufferedImage(WORLD_WIDTH, WORLD_HEIGHT, BufferedImage.TYPE_INT_RGB);

        g2d = (Graphics2D) world.getGraphics();

        gsm = new GameStateManager();

        this.addKeyListener(new KeyInput(gsm));

        running = true;
    }

    //////////////////////////////////////
    //////////// GAME LOOP ///////////////
    //////////////////////////////////////

    public void run() {
        init();

        long lastTime = System.nanoTime();
        final double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int updates = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();

        while(running) {

            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if(delta >= 1) {
                update();
                updates++;
                delta--;
            }

            draw();
            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(("updates : " + updates + " | FPS: "  + frames));
                updates = 0;
                frames = 0;
            }
        }

        stop();
    }

    //////////////////////////////////////
    //////////// STOP THREAD /////////////
    //////////////////////////////////////

    private void stop() {
        running = false;
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //////////////////////////////////////
    /////////////// UPDATE ///////////////
    //////////////////////////////////////

    private void update() {
        gsm.update();
    }

    /////////////////////////////////////////////////
    ///// DRAW IMAGES ONTO GRAPHICS WRAPPER /////////
    /////////////////////////////////////////////////

    public void draw() {
        gsm.draw(g2d);
    }

    //////////////////////////////////////
    //////// RENDER IMAGE TO PANEL ///////
    //////////////////////////////////////

    private void render() {
        Graphics g = getGraphics();
        g.drawImage(world, 0, 0, null);
        g.dispose();
    }

}
