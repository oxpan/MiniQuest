package Game.GameState;

import Game.Main.GamePanel;
import Game.Manager.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

public class GameOverState extends GameState{
    private Color color;

    private int rank;
    private long ticks;
    private BufferedImage solo;
    private BufferedImage youdead;

    public GameOverState(GameStateManager gsm) {
        super(gsm);
    }
    public void init() {
        try {
//            logo = ImageIO.read(new FileInputStream("Resources/Logo/Dialog.gif"));
            solo = ImageIO.read(new FileInputStream("Resources/Sprites/solo.gif"));
            youdead = ImageIO.read(new FileInputStream("Resources/Sprites/youdead.gif"));
            RecorderBox.load("Resources/SFX/dsdead.wav","dsdead");
            RecorderBox.load("Resources/SFX/rmend.wav","rmend");

        }
        catch(Exception e) {
            e.printStackTrace();
        }

        if (Data.getIsDead() == true){
            RecorderBox.play("dsdead");
        }else {
            RecorderBox.play("rmend");
        }
        color = new Color(229, 108, 24);
        ticks = Data.getTime();
        if(ticks < 4500) rank = 1;
        else if(ticks < 6300) rank = 2;
        else if(ticks < 8100) rank = 3;
        else rank = 4;
    }
    public void update() {handleInput();}
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT2);

        Content.drawString(g, "time", 46, 36);

        int minutes = (int) (ticks / 1800);
        int seconds = (int) ((ticks / 30) % 60);

        if(minutes < 10) {
            if(seconds < 10) Content.drawString(g, "0" + minutes + ":0" + seconds, 44, 48);
            else Content.drawString(g, "0" + minutes + ":" + seconds, 44, 48);
        }
        else {
            if(seconds < 10) Content.drawString(g, minutes + ":0" + seconds, 44, 48);
            else Content.drawString(g, minutes + ":" + seconds, 44, 48);
        }
        if (Data.getIsDead() == true){

//            Content.drawString(g,"Y", 20, 78);
            g.drawImage(youdead, 0, 78,GamePanel.WIDTH,24, null);

        }
        if (Data.getIsDead() == false){
            Content.drawString(g, "rank", 48, 66);
            if(rank == 1) Content.drawString(g, "speed demon", 20, 78);
            else if(rank == 2) Content.drawString(g, "adventurer", 24, 78);
            else if(rank == 3) Content.drawString(g, "beginner", 32, 78);
            else if(rank == 4) Content.drawString(g, "bumbling idiot!", 8, 78);
        }

        Content.drawString(g, "press any key", 12, 110);
        g.drawImage(solo, 44, 120, null);
    }
    public void handleInput() {

            if (Keys.isPressed(Keys.ENTER)) {

                    if (Data.getIsDead() == true) {
                        RecorderBox.stop("dsdead");
                        gsm.setState(GameStateManager.MENU);

                    } else {
                        RecorderBox.stop("rmend");
                        gsm.setState(GameStateManager.TITLES);
                    }

                    RecorderBox.play("collect");

            }

    }
}
