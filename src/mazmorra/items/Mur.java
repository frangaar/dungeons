package mazmorra.items;

import mazmorra.Dungeon;

import javax.swing.*;

public class Mur extends Dungeon {

    public Mur(String type, JProgressBar healthBar, JLabel label, Timer timer, String direction, String image, int width, int height, int valueX, int valueY, int speed, int lifePoints) {
        super(type, healthBar,label, timer, direction, image, width, height, valueX, valueY, speed, lifePoints);
    }
}
