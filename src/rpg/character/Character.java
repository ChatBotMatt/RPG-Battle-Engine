package rpg.character;

import java.util.ArrayList;
import java.util.Random;

import rpg.engine.Game;

public abstract class Character {

	private String name;
	private String description;
	private int maxHealth;
	private int maxMana;
	private int health;
	private int mana;
	
	//Total stats, as decided by formulas based on base[Stat] and equip[Stat]. 
	private int attack;
	private int defence;
	private int strength;
	private int intelligence;
	private int fitness; //Used for turn order.
	private int dexterity;
	
	private String AI; //Wild, Domesticated, Civilised, Player
	private int level;
	private int turnPoints; //At the threshold, they can act.
	
	private CharacterInfo info;

	private Random random;
	
	/**
	 * Creates a character based on data read in by a Reader object. Iterates through characterData's stored key-value pairs, settings fields as appropriate.
	 * @param characterData Data read in by a Reader object, holds multiple key-value pairs.
	 * @param game The Game object that created it.
	 */
	public Character(ArrayList<String[]> characterData, Game game) {
		for (String[] field: characterData){
			switch(field[0]){
			case("Name"):
				name = field[1];
				break;
			case("Description"):
				description = field[1];
				break;
			case("Max Health"):
				maxHealth = Integer.parseInt(field[1]);
				health = maxHealth;
				break;
			case("Max Mana"):
				maxMana = Integer.parseInt(field[1]);
				mana = maxMana;
				break;
			case("Attack"):
				attack = Integer.parseInt(field[1]);
				break;
			case("Defence"):
				defence = Integer.parseInt(field[1]);
				break;
			case("Strength"):
				strength = Integer.parseInt(field[1]);
				break;
			case("Intelligence"):
				intelligence = Integer.parseInt(field[1]);
				break;
			case("Fitness"):
				fitness = Integer.parseInt(field[1]);
				break;
			case("Dexterity"):
				dexterity = Integer.parseInt(field[1]);
				break;
			default:
				System.out.println("Uh oh!");
				break;
				
			}
		}
		random = new Random();
	}
	
	/**
	 * Builds a character based on input stats.
	 * @param name
	 * @param description
	 * @param maxHealth
	 * @param maxMana
	 * @param attack
	 * @param defence
	 * @param strength
	 * @param intelligence
	 * @param fitness
	 * @param dexterity
	 */
	public Character(String name, String description, int maxHealth, int maxMana, int attack, int defence, int strength, 
			int intelligence, int fitness, int dexterity) {
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
		
		random = new Random();
	}
	
	
	/**
	 * Called constantly, updates turnPoints by (roughly) speed, with a tiny modifier to help prevent two characters reaching 100 at the same time.
	 * 
	 * @return True if they can take their turn, false if not.
	 */
	public double updateTurnPoints(){
		int randomBuffer = random.nextInt(100);
		turnPoints+=(fitness+(randomBuffer/100));
		if (turnPoints > 100){ //Stops it from going over the max value.
			turnPoints = 100;
			
		}
		return turnPoints;
	}

	/*/**
	 * Performs a battle action against a target. Will be AI based on stats, currently oscillates evenly between attack and flee.
	 * 
	 * @param target The enemy being attacked/fleed.
	 *
	public void battleAction(/*Character target){
		
		Character target;
		if (AI.equals("support")){ //TODO Make case insensitive
			target = gameEngine.getTarget(AI, 70);
		}
		else if (AI.equals("balanced")){
			target = gameEngine.getTarget(AI, 70);
		}
		else{
			target = gameEngine.getTarget(AI, -1);
		}
		
		
		
		//boolean attack = random.nextBoolean();
		
		/*if (attack){
			if( hit(target.getDexterity(), target.getSpeed())){
				attack(target);
			}
		}
		
		else{
			flee(target.getLevel(), target.getSpeed());
		}
	}*/

