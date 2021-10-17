package Game.Entity;

import Game.Manager.Content;
import Game.Manager.RecorderBox;
import Game.TileMap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Monster extends Entity{
// spaites
    private BufferedImage[] downSprites;
    private BufferedImage[] leftSprites;
    private BufferedImage[] rightSprites;
    private BufferedImage[] upSprites;
    private BufferedImage[] downBoatSprites;
    private BufferedImage[] leftBoatSprites;
    private BufferedImage[] rightBoatSprites;
    private BufferedImage[] upBoatSprites;

    // animation
    private final int DOWN = 0;
    private final int LEFT = 1;
    private final int RIGHT = 2;
    private final int UP = 3;
    private final int DOWNBOAT = 4;
    private final int LEFTBOAT = 5;
    private final int RIGHTBOAT = 6;
    private final int UPBOAT = 7;

    // gameplay
    private boolean onWater;
    private long ticks;

    private int putX1,putY1,putX2,putY2;
    private boolean isPutXY1,isPutXY2;
    private Player player;
    private int damage;
    private long culldawnDamage;
    private boolean isDamage;



    public Monster(TileMap tm, Player player){
        super(tm);
        this.player = player;

        width = 16;
        height = 16;
        cwidth = 12;
        cheight = 12;

        moveSpeed = 2;
        isPutXY1 = true;
        isPutXY2 = false;
        damage = 1;
        culldawnDamage = 0;
        isDamage = true;


        downSprites = Content.MONSTER[0];
        leftSprites = Content.MONSTER[1];
        rightSprites = Content.MONSTER[2];
        upSprites = Content.MONSTER[3];
        downBoatSprites = Content.MONSTER[4];
        leftBoatSprites = Content.MONSTER[5];
        rightBoatSprites = Content.MONSTER[6];
        upBoatSprites = Content.MONSTER[7];

        animation.setFrames(downSprites);
        animation.setDelay(10);

    }
    private void setAnimation(int i, BufferedImage[] bi, int d) {
        currentAnimation = i;
        animation.setFrames(bi);
        animation.setDelay(d);
    }
    public void setPutXY1(int putY1, int putX1){
        int tmpy= putY1 * tileSize + tileSize / 2;
        int tmpx = putX1 * tileSize + tileSize / 2;
        this.putX1 = tmpx;
        this.putY1 = tmpy;
    }
    public void setPutXY2(int putY2, int putX2){
        int tmpy= putY2 * tileSize + tileSize / 2;
        int tmpx = putX2 * tileSize + tileSize / 2;
        this.putX2 = tmpx;
        this.putY2 = tmpy;
    }

    public void setDown() {
        super.setDown();
    }
    public void setLeft() {
        super.setLeft();
    }
    public void setRight() {
        super.setRight();
    }
    public void setUp() {
        super.setUp();
    }

    public void setAction(){
        if (xdest == player.xdest && ydest == player.ydest){
            RecorderBox.play("damage");
            player.damage(damage);
        }
    }


    public void update(){
        ticks++;

        if (ticks%10 == 0) {
            intelligentMovement();
        }
        if (culldawnDamage < 20){
            culldawnDamage++;
        }//culldawn is damage
        if (culldawnDamage == 15){
            isDamage = true;
        }
        if (isDamage) {
            setAction();
            isDamage = false;
            culldawnDamage = 0;
        }

        // check if on water
        boolean current = onWater;
        if(tileMap.getIndex(ydest / tileSize, xdest / tileSize) == 4) {
            onWater = true;
        }
        else {
            onWater = false;
        }
        // if going from land to water
        if(!current && onWater) {
            RecorderBox.play("splash");
        }

        // set animation
        if(down) {
            if(onWater && currentAnimation != DOWNBOAT) {
                setAnimation(DOWNBOAT, downBoatSprites, 10);
            }
            else if(!onWater && currentAnimation != DOWN) {
                setAnimation(DOWN, downSprites, 10);
            }
        }
        if(left) {
            if(onWater && currentAnimation != LEFTBOAT) {
                setAnimation(LEFTBOAT, leftBoatSprites, 10);
            }
            else if(!onWater && currentAnimation != LEFT) {
                setAnimation(LEFT, leftSprites, 10);
            }
        }
        if(right) {
            if(onWater && currentAnimation != RIGHTBOAT) {
                setAnimation(RIGHTBOAT, rightBoatSprites, 10);
            }
            else if(!onWater && currentAnimation != RIGHT) {
                setAnimation(RIGHT, rightSprites, 10);
            }
        }
        if(up) {
            if(onWater && currentAnimation != UPBOAT) {
                setAnimation(UPBOAT, upBoatSprites, 10);
            }
            else if(!onWater && currentAnimation != UP) {
                setAnimation(UP, upSprites, 10);
            }
        }

    super.update();
    }
    public void draw(Graphics2D g) {
        super.draw(g);
    }




    private void intelligentMovement(){
            if (isPutXY1) {
                if (xdest < putX1) {
                    setRight();
//                    System.out.println("1");
//                    System.out.println("xdes = "+ xdest +"ydes = "+ ydest+ ": putX = "+putX1 + ": putY = "+putY1);
                }
                if (ydest < putY1) {
                    setDown();//zamena
//                    System.out.println("2");
//                    System.out.println("xdes = "+ xdest +"ydes = "+ ydest+ ": putX = "+putX1 + ": putY = "+putY1);
                }
                if (xdest > putX1) {
                    setLeft();
//                    System.out.println("3");
//                    System.out.println("xdes = "+ xdest +"ydes = "+ ydest+ ": putX = "+putX1 + ": putY = "+putY1);
                }
                if (ydest > putY1) {
                    setUp();//zamena
//                    System.out.println("4");
//                    System.out.println("xdes = "+ xdest +"ydes = "+ ydest+ ": putX = "+putX1 + ": putY = "+putY1);
                }
                if (xdest == putX1 && ydest == putY1) {
                    isPutXY1 = false;
                    isPutXY2 = true;
//                    System.out.println("5");
//                    System.out.println("xdes = "+ xdest +"ydes = "+ ydest+ ": putX = "+putX1 + ": putY = "+putY1);
                }
//                System.out.println("0");
            }
            if (isPutXY2) {
                if (xdest < putX2) {
                    setRight();
//                    System.out.println("1+");
//                    System.out.println("xdes = "+ xdest +"ydes = "+ ydest+ ": putX = "+putX2 + ": putY = "+putY2);
                }
                if (ydest < putY2) {
                    setDown();
//                    System.out.println("2+");
//                    System.out.println("xdes = "+ xdest +"ydes = "+ ydest+ ": putX = "+putX2 + ": putY = "+putY2);
                }
                if (xdest > putX2) {
                    setLeft();
//                    System.out.println("3+");
//                    System.out.println("xdes = "+ xdest +"ydes = "+ ydest+ ": putX = "+putX2 + ": putY = "+putY2);
                }
                if (ydest > putY2) {
                    setUp();
//                    System.out.println("4+");
//                    System.out.println("xdes = "+ xdest +"ydes = "+ ydest+ ": putX = "+putX2 + ": putY = "+putY2);
                }
                if (xdest == putX2 && ydest == putY2) {
                    isPutXY1 = true;
                    isPutXY2 = false;
//                    System.out.println("5");
//                    System.out.println("xdes = "+ xdest +"ydes = "+ ydest + ": putX = "+putX2 + ": putY = "+putY2);
                }
//                System.out.println("0");
            }
    }



}
