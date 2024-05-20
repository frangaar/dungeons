package mazmorra.characters;

import mazmorra.Character;


public class Priest extends Character {

    public Priest(String name, String type, int lifes, int speed, int coordX, int coordY, String direction, int attackPoints, int lifePoints, boolean poisoned) {
        super(name, type,lifes, speed, coordX, coordY, direction, lifePoints, attackPoints, poisoned);
    }
}