	/**
	 * Attacks the target, based on their defence.
	 * 
	 * Note: Random buffer is much more distinctive at very low base damages.
	 * @param target The target to attack.
	 */
	/*public void attack(Character target){
		int damageBufferMax = 20;
		int targetDefence = target.getDefence();
		double damage = Math.ceil((attack + (0.5*strength) - (0.5*targetDefence)));
		
		damage = variance(damage,damageBufferMax,true);
		if (damage < 1){ //Stops useless/negative damage. Note: Any "negative" damage caused by absorbing an element happens in the attacked character.
			damage = 1;
		}
		if (hit(target.getLevel(), target.getFitness())){
			target.damage(damage); //Inflicts damage to enemy.
		}
	}*/
	
	/**
	 * Calculate damage based on enemy defence, return the value.
	 * @param enemyDefence The defence of the chosen target.
	 * @return damage A double representing how much damage will be dealt based on a target's defence.
	 */
	public int calculateDamage(int enemyDefence){
		int damageBufferMax = 20;
		double damage = (attack + (0.5*strength) - (0.5*enemyDefence));
		
		damage = Math.ceil(variance(damage,damageBufferMax,true));
		if (damage < 1){ //Stops useless/negative damage. Note: Any "negative" damage caused by absorbing an element happens in the attacked character.
			damage = 1;
		}
		return (int) damage;
		/*if (hit(target.getLevel(), target.getFitness())){
			target.damage(damage); //Inflicts damage to enemy.
		}*/ //TODO Add hit check in game
	}
	
	/**
	 * Calculates hit chance, based on enemy dexterity and fitness.
	 * @param enemyDex
	 * @param enemyFitness
	 * @return The ceiling of the hitChance double, the chance that the character will hit the enemy based on the given stats.
	 */
	public int hit(int enemyDex, int enemyFitness/*, int enemyStatus*/){
		//TODO Add increase/decrease based on status effects (frozen, paralyzed...)
		int maxChance = 95; //TODO base more on DEX than fitness? Fitness/2? Needs testing.
		int minChance = 35;
		double hitChance = 75;
		hitChance+=(dexterity-enemyDex)+(0.5*(fitness-enemyFitness));
		hitChance = variance(hitChance, 10, true);
		
		if (hitChance > maxChance){
			hitChance = maxChance;
		}
		else if (hitChance < minChance){
			hitChance= minChance;
		}
		return (int) (Math.ceil(hitChance));
	}
	
	/**
	 * Inflicts damage upon the character, based on damage taken, and kills them if needed. Death currently stops the game.
	 * 
	 * @param damageTaken The amount of damage taken.
	 */
	public void damage(double damageTaken){
		health-= damageTaken;
		System.out.println(name + " has taken " + damageTaken + " points of damage!");
		if (health <= 0){
			System.out.println(name + " has died!");
			System.exit(0);
		}
	}
	
	/**
	 * Checks whether they can flee the enemy, based on fitness stats. If they can, drops money based on level stats. 
	 * 
	 * @param enemyLevel
	 * @param enemyFitness
	 * 
	 * @return True if they can flee.
	 */
	public boolean flee(int enemyLevel, int enemyFitness){
		//TODO Buffs/Statuses affect it
		int maxChance = 95;
		int minChance = 5;
		int baseChance = 65;
		
		int fleeChance = baseChance - (3*(enemyFitness - fitness));
		if (fleeChance > maxChance){
			fleeChance = maxChance;
		}
		else if (fleeChance < minChance){
			fleeChance = minChance;
		}

		int fleeRoll = random.nextInt(100);
		if (fleeRoll <= fleeChance){
			//TODO Drop money upon fleeing, based on level and your luck.
			/*int moneyDropModifier = (level - enemyLevel);
			int randomMoney = (1 + random.nextInt(10));
			long moneyDrop = (moneyDropModifier*randomMoney) + (money/randomMoney);
			if (moneyDrop > money){
				moneyDrop = money;
			} //TODO Move most of this to gameEngine.
			System.out.println("In " + name + "'s haste to flee, they dropped " + moneyDrop + " gold coins!");*/	
			System.exit(0); //TODO Delete later, when we have progressed past simulating a single battle.
			return true;
		}
		return false;
		
		//Test scenarios.
		/*A = 70 - 3(x-y)
		 * 1, 1 = 65% No advantage
		 * 1, 5 = 65 + 15 = 80% Significant player advantage
		 * 5, 1 = 50% Significant mob advantage
		 * 10, 1 = 35% Extreme mob advantage
		 * 1, 10 = 95% Extreme player advantage
		 * */
	}
	
