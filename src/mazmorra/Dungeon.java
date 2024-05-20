package mazmorra;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Objects;

public abstract class Dungeon {

    protected String type;
    protected JProgressBar healthBar;
    protected JLabel label;
    protected Timer timer;
    protected String direction;
    protected String image;
    protected int width;
    protected int height;
    protected int valueX;
    protected int valueY;
    protected int speed;
    protected int lifePoints;


    public Dungeon(JLabel label){
        this.label = label;
    }
    public Dungeon(String type, JProgressBar healthBar, JLabel label, Timer timer, String direction, String image, int width, int height, int valueX, int valueY, int speed, int lifePoints) {
        this.type = type;
        this.healthBar = healthBar;
        this.label = label;
        this.setTimer(timer);
        this.setDirection(direction);
        this.image = image;
        this.width = width;
        this.height = height;
        this.valueX = valueX;
        this.valueY = valueY;
        this.speed = speed;
        this.lifePoints = lifePoints;
    }

    public String getType() {
        return type;
    }

    public void setTimer(Timer timer) {
        if(timer != null){
            this.timer = timer;
        }
    }

    public void setDirection(String direction) {
        if(!direction.equals("")) {
            this.direction = direction;
        }
    }

    public String getDirection() {
        return direction;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public void setValueX(int valueX) {
        this.valueX = valueX;
    }

    public void setValueY(int valueY) {
        this.valueY = valueY;
    }

    public int getValueX() {
        return valueX;
    }

    public int getValueY() {
        return valueY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSpeed() {
        return speed;
    }

    public String getImage() {
        return image;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    public JProgressBar getHealthBar() {
        return healthBar;
    }

    public JLabel create(int id, String image){

        JLabel item = new JLabel();

        item.setSize(32,32);

        this.image = image;

        URL url = Dungeon.class.getResource(this.image);
        ImageIcon iconItem = new ImageIcon(url);
        Icon icon = new ImageIcon(iconItem.getImage().getScaledInstance(item.getWidth(),item.getHeight(), Image.SCALE_DEFAULT));
        item.setIcon(icon);
        if(id == 37 || id == 74){
            item.setToolTipText("entry");
        }else if(id == 728 || id == 765){
            item.setToolTipText("exit");
        }else{
            item.setToolTipText(this.type);
        }
        item.setName(String.valueOf(id));

        return item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dungeon dungeon = (Dungeon) o;
        return label.equals(dungeon.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }
}
