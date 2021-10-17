package Game.Main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Begin project 2019 - 2021");
        final float versia = 0.438f;
        final String relize = "Alpha: ";

        JFrame window = new JFrame("MiniQuest-TheFinalGame" + " (" + relize + versia + ")");

        window.add(new GamePanel());
        window.setIconImage(new ImageIcon("Resources/ico.png").getImage());

        window.setResizable(false);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
