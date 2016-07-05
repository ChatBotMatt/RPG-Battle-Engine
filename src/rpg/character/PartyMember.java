package rpg.character;

import rpg.character.Character;

public class PartyMember
extends Character {
    private long experience = 0;
    private int killCount = 0;
    private int deathCount = 0;
    private double damageDealt = 0.0;
    private double damageTaken = 0.0;

    public PartyMember() {
        super("Blankles", "A default base", 100, 20, 5, 5, 5, 5, 5, 5);
    }

    public PartyMember(String name, String description, int maxHealth, int maxMana, int attack, int defence, int strength, int intelligence, int fitness, int dexterity) {
        super(name, description, maxHealth, maxMana, attack, defence, strength, intelligence, fitness, dexterity);
    }
}

