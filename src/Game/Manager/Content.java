package Game.Manager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

public class Content {
    public static BufferedImage[][] MENUBG = load("Resources/HUD/menuscreen.gif", 128, 144);
    public static BufferedImage[][] MENUBG2 = load("Resources/HUD/menuscreen2.gif", 128, 144);
    public static BufferedImage[][] MENUBG3 = load("Resources/HUD/menuscreen3.gif", 128, 144);
    public static BufferedImage[][] BAR = load("Resources/HUD/bar.gif", 128, 16);

    public static BufferedImage[][] PLAYER = load("Resources/Sprites/playersprites.gif", 16, 16);
    public static BufferedImage[][] MONSTER = load("Resources/Sprites/samuraisprites.gif", 16, 16);
    public static BufferedImage[][] DIAMOND = load("Resources/Sprites/diamond.gif", 16, 16);
    public static BufferedImage[][] SPARKLE = load("Resources/Sprites/sparkle.gif", 16, 16);
    public static BufferedImage[][] ITEMS = load("Resources/Sprites/items.gif", 16, 16);

    public static BufferedImage[][] font = load("Resources/HUD/font.gif", 8, 8);
    public static BufferedImage[][] LOVE = load("Resources/Sprites/Love.gif", 16, 16);


    public static BufferedImage[][] load(String s, int w, int h) {
        BufferedImage[][] ret;
        try {

            BufferedImage spritesheet = ImageIO.read(new FileInputStream(s));

            int width = spritesheet.getWidth() / w;
            int height = spritesheet.getHeight() / h;
            ret = new BufferedImage[height][width];
            for(int i = 0; i < height; i++) {
                for(int j = 0; j < width; j++) {
                    ret[i][j] = spritesheet.getSubimage(j * w, i * h, w, h);
                }
            }
            return ret;
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("Error loading graphics.");
            System.exit(0);
        }
        return null;
    }
    public static void drawString(Graphics2D g, String s, int x, int y) {
        s = s.toUpperCase();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == 47) c = 36; // slash /
            if(c == 58) c = 37; // colon :
            if(c == 32) c = 38; // space
            if (c == 33) c = 39;// !
            if(c >= 65 && c <= 90) c -= 65; // letters
            if(c >= 48 && c <= 57) c -= 22; // numbers
            int row = c / font[0].length;
            int col = c % font[0].length;
            g.drawImage(font[row][col], x + 8 * i, y, null);
        }
    }
}