	public void heal(){
		int healCost = 5;
		int healAmount = 20;
		if (getMana() >= healCost){
			/*int randomBound = (int) healAmount/5;
			int randomHeal = random.nextInt(randomBound);
			randomHeal = input.reverseSign(randomHeal, false);
			healAmount += randomHeal;*/ 
			//TODO Check that the new variance() code works properly, before deleting this.
			healAmount = (int) variance(healAmount, 20, true);
			health += healAmount;
			if (health > maxHealth){
				health = maxHealth;
			}
			mana -= healCost;
		}
		else{
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
	
	/**
	 * Takes a value and adds a variance to it. E.g. Passing in 20 as a base and 25 as a variance will return a number between 75% and 125% of 20 (15-25)
	 * Note: Can return negative values.
	 * 
	 * @param base The base number to be varied.
	 * @param variance The amount of variance allowed (From base+variance to base-variance.
	 * @param negativeAllowed Whether the base number can decrease due to variance.
	 * 
	 * @return variedValue The new number, calculated by varying base by an amount with bounds defined by variance. Returns as a double.
	 */
	private double variance(double base, int variance, boolean negativeAllowed){
		double variedValue;
		if (variance < 1){
			variance = 1;
		}
		else if (variance > 100){
			variance = 100;
		}
		
		int buffer = random.nextInt(++variance);
		if ((random.nextBoolean()) && (negativeAllowed)){
			buffer = -buffer;
		}
		double percent = (float) ((float)(100-buffer)/100);
		variedValue = base*percent;
		return variedValue;
	}
	
	/**
	 * Called when equipment changes, or a stat-affecting action (E.g. (de)buff, tactics) occurs.
	 */
	public void updateAttack(double attackModifier){
		info.updateAttack(attackModifier);
	}
	
	public void updateDefence(double defenceModifier){
		info.updateDefence(defenceModifier);
	}
	
	public void updateStrength(double strengthModifier){
		info.updateStrength(strengthModifier);
	}
	
	public void updateIntelligence(double intelligenceModifier){
		info.updateIntelligence(intelligenceModifier);
	}
	
	public void updateFitness(double fitnessModifier){
		info.updateFitness(fitnessModifier);
	}
	
	public void updateDexterity(double dexterityModifier){
		info.updateDexterity(dexterityModifier);
	}
	
	public void updateMaxHealth(double maxHealthModifier){
		info.updateMaxHealth(maxHealthModifier);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getMaxMana() {
		return maxMana;
	}

	public void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}

	public int getDefence() {
		return defence;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}

	public int getFitness() {
		return fitness;
	}

	public void setFitness(int fitness) {
		this.fitness = fitness;
	}

	public int getDexterity() {
		return dexterity;
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
		return turnPoints;
	}

	public void setTurnPoints(int turnPoints) {
		this.turnPoints = turnPoints;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getHealth(){
		return health;
	}
	
	public int getMana(){
		return mana;
	}

	public int getBaseMaxHealth() {
		return info.getBaseMaxHealth();
	}

	public void setBaseMaxHealth(int baseMaxHealth) {
		info.setBaseMaxHealth(baseMaxHealth);
	}

	public int getEquipMaxHealth() {
		return info.getEquipMaxHealth();
	}

	public void setEquipMaxHealth(int equipMaxHealth) {
		info.setEquipMaxHealth(equipMaxHealth);
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
	
}
