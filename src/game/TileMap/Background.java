package game.TileMap;

import game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;


public class Background {

    private BufferedImage image;

    private double x, y, dx, dy;
    private double paralaxMulti;

    public Background(String resourcePath, double pm) {
        this.paralaxMulti = pm;

        try{
            image = ImageIO.read(getClass().getResourceAsStream(resourcePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPosition(double x, double y) {
        this.x = (x * paralaxMulti) % GamePanel.FRAME_WIDTH;
        this.y = (y * paralaxMulti) % GamePanel.FRAME_HEIGHT;
    }

    public void setVector(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void update() {
        x += dx;
        y += dy;
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(image, (int) x, (int) y, null);
        if (x < 0) {
            g2d.drawImage(image, (int) x + GamePanel.FRAME_WIDTH, (int) y, null);
        }
        if (x > 0) {
            g2d.drawImage(image, (int) x - GamePanel.FRAME_WIDTH, (int) y, null);
        }
        if(x == GamePanel.FRAME_WIDTH) {
            setPosition(0, 0);
        }

        if(x <= 0-GamePanel.FRAME_WIDTH) {
            setPosition(0, 0);
        }
    }

}
