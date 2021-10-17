package Game.GameState;

import Game.Manager.Content;
import Game.Manager.GameStateManager;
import Game.Manager.Keys;
import Game.Manager.RecorderBox;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuState extends GameState{
    private BufferedImage bg;
    private BufferedImage bgi;
    private BufferedImage bgin;
    private BufferedImage diamond;
    private long ticks;

    private int currentOption = 0;
    private String[] options = {
            "START",
            "INTRO",
            "TITLES",
            "QUIT"
    };
    public MenuState(GameStateManager gsm) {
        super(gsm);
    }
    public void init() {
        bg = Content.MENUBG[0][0];
        bgi = Content.MENUBG2[0][0];
        bgin = Content.MENUBG3[0][0];
        diamond = Content.DIAMOND[0][0];
        RecorderBox.load("Resources/SFX/collect.wav", "collect");
        RecorderBox.load("Resources/SFX/menuoption.wav", "menuoption");
        RecorderBox.load("Resources/Music/intro1.wav", "musicmenu");
        RecorderBox.setVolume("music1", -10);
        RecorderBox.play("musicmenu");
        ticks = 0;
    }

    public void update() {
        handleInput();
        ticks++;
//        System.out.println("t = "+ticks);
    }
    public void draw(Graphics2D g) {
        if (ticks > 1800 && ticks <= 3600){
            g.drawImage(bgi, 0, 0, null);
        }else if (ticks > 3600){
            g.drawImage(bgin, 0, 0, null);
        }
        else {
            g.drawImage(bg, 0, 0, null);
        }


        Content.drawString(g, options[0], 44, 70);
        Content.drawString(g, options[1], 44, 80);
        Content.drawString(g, options[2], 40, 90);
        Content.drawString(g, options[3], 48, 100);

        if(currentOption == 0) g.drawImage(diamond, 25, 66, null);
        else if(currentOption == 1) g.drawImage(diamond, 25, 76, null);
        else if (currentOption == 2) g.drawImage(diamond, 25, 86, null);
        else if (currentOption == 3) g.drawImage(diamond, 25, 96, null);
    }

    public void handleInput() {
        if(Keys.isPressed(Keys.DOWN) && currentOption < options.length - 1) {
            RecorderBox.play("menuoption");
            currentOption++;
        }
        if(Keys.isPressed(Keys.UP) && currentOption > 0) {
            RecorderBox.play("menuoption");
            currentOption--;
        }
        if(Keys.isPressed(Keys.ENTER)) {
            RecorderBox.play("collect");
            selectOption();
        }
    }

    private void selectOption() {
        if(currentOption == 0) {
            RecorderBox.stop("musicmenu");
//            gsm.setState(GameStateManager.PLAY);
            gsm.setState(GameStateManager.DIALOG);
        }
        if(currentOption == 3) {
            System.exit(0);
        }
        if (currentOption == 1){
            RecorderBox.stop("musicmenu");
            gsm.setState(GameStateManager.INTRO);
        }
        if (currentOption == 2){
            RecorderBox.stop("musicmenu");
            gsm.setState(GameStateManager.TITLES);
        }
    }
}
