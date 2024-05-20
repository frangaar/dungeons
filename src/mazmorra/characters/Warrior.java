package mazmorra.characters;

import mazmorra.Character;

import javax.swing.*;
import java.util.ArrayList;

public class Warrior extends Character {

    public Warrior(String name, String type, int lifes, int speed, int coordX, int coordY, String direction, int attackPoints, int lifePoints, boolean poisoned) {
        super(name, type,lifes, speed, coordX, coordY, direction, lifePoints, attackPoints, poisoned);
    }
}
