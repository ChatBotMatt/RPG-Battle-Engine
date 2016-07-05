package rpg.character;

import rpg.character.Character;

public class Enemy
extends Character {
    public Enemy(String name, String description, int maxHealth, int maxMana, int attack, int defence, int strength, int intelligence, int fitness, int dexterity) {
        super(name, description, maxHealth, maxMana, attack, defence, strength, intelligence, fitness, dexterity);
    }
}

