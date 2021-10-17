package Game.GameState;

import Game.Main.GamePanel;
import Game.Manager.GameStateManager;
import Game.Manager.Keys;
import Game.Manager.RecorderBox;


import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.io.FileInputStream;

public class IntroState extends GameState{
    private BufferedImage logo;

    private int alpha;
    private int ticks;

    private final int FADE_IN = 120;
    private final int LENGTH = 210;
    private final int FADE_OUT = 120;

    public IntroState(GameStateManager gsm) {
        super(gsm);
    }

    public void init() {
        ticks = 0;
        try {
            logo = ImageIO.read(new FileInputStream("Resources/Logo/logo.gif"));
            RecorderBox.load("Resources/Music/intro3.wav", "musicintro");
            RecorderBox.setVolume("music1", -10);
            RecorderBox.play("musicintro");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        handleInput();
        ticks++;
        if(ticks < FADE_IN) {
            alpha = (int) (255 - 255 * (1.0 * ticks / FADE_IN));
//            System.out.println("+");
            if(alpha < 0) alpha = 0;
        }
        if(ticks > FADE_IN + LENGTH) {
            alpha = (int) (255 * (1.0 * ticks - FADE_IN - LENGTH) / FADE_OUT);
//            System.out.println("-");
            if(alpha > 255) alpha = 255;
        }
        if(ticks > FADE_IN + LENGTH + FADE_OUT) {
            gsm.setState(GameStateManager.MENU);
        }
//        System.out.println(alpha);
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT2);
        g.drawImage(logo, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT2, null);
        g.setColor(new Color(0, 0, 0, alpha));
//        System.out.println("draw");
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT2);
    }

    public void handleInput() {
        if(Keys.isPressed(Keys.ENTER)) {
            RecorderBox.stop("musicintro");
            gsm.setState(GameStateManager.MENU);
        }
    }
}
