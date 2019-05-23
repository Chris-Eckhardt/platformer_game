package game;

import javax.swing.*;


public class Game {

    //////////////////////////////////////
    /////////////// MAIN /////////////////
    //////////////////////////////////////

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ninja Platformer");
        frame.setContentPane(new GamePanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

}
