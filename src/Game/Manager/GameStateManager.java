package Game.Manager;

import Game.GameState.*;

import java.awt.*;

public class GameStateManager {
    private boolean paused;
    private PauseState pauseState;

    private GameState[] gameStates;
    private int currentState;
    private int previousState;

    public static final int NUM_STATES = 6;
    public static final int INTRO = 0;
    public static final int MENU = 1;
    public static final int PLAY = 2;
    public static final int GAMEOVER = 3;
    public static final int DIALOG = 4;
    public static final int TITLES = 5;


    public GameStateManager() {
        RecorderBox.init();

        paused = false;
        pauseState = new PauseState(this);

        gameStates = new GameState[NUM_STATES];
        setState(INTRO);
    }

    public void setState(int i) {
        previousState = currentState;
        unloadState(previousState);
        currentState = i;
        if(i == INTRO) {
            gameStates[i] = new IntroState(this);
            gameStates[i].init();
        }
        else if(i == MENU) {
            gameStates[i] = new MenuState(this);
            gameStates[i].init();
        }
        else if(i == PLAY) {
            gameStates[i] = new PlayState(this);
            gameStates[i].init();
        }
        else if(i == GAMEOVER) {
            gameStates[i] = new GameOverState(this);
            gameStates[i].init();
        }
        else if (i == DIALOG){
            gameStates[i] = new DialogState(this);
            gameStates[i].init();
        }
        else if (i == TITLES){
            gameStates[i] = new TitlesState(this);
            gameStates[i].init();
        }
    }
    public void unloadState(int i) {
        gameStates[i] = null;
    }
    public void setPaused(boolean b) {
        paused = b;
    }
    public void update() {
        if(paused) {
            pauseState.update();
        }
        else if(gameStates[currentState] != null) {
            gameStates[currentState].update();
        }
    }
    public void draw(Graphics2D g) {
        if(paused) {
            pauseState.draw(g);
        }
        else if(gameStates[currentState] != null) {
            gameStates[currentState].draw(g);
        }
    }
}
