package game.util;

import java.awt.image.BufferedImage;


public class EnemyImageLoader extends ImageLoader {

    public BufferedImage[] loadEnemyFoxIdle() {
        BufferedImage[] images = new BufferedImage[6];

        images[0] = loadImage("/enemy/Fox/idle/Fox Enemy_Idle_0.png");
        images[1] = loadImage("/enemy/Fox/idle/Fox Enemy_Idle_1.png");
        images[2] = loadImage("/enemy/Fox/idle/Fox Enemy_Idle_2.png");
        images[3] = loadImage("/enemy/Fox/idle/Fox Enemy_Idle_3.png");
        images[4] = loadImage("/enemy/Fox/idle/Fox Enemy_Idle_4.png");
        images[5] = loadImage("/enemy/Fox/idle/Fox Enemy_Idle_5.png");

        return images;
    }

    public BufferedImage[] loadEnemyFoxWalk() {
        BufferedImage[] images = new BufferedImage[8];

        images[0] = loadImage("/enemy/Fox/walk/Fox Enemy_walk_0.png");
        images[1] = loadImage("/enemy/Fox/walk/Fox Enemy_walk_1.png");
        images[2] = loadImage("/enemy/Fox/walk/Fox Enemy_walk_2.png");
        images[3] = loadImage("/enemy/Fox/walk/Fox Enemy_walk_3.png");
        images[4] = loadImage("/enemy/Fox/walk/Fox Enemy_walk_4.png");
        images[5] = loadImage("/enemy/Fox/walk/Fox Enemy_walk_5.png");
        images[6] = loadImage("/enemy/Fox/walk/Fox Enemy_walk_6.png");
        images[7] = loadImage("/enemy/Fox/walk/Fox Enemy_walk_7.png");

        return images;
    }

    public BufferedImage[] loadEnemyFoxAttack() {
        BufferedImage[] images = new BufferedImage[6];

        images[0] = loadImage("/enemy/Fox/attack/Enemy_Attack_0.png");
        images[1] = loadImage("/enemy/Fox/attack/Enemy_Attack_1.png");
        images[2] = loadImage("/enemy/Fox/attack/Enemy_Attack_2.png");
        images[3] = loadImage("/enemy/Fox/attack/Enemy_Attack_3.png");
        images[4] = loadImage("/enemy/Fox/attack/Enemy_Attack_4.png");
        images[5] = loadImage("/enemy/Fox/attack/Enemy_Attack_5.png");

        return images;
    }

}
