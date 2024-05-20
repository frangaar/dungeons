package mazmorra;

import javax.swing.*;
import java.util.ArrayList;

public abstract class Character {

    protected String name;
    protected String type;
    protected int lifes;
    protected int gold;
    protected int speed;
    protected ArrayList<JLabel> objects;
    protected int coordX;
    protected int coordY;

    protected String direction;

    protected int attackPoints;
    protected int lifePoints;
    protected boolean poisoned;


    public Character(String name, String type, int lifes, int speed, int coordX, int coordY, String direction, int lifePoints, int attackPoints, boolean poisoned) {
        this.name = name;
        this.type = type;
        this.lifes = lifes;
        this.gold = 0;
        this.speed = speed;
        this.objects = null;
        this.coordX = coordX;
        this.coordY = coordY;
        this.direction = direction;
        this.attackPoints = attackPoints;
        this.lifePoints = lifePoints;
        this.poisoned = poisoned;
    }

    public void setLifes(int lifes) {
        this.lifes = lifes;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setObjects(ArrayList<JLabel> objects) {
        this.objects = objects;
    }

    public ArrayList<JLabel> getObjects() {
        return objects;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public String getName() {
        return name;
    }

    public String getType() { return type; }

    public int getLifes() {
        return lifes;
    }

    public int getGold() {
        return gold;
    }

    public int getSpeed() {
        return speed;
    }

    public String getDirection() {
        return direction;
    }

    public int getAttackPoints() {
        return attackPoints;
    }

    public boolean isPoisoned() {
        return poisoned;
    }

    public void setPoisoned(boolean poisoned) {
        this.poisoned = poisoned;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    public String getInfo(){

        return this.name + "," + this.type + "," + this.lifes + "," + this.gold;
    }
}
