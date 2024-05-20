package mazmorra.characters;

import mazmorra.Character;

import javax.swing.*;

public class Wizard extends Character {

    public Wizard(String name, String type, int lifes, int speed, int coordX, int coordY, String direction, int attackPoints, int lifePoints, boolean poisoned) {
        super(name, type,lifes, speed, coordX, coordY, direction, lifePoints, attackPoints, poisoned);
    }
}

