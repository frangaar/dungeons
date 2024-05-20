package mazmorra.items;

import mazmorra.Dungeon;

import javax.swing.*;

public class Skull extends Dungeon {

    public Skull(JLabel label){
        super(label);
    }

    public Skull(String type, JProgressBar healthBar, JLabel label, Timer timer, String direction, String image, int width, int height, int valueX, int valueY, int speed, int lifePoints) {
        super(type, healthBar,label, timer, direction, image, width, height, valueX, valueY, speed, lifePoints);
    }

    public void start(){
        this.timer.start();
    }

    public void stop(){
        this.timer.stop();
    }


}
