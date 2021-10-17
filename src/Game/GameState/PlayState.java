package Game.GameState;

import Game.Entity.*;
import Game.HUD.Hud;
import Game.Main.GamePanel;
import Game.Manager.*;
import Game.TileMap.TileMap;

import java.awt.*;

import java.util.ArrayList;

public class PlayState extends GameState{

    // player
    private Player player;


    // tilemap
    private TileMap tileMap;

    // diamonds
    private ArrayList<Diamond> diamonds;

    // items
    private ArrayList<Item> items;

    // sparkles
    private ArrayList<Sparkle> sparkles;

    //monster
    public static ArrayList<Monster> monsters;
    private MonsterIOThread monsterIOThread;
    static Thread monsterIO;

    // camera position
    private int xsector;
    private int ysector;
    private int sectorSize;

    // hud
    private Hud hud;

    // events
    private boolean blockInput;
    private boolean eventStart;
    private boolean eventFinish;
    private int eventTick;

    // transition box
    private ArrayList<Rectangle> boxes;
    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    public void init() {
// create lists
        diamonds = new ArrayList<Diamond>();
        sparkles = new ArrayList<Sparkle>();
        items = new ArrayList<Item>();
        monsters = new ArrayList<>();

        // load map
        tileMap = new TileMap(16);
        tileMap.loadTiles("Resources/Tilesets/testtileset.gif");
        tileMap.loadMap("Resources/Maps/testmap.map");

        // create player
        player = new Player(tileMap);


        monsterIOThread = new MonsterIOThread();
        monsterIO = new Thread(monsterIOThread);

        monsterIO.start();

        // fill lists
        populateMonster();
        populateDiamonds();
        populateItems();

        // initialize player
        player.setTilePosition(17, 17);
        player.setTotalDiamonds(diamonds.size());




        // set up camera position
        sectorSize = GamePanel.WIDTH;
        xsector = player.getx() / sectorSize;
        ysector = player.gety() / sectorSize;
        tileMap.setPositionImmediately(-xsector * sectorSize, -ysector * sectorSize);

        // load hud
        hud = new Hud(player, diamonds);

        // load music
        RecorderBox.load("Resources/Music/mainmuzick.wav", "music1");
        RecorderBox.setVolume("music1", -10);
        RecorderBox.loop("music1", 1000, 1000, RecorderBox.getFrames("music1") - 1000);
        RecorderBox.load("Resources/Music/finish1.wav", "finish");
        RecorderBox.setVolume("finish", -10);

        // load sfx
        RecorderBox.load("Resources/SFX/collect.wav", "collect");
        RecorderBox.load("Resources/SFX/mapmove.wav", "mapmove");
        RecorderBox.load("Resources/SFX/tilechange.wav", "tilechange");
        RecorderBox.load("Resources/SFX/splash.wav", "splash");
        RecorderBox.load("Resources/SFX/menu.wav", "pause");
        RecorderBox.load("Resources/SFX/yyoo.wav", "damage");
//        disconnect
        RecorderBox.load("Resources/SFX/disconnect.wav", "discon");

        // start event
        boxes = new ArrayList<Rectangle>();
        eventStart = true;
        eventStart();
        Data.setIsWather(false);
    }
    private void populateMonster(){
        Monster monster;

        monster = new Monster(tileMap, player);
        monster.setTilePosition(27,19);
        monster.setPutXY1(27,18);
        monster.setPutXY2(27,21);
        monsters.add(monster);
        monster = new Monster(tileMap, player);
        monster.setTilePosition(28,27);
        monster.setPutXY1(27,28);
        monster.setPutXY2(30,25);
        monsters.add(monster);
        monster = new Monster(tileMap, player);
        monster.setTilePosition(12,34);
        monster.setPutXY1(10,34);
        monster.setPutXY2(14,34);
        monsters.add(monster);
        monster = new Monster(tileMap, player);
        monster.setTilePosition(12,18);
        monster.setPutXY1(14,17);
        monster.setPutXY2(11,20);
        monsters.add(monster);
        monster = new Monster(tileMap, player);
        monster.setTilePosition(14,4);
        monster.setPutXY1(14,6);
        monster.setPutXY2(14,3);
        monsters.add(monster);
        monster = new Monster(tileMap, player);
        monster.setTilePosition(4,7);
        monster.setPutXY1(5,7);
        monster.setPutXY2(3,8);
        monsters.add(monster);
        monster = new Monster(tileMap, player);
        monster.setTilePosition(3,23);
        monster.setPutXY1(5,23);
        monster.setPutXY2(2,22);
        monsters.add(monster);
        monster = new Monster(tileMap, player);
        monster.setTilePosition(4,31);
        monster.setPutXY1(2,31);
        monster.setPutXY2(6,31);
        monsters.add(monster);
        monster = new Monster(tileMap, player);
        monster.setTilePosition(35,22);
        monster.setPutXY1(33,22);
        monster.setPutXY2(37,22);
        monsters.add(monster);
        monster = new Monster(tileMap, player);
        monster.setTilePosition(35,23);
        monster.setPutXY1(37,23);
        monster.setPutXY2(33,23);
        monsters.add(monster);
        monster = new Monster(tileMap, player);
        monster.setTilePosition(35,24);
        monster.setPutXY1(33,24);
        monster.setPutXY2(37,24);
        monsters.add(monster);
        monster = new Monster(tileMap, player);
        monster.setTilePosition(35,3);
        monster.setPutXY1(36,2);
        monster.setPutXY2(34,4);
        monsters.add(monster);

    }
    private void watherPopularMonsters(){
        Monster monster;
        monster = new Monster(tileMap, player);
        monster.setTilePosition(9,28);
        monster.setPutXY1(10,26);
        monster.setPutXY2(9,29);
        monsters.add(monster);
        monster = new Monster(tileMap, player);
        monster.setTilePosition(34,36);
        monster.setPutXY1(34,38);
        monster.setPutXY2(34,34);
        monsters.add(monster);

        System.out.println("water");
    }

