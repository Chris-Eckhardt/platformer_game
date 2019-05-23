package game.util;

import java.awt.image.BufferedImage;


public class HeroImageLoader extends ImageLoader {

    public BufferedImage[] loadIdle() {
        BufferedImage[] images = new BufferedImage[5];

        images[0] = this.loadImage("/player/idle/player_Idle_0.png");
        images[1] = this.loadImage("/player/idle/player_Idle_1.png");
        images[2] = this.loadImage("/player/idle/player_Idle_2.png");
        images[3] = this.loadImage("/player/idle/player_Idle_3.png");
        images[4] = this.loadImage("/player/idle/player_Idle_4.png");

        return images;
    }

    public BufferedImage[] loadRun() {
        BufferedImage[] images = new BufferedImage[4];

        images[0] = this.loadImage("/player/run/player_Run_0.png");
        images[1] = this.loadImage("/player/run/player_Run_1.png");
        images[2] = this.loadImage("/player/run/player_Run_2.png");
        images[3] = this.loadImage("/player/run/player_Run_3.png");

        return images;
    }

    public BufferedImage[] loadJumping() {
        BufferedImage[] images = new BufferedImage[2];

        images[0] = this.loadImage("/player/jump/player_jump up_0.png");
        images[1] = this.loadImage("/player/jump/player_jump up_1.png");

        return images;
    }

    public BufferedImage[] loadFalling() {
        BufferedImage[] images = new BufferedImage[2];

        images[0] = this.loadImage("/player/jump/falling_0.png");
        images[1] = this.loadImage("/player/jump/falling_1.png");

        return images;
    }

    public BufferedImage[] loadClimbing() {
        BufferedImage[] images = new BufferedImage[6];

        images[0] = this.loadImage("/player/climb ladder/ladder climb_ladder climb_0.png");
        images[1] = this.loadImage("/player/climb ladder/ladder climb_ladder climb_1.png");
        images[2] = this.loadImage("/player/climb ladder/ladder climb_ladder climb_2.png");
        images[3] = this.loadImage("/player/climb ladder/ladder climb_ladder climb_3.png");
        images[4] = this.loadImage("/player/climb ladder/ladder climb_ladder climb_4.png");
        images[5] = this.loadImage("/player/climb ladder/ladder climb_ladder climb_5.png");

        return images;
    }

    public BufferedImage[] loadCrouch() {
        BufferedImage[] images = new BufferedImage[7];

        images[0] = this.loadImage("/player/crouch/ninja update 1_crouch_0.png");
        images[1] = this.loadImage("/player/crouch/ninja update 1_crouch_1.png");
        images[2] = this.loadImage("/player/crouch/ninja update 1_crouch_2.png");
        images[3] = this.loadImage("/player/crouch/ninja update 1_crouch_3.png");
        images[4] = this.loadImage("/player/crouch/ninja update 1_crouch_4.png");
        images[5] = this.loadImage("/player/crouch/ninja update 1_crouch_5.png");
        images[6] = this.loadImage("/player/crouch/ninja update 1_crouch_6.png");

        return images;
    }

    public BufferedImage[] loadAttack1() {
        BufferedImage[] images = new BufferedImage[4];

        images[0] = this.loadImage("/player/attack/attack_1/attack_1_0.png");
        images[1] = this.loadImage("/player/attack/attack_1/attack_1_1.png");
        images[2] = this.loadImage("/player/attack/attack_1/attack_1_2.png");
        images[3] = this.loadImage("/player/attack/attack_1/attack_1_3.png");

        return images;
    }
}
