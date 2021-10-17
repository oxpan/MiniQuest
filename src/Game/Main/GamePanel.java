package Game.Main;

import Game.Manager.GameStateManager;
import Game.Manager.Keys;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;


public class GamePanel extends JPanel implements Runnable, KeyListener {

    public static final int WIDTH = 128;
    public static final int HEIGHT = 128;
    public static final int HEIGHT2 = HEIGHT + 16;
    public static final int SCALE = 3;

    private Thread thread;
    private boolean running;
    private final int FPS = 30;
    private final int TARGET_TIME = 1000 / FPS;

    //рисование всякой всячины
    private BufferedImage image;
    private Graphics2D g;

    private GameStateManager gsm;



    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT2 * SCALE));
        setFocusable(true);
        requestFocus();
    }

    public void addNotify() {
        super.addNotify();
        if(thread == null) {
            addKeyListener(this);
            thread = new Thread(this);
            thread.start();
        }
    }


    public void run(){
        init();

        long start;
        long elapsed;
        long wait;

        // game loop
        while(running) {

            start = System.nanoTime();

            update();
            draw();
            drawToScreen();

            elapsed = System.nanoTime() - start;

            wait = TARGET_TIME - elapsed / 1000000;
            if(wait < 0) wait = TARGET_TIME;

            try {
                Thread.sleep(wait);
            }
            catch(Exception e) {
                e.printStackTrace();
            }

        }
    }
    private void init() {
        running = true;
        image = new BufferedImage(WIDTH, HEIGHT2, 1);
        g = (Graphics2D) image.getGraphics();
        gsm = new GameStateManager();
    }

    private void update(){
        gsm.update();
        Keys.update();
    }

    private void draw() {
        gsm.draw(g);
    }
    private void drawToScreen() {
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT2 * SCALE, null);
        g2.dispose();
    }

    public void keyTyped(KeyEvent key) {}
    public void keyPressed(KeyEvent key) {
        Keys.keySet(key.getKeyCode(), true);
    }
    public void keyReleased(KeyEvent key) {
        Keys.keySet(key.getKeyCode(), false);
    }
}