    private void populateDiamonds() {

        Diamond d;

        d = new Diamond(tileMap);
        d.setTilePosition(20, 20);
        d.addChange(new int[] { 23, 19, 1 });
        d.addChange(new int[] { 23, 20, 1 });
        diamonds.add(d);
        d = new Diamond(tileMap);
        d.setTilePosition(12, 36);
        d.addChange(new int[] { 31, 17, 1 });
        diamonds.add(d);
        d = new Diamond(tileMap);
        d.setTilePosition(28, 4);
        d.addChange(new int[] {27, 7, 1});
        d.addChange(new int[] {28, 7, 1});
        diamonds.add(d);
        d = new Diamond(tileMap);
        d.setTilePosition(4, 34);
        d.addChange(new int[] { 31, 21, 1 });
        diamonds.add(d);

        d = new Diamond(tileMap);
        d.setTilePosition(28, 19);
        diamonds.add(d);
        d = new Diamond(tileMap);
        d.setTilePosition(35, 26);
        diamonds.add(d);
        d = new Diamond(tileMap);
        d.setTilePosition(38, 36);
        diamonds.add(d);
        d = new Diamond(tileMap);
        d.setTilePosition(27, 28);
        diamonds.add(d);
        d = new Diamond(tileMap);
        d.setTilePosition(20, 30);
        diamonds.add(d);
        d = new Diamond(tileMap);
        d.setTilePosition(14, 25);
        diamonds.add(d);
        d = new Diamond(tileMap);
        d.setTilePosition(4, 21);
        diamonds.add(d);
        d = new Diamond(tileMap);
        d.setTilePosition(9, 14);
        diamonds.add(d);
        d = new Diamond(tileMap);
        d.setTilePosition(4, 3);
        diamonds.add(d);
        d = new Diamond(tileMap);
        d.setTilePosition(20, 14);
        diamonds.add(d);
        d = new Diamond(tileMap);
        d.setTilePosition(13, 20);
        diamonds.add(d);

    }

    private void populateItems() {

        Item item;

        item = new Item(tileMap);
        item.setType(Item.AXE);
        item.setTilePosition(26, 37);
        items.add(item);

        item = new Item(tileMap);
        item.setType(Item.BOAT);
        item.setTilePosition(12, 4);
        items.add(item);

    }

