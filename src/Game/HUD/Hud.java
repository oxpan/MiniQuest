package Game.HUD;

import Game.Entity.Diamond;
import Game.Entity.Player;
import Game.Main.GamePanel;
import Game.Manager.Content;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Hud {
    private int yoffset;

    private BufferedImage bar;
    private BufferedImage diamond;
    private BufferedImage boat;
    private BufferedImage axe;
    private BufferedImage love;

    private Player player;

    private int numDiamonds;
    private int numHP;

    private Font font;
    private Color textColor;

    public Hud(Player p, ArrayList<Diamond> d) {

        player = p;
        numDiamonds = d.size();
        numHP = player.getHP();
        yoffset = GamePanel.HEIGHT;

        bar = Content.BAR[0][0];
        diamond = Content.DIAMOND[0][0];
        boat = Content.ITEMS[0][0];
        axe = Content.ITEMS[0][1];
        love = Content.LOVE[0][0];

        font = new Font("Arial", Font.PLAIN, 10);
        textColor = new Color(48, 241, 13);

    }

    public void draw(Graphics2D g) {

        // draw hud
        g.drawImage(bar, 0, yoffset, null);

        // draw diamond bar
        if (player.getHP() == 3) {
            g.setColor(textColor);
        }
        if (player.getHP() == 2){
            g.setColor(new Color(219, 241, 19));
        }
        if (player.getHP() == 1){
            g.setColor(new Color(241, 19, 19));
        }
//        g.fillRect(8, yoffset + 6, (int)(28.0 * player.numDiamonds() / numDiamonds), 4);
        g.fillRect(8,yoffset+6,(int)(28.8 * player.getHP() / numHP),4);

        // draw diamond amount
        g.setColor(textColor);
        g.setFont(font);
        String s = player.numDiamonds() + "/" + numDiamonds;
        Content.drawString(g, s, 40, yoffset + 3);
        if(player.numDiamonds() >= 10) g.drawImage(diamond, 80, yoffset, null);
        else g.drawImage(diamond, 72, yoffset, null);
        g.drawImage(love, 1, yoffset, null);

        // draw items
        if(player.hasBoat()) g.drawImage(boat, 100, yoffset, null);
        if(player.hasAxe()) g.drawImage(axe, 112, yoffset, null);

        // draw time
        int minutes = (int) (player.getTicks() / 1800);
        int seconds = (int) ((player.getTicks() / 30) % 60);
        if(minutes < 10) {
            if(seconds < 10) Content.drawString(g, "0" + minutes + ":0" + seconds, 85, 3);
            else Content.drawString(g, "0" + minutes + ":" + seconds, 85, 3);
        }
        else {
            if(seconds < 10) Content.drawString(g, minutes + ":0" + seconds, 85, 3);
            else Content.drawString(g, minutes + ":" + seconds, 85, 3);
        }



    }
}
