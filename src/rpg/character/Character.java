package rpg.character;

import java.util.Random;
import rpg.character.CharacterInfo;

public abstract class Character {
    private String name;
    private String description;
    private int maxHealth;
    private int maxMana;
    private int health;
    private int mana;
    private int attack;
    private int defence;
    private int strength;
    private int intelligence;
    private int fitness;
    private int dexterity;
    private String AI;
    private int level;
    private int turnPoints;
    private CharacterInfo info;
    private Random random;

    public Character(String name, String description, int maxHealth, int maxMana, int attack, int defence, int strength, int intelligence, int fitness, int dexterity) {
        this.name = name;
        this.description = description;
        this.maxHealth = maxHealth;
        this.maxMana = maxMana;
        this.health = maxHealth;
        this.mana = maxMana;
        this.attack = attack;
        this.defence = defence;
        this.strength = strength;
        this.intelligence = intelligence;
        this.fitness = fitness;
        this.dexterity = dexterity;
        this.random = new Random();
    }

    public double updateTurnPoints() {
        int randomBuffer = this.random.nextInt(100);
        this.turnPoints += this.fitness + randomBuffer / 100;
        if (this.turnPoints > 100) {
            this.turnPoints = 100;
        }
        return this.turnPoints;
    }

    public int calculateDamage(int enemyDefence) {
        int damageBufferMax = 20;
        double damage = this.attack + 0.5 * this.strength - 0.5 * enemyDefence;
        if ((damage = Math.ceil(this.variance(damage, damageBufferMax, true))) < 1.0) {
            damage = 1.0;
        }
        return (int)damage;
    }

    public int hit(int enemyDex, int enemyFitness) {
        int maxChance = 95;
        int minChance = 35;
        double hitChance = 75.0;
        hitChance += (this.dexterity - enemyDex) + 0.5 * (this.fitness - enemyFitness);
        if ((hitChance = this.variance(hitChance, 10, true)) > maxChance) {
            hitChance = maxChance;
        } else if (hitChance < minChance) {
            hitChance = minChance;
        }
        return (int)Math.ceil(hitChance);
    }

    public void damage(double damageTaken) {
        this.health = (int)((double)this.health - damageTaken);
        System.out.println(String.valueOf(this.name) + " has taken " + damageTaken + " points of damage!");
        if (this.health <= 0) {
            System.out.println(String.valueOf(this.name) + " has died!");
            System.exit(0);
        }
    }

    public boolean flee(int enemyLevel, int enemyFitness) {
        int maxChance = 95;
        int minChance = 5;
        int baseChance = 65;
        int fleeChance = baseChance - 3 * (enemyFitness - this.fitness);
        if (fleeChance > maxChance) {
            fleeChance = maxChance;
        } else if (fleeChance < minChance) {
            fleeChance = minChance;
        }
        int fleeRoll = this.random.nextInt(100);
        if (fleeRoll <= fleeChance) {
            System.exit(0);
            return true;
        }
        return false;
    }

    public void heal() {
        int healCost = 5;
        int healAmount = 20;
        if (this.getMana() >= healCost) {
            healAmount = (int)this.variance(healAmount, 20, true);
            this.health += healAmount;
            if (this.health > this.maxHealth) {
                this.health = this.maxHealth;
            }
            this.mana -= healCost;
            System.out.println(name + " healed for " + healAmount + ".");
        } else {
            System.out.println("Not enough mana!");
        }
    }
    
	public int regenerateMana()
	{
		//TODO different calculations based on stances
		int regen = intelligence/4;
		int minRegen=2;
		int maxRegen=50;
		if (regen<minRegen){
			regen=minRegen;
		}
		if(regen>maxRegen){
			regen=maxRegen;
		}
			
		return regen;
	}

    private double variance(double base, int variance, boolean negativeAllowed) {
        if (variance < 1) {
            variance = 1;
        } else if (variance > 100) {
            variance = 100;
        }
        int buffer = this.random.nextInt(++variance);
        if (this.random.nextBoolean() && negativeAllowed) {
            buffer = - buffer;
        }
        double percent = (float)(100 - buffer) / 100.0f;
        double variedValue = base * percent;
        return variedValue;
    }

    public void updateAttack(double attackModifier) {
        this.info.updateAttack(attackModifier);
    }

    public void updateDefence(double defenceModifier) {
        this.info.updateDefence(defenceModifier);
    }

    public void updateStrength(double strengthModifier) {
        this.info.updateStrength(strengthModifier);
    }

    public void updateIntelligence(double intelligenceModifier) {
        this.info.updateIntelligence(intelligenceModifier);
    }

    public void updateFitness(double fitnessModifier) {
        this.info.updateFitness(fitnessModifier);
    }

    public void updateDexterity(double dexterityModifier) {
        this.info.updateDexterity(dexterityModifier);
    }

    public void updateMaxHealth(double maxHealthModifier) {
        this.info.updateMaxHealth(maxHealthModifier);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAttack() {
        return this.attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getMaxMana() {
        return this.maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public int getDefence() {
        return this.defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getStrength() {
        return this.strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getIntelligence() {
        return this.intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getFitness() {
        return this.fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public int getDexterity() {
        return this.dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public double getTurnPoints() {
        return this.turnPoints;
    }

    public void setTurnPoints(int turnPoints) {
        this.turnPoints = turnPoints;
    }

    public int getLevel() {
        return this.level;
    }

    public int getHealth() {
        return this.health;
    }

    public int getMana() {
        return this.mana;
    }

    public int getBaseMaxHealth() {
        return this.info.getBaseMaxHealth();
    }

    public void setBaseMaxHealth(int baseMaxHealth) {
        this.info.setBaseMaxHealth(baseMaxHealth);
    }

    public int getEquipMaxHealth() {
        return this.info.getEquipMaxHealth();
    }

    public void setEquipMaxHealth(int equipMaxHealth) {
        this.info.setEquipMaxHealth(equipMaxHealth);
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
}

