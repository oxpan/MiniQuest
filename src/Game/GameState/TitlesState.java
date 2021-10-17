package Game.GameState;

import Game.Main.GamePanel;
import Game.Manager.Content;
import Game.Manager.GameStateManager;
import Game.Manager.Keys;
import Game.Manager.RecorderBox;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

public class TitlesState extends GameState{

    private BufferedImage drive;

    private Color color;
    private long ticks;
    private int pos;

    private String[] titless = {
      "PAN",
      "did everything",
            "The End"
    };

    public TitlesState(GameStateManager gs){
        super(gs);
    }
    public void init(){
    color = new Color(0, 0, 0);
    pos = GamePanel.HEIGHT;
    ticks = 0;
        try {
            drive = ImageIO.read(new FileInputStream("Resources//ico.png"));
            RecorderBox.load("Resources/Music/theend.wav", "theend");
            RecorderBox.play("theend");

        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void update(){
        ticks++;
        handleInput();
        if (ticks == 2000){
            RecorderBox.stop("theend");
            gsm.setState(GameStateManager.MENU);
        }
    }
    public void draw(Graphics2D g){
//        System.out.println("ti" + ticks);

        if (ticks%10 == 0){
            g.setColor(color);
            g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT2);
//            System.out.println("1");
            if (pos >= 0 && pos <= GamePanel.HEIGHT){
                if (pos >= 20){
                    Content.drawString(g,titless[0],50,pos);
                }

                if (pos >= 10 && pos <= GamePanel.HEIGHT-10 ){
                    Content.drawString(g,titless[1],10,pos+10);
                }

                if (pos >= 0 && pos <= GamePanel.HEIGHT - 20){
                    g.drawImage(drive, 20, pos+20, 90,90, null);
                }
//                System.out.println("-5");
                pos = pos - 2;
            }
            else {
                Content.drawString(g,titless[2],40,60);
            }

        }
    }
    public void handleInput(){
        if(Keys.isPressed(Keys.ENTER)) {
            RecorderBox.stop("theend");
            gsm.setState(GameStateManager.MENU);
            RecorderBox.play("collect");
        }
    }

}
