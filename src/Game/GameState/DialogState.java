package Game.GameState;

import Game.Main.GamePanel;
import Game.Manager.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.ArrayList;

public class DialogState extends GameState{
    private BufferedImage logo;
    private BufferedImage drive;


    private int ticks;
    private boolean eventStart;
    private boolean eventFinish;
    private int eventTick;


    private final int LENGTH1 = 90;
    private final int LENGTH2 = 70;
    private final int LENGTH3 = 70;
    private final int LENGTH4 = 70;
    private final int FADE_OUT = 100;
    private String[] options = {
            "Hi! Im Totoro!",
            "I love ",
            "diamonds!",
            "collect the ",
            "diamonds!"
    };

    private ArrayList<Rectangle> boxes;
    public DialogState(GameStateManager gsm){ super(gsm);}
    public void init(){
        ticks = 0;
        try {
            logo = ImageIO.read(new FileInputStream("Resources/Logo/Dialog.gif"));
            drive = ImageIO.read(new FileInputStream("Resources/Logo/Drive.gif"));
            RecorderBox.load("Resources/SFX/totoro.wav", "totoro");

        }
        catch(Exception e) {
            e.printStackTrace();
        }
        boxes = new ArrayList<Rectangle>();
        eventStart = true;
        eventStart();
    }
    public void update(){
        handleInput();
        if(eventStart) eventStart();
        if(eventFinish) eventFinish();
        ticks++;
        if(ticks > LENGTH1+LENGTH2+LENGTH3+LENGTH4+FADE_OUT) {
//            gsm.setState(GameStateManager.MENU);
            eventFinish = true;
        }
        if (ticks == LENGTH1+LENGTH2){
            RecorderBox.play("totoro");
        }

    }
    public void draw(Graphics2D g){
        g.setColor(Color.RED);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT2);
        g.drawImage(logo, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT2, null);
        if (ticks >= LENGTH1){
            g.drawImage(drive, 7, 18, null);

        }
        if (ticks >= LENGTH1+LENGTH2){

            Content.drawString(g,options[0],12,30);
        }
        if (ticks >= LENGTH1+LENGTH2+LENGTH3){

            g.drawImage(drive, 7, 18, null);
            Content.drawString(g,options[1],14,24);
            Content.drawString(g,options[2],14,34);
        }
        if (ticks >= LENGTH1+LENGTH2+LENGTH3+LENGTH4){

            g.drawImage(drive, 7, 18, null);
            Content.drawString(g,options[3],14,24);
            Content.drawString(g,options[4],14,34);

        }
        // draw transition boxes
        g.setColor(java.awt.Color.BLACK);
        for(int i = 0; i < boxes.size(); i++) {
            g.fill(boxes.get(i));
        }
    }
    public void handleInput(){
        if(Keys.isPressed(Keys.ENTER)) {
//            gsm.setState(GameStateManager.MENU);
            eventFinish = true;
            RecorderBox.play("collect");
        }
    }

    private void eventStart() {
        eventTick++;
        if(eventTick == 1) {
            boxes.clear();
            for(int i = 0; i < 9; i++) {
                boxes.add(new Rectangle(0, i * 16, GamePanel.WIDTH, 16));
            }
        }
        if(eventTick > 1 && eventTick < 32) {
            for(int i = 0; i < boxes.size(); i++) {
                Rectangle r = boxes.get(i);
                if(i % 2 == 0) {
                    r.x -= 4;
                }
                else {
                    r.x += 4;
                }
//                System.out.println("1");
            }
        }
        if(eventTick == 33) {
            boxes.clear();
            eventStart = false;
            eventTick = 0;
        }
//        System.out.println("ticsStart:" + eventTick);
    }

    private void eventFinish() {
        eventTick++;
        if(eventTick == 1) {
            boxes.clear();
            for(int i = 0; i < 9; i++) {
                if(i % 2 == 0) boxes.add(new Rectangle(-128, i * 16, GamePanel.WIDTH, 16));
                else boxes.add(new Rectangle(128, i * 16, GamePanel.WIDTH, 16));
            }
//            RecorderBox.stop("music1");
//            RecorderBox.play("finish");
        }
        if(eventTick > 1) {
            for(int i = 0; i < boxes.size(); i++) {
                Rectangle r = boxes.get(i);
                if(i % 2 == 0) {
                    if(r.x < 0) r.x += 4;
                }
                else {
                    if(r.x > 0) r.x -= 4;
                }
            }
        }
        if(eventTick > 33) {
//            if(!RecorderBox.isPlaying("finish")) {
//                Data.setTime(player.getTicks());
//                gsm.setState(GameStateManager.GAMEOVER);
            gsm.setState(GameStateManager.PLAY);
//            }
        }

    }

}
