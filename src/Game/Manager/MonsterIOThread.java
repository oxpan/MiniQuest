package Game.Manager;


import Game.Entity.Monster;
import Game.GameState.PlayState;

import java.awt.*;

public class MonsterIOThread extends Thread{
    private static boolean running;
    private static boolean isIntelligence;
    private final int FPS = 30;
    private final int TARGET_TIME = 1000 / FPS;

    private int intTest = 0;


    @Override
    public void run(){
        long start;
        long elapsed;
        long wait;

        init();

        while (running){

            start = System.nanoTime();
            if (isIntelligence) {
                intTest++;
                update();
//                System.out.println("n = " + intTest);
            }

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
    private void init(){
        running = true;
        isIntelligence = true;
    }
    public static void setRunning(boolean b){
        running = b;
    }
    public static void setIntelligence(boolean b){
        isIntelligence = b;
    }


    private void update(){
//        PlayState.monsters.get(1).update();
        for (int i = 0; i < PlayState.monsters.size();i++){
            PlayState.monsters.get(i).update();
        }
//        System.out.println("2 поток работает!");


    }

}