    public void update() {
// check keys
        handleInput();

        // check events

        if(eventStart) eventStart();
        if(eventFinish) eventFinish();

        if(player.numDiamonds() == player.getTotalDiamonds()) {
            eventFinish = blockInput = true;
            Data.setIsDead(false);
        }
        if (player.getHP() == 0){
            eventFinish = blockInput = true;
            Data.setIsDead(true);
        }
        if (Data.getWather()==true){
            watherPopularMonsters();
            Data.setIsWather(false);
        }

        // update camera
        int oldxs = xsector;
        int oldys = ysector;
        xsector = player.getx() / sectorSize;
        ysector = player.gety() / sectorSize;
        tileMap.setPosition(-xsector * sectorSize, -ysector * sectorSize);
        tileMap.update();

        if(oldxs != xsector || oldys != ysector) {
            RecorderBox.play("mapmove");
        }

        if(tileMap.isMoving()) return;

        // update player
        player.update();
//        monster.update();//для потока потом

        // update diamonds
        for(int i = 0; i < diamonds.size(); i++) {

            Diamond d = diamonds.get(i);
            d.update();

            // player collects diamond
            if(player.intersects(d)) {

                // remove from list
                diamonds.remove(i);
                i--;

                // increment amount of collected diamonds
                player.collectedDiamond();

                // play collect sound
                RecorderBox.play("collect");

                // add new sparkle
                Sparkle s = new Sparkle(tileMap);
                s.setPosition(d.getx(), d.gety());
                sparkles.add(s);

                // make any changes to tile map
                ArrayList<int[]> ali = d.getChanges();
                for(int[] j : ali) {
                    tileMap.setTile(j[0], j[1], j[2]);
                }
                if(ali.size() != 0) {
                    RecorderBox.play("tilechange");
                }

            }
        }

        // update sparkles
        for(int i = 0; i < sparkles.size(); i++) {
            Sparkle s = sparkles.get(i);
            s.update();
            if(s.shouldRemove()) {
                sparkles.remove(i);
                i--;
            }
        }

        // update items
        for(int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if(player.intersects(item)) {
                items.remove(i);
                i--;
                item.collected(player);
                RecorderBox.play("collect");
                Sparkle s = new Sparkle(tileMap);
                s.setPosition(item.getx(), item.gety());
                sparkles.add(s);
            }
        }
    }

    public void draw(Graphics2D g) {
        // draw tilemap
        tileMap.draw(g);

        // draw player
        player.draw(g);
        for (Monster m : monsters){
            m.draw(g);
        }
        // для потока потом

        // draw diamonds
        for(Diamond d : diamonds) {
            d.draw(g);
        }

        // draw sparkles
        for(Sparkle s : sparkles) {
            s.draw(g);
        }

        // draw items
        for(Item i : items) {
            i.draw(g);
        }

        // draw hud
        hud.draw(g);

        // draw transition boxes
        g.setColor(java.awt.Color.BLACK);
        for(int i = 0; i < boxes.size(); i++) {
            g.fill(boxes.get(i));
        }
    }

    public void handleInput() {
        if(blockInput) return;
        if(Keys.isPressed(Keys.ESCAPE)) {
            RecorderBox.stop("music1");
            gsm.setPaused(true);
            MonsterIOThread.setIntelligence(false);
            RecorderBox.play("pause");
        }

        if(Keys.isDown(Keys.LEFT)) player.setLeft();
        if(Keys.isDown(Keys.RIGHT)) player.setRight();
        if(Keys.isDown(Keys.UP)) player.setUp();
        if(Keys.isDown(Keys.DOWN)) player.setDown();
        if(Keys.isPressed(Keys.SPACE)) player.setAction();
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
            RecorderBox.stop("music1");
            RecorderBox.play("finish");
        }
        MonsterIOThread.setRunning(false);
        monsterIO.stop();

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
            if(!RecorderBox.isPlaying("finish")) {
                Data.setTime(player.getTicks());
                gsm.setState(GameStateManager.GAMEOVER);
            }
        }
//        System.out.println("ticsStop:" + eventTick);
    }



}
