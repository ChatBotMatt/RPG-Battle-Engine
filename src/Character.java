import java.util.ArrayList;
import java.util.Random;

import mam95.tools.IO.IO;

public class Character {

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
	private int speed; //Used for turn order.
	private int dexterity;
	
	//Base stats, affected by levelling.
	private int baseMaxHealth;
	private int baseAttack;
	private int baseDefence;
	private int baseStrength;
	private int baseIntelligence;
	private int baseSpeed;
	private int baseDexterity;
	
	//Stat bonuses given by equipment. E.g. A longsword gives +2 to speed.
	private int equipMaxHealth;
	private int equipAttack;
	private int equipDefence;
	private int equipStrength;
	private int equipIntelligence;
	private int equipSpeed;
	private int equipDexterity;

	private String AI;
	private long experience;
	private int level;
	private int killCount;
	private int deathCount;
	private double damageDealt;
	private double damageTaken;
	private long money;
	private double turnPoints; //At 100, they can act.

	private Random random;
	private Game gameEngine;
	private IO input; //Handles I/O operations.
	
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
			case("Speed"):
				speed = Integer.parseInt(field[1]);
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
		gameEngine = game;
		input = new IO();
	}
	
	/**
	 * Builds a character based on input stats.
	 * @param name
	 * @param maxHealth
	 * @param attack
	 * @param defence
	 * @param strength
	 * @param intelligence
	 * @param speed
	 * @param dexterity
	 */
	public Character(String name, int maxHealth, int attack, int defence, int strength, int intelligence, int speed, int dexterity, Game game) {
		this.name = name;
		this.setMaxHealth(maxHealth);
		health = maxHealth;
		this.attack = attack;
		this.defence = defence;
		this.strength = strength;
		this.intelligence = intelligence;
		this.speed = speed;
		this.dexterity = dexterity;
		
		random = new Random();
		gameEngine = game;
		input = new IO();
	}
	
	public Character() {
		
	}

	/**
	 * Called constantly, updates turnPoints by (roughly) speed, with a tiny modifier to help prevent two characters reaching 100 at the same time.
	 * 
	 * @return True if they can take their turn, false if not.
	 */
	public boolean updateTurnPoints(){
		int randomBuffer = random.nextInt(100);
		turnPoints+=(speed+(randomBuffer/100));
		if (turnPoints > 100){ //Stops it from going over the max value.
			turnPoints = 100;
			return true;
		}
		return false;
	}
	
	public String chooseAction(){
		mana+=manaRegen();
		System.out.println(name + " has recovered " + manaRegen() + " mana");
		String[] options = {"Attack", "Heal", "Flee"};
		int actionIndex = input.printMenu(options);
		String action = options[actionIndex];
		return action;
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
	public void attack(Character target){
		int targetDefence = target.getDefence();
		double damage = Math.ceil((attack + (0.5*strength) - (0.5*targetDefence)));
		
		int damageBufferMax = 20;
		int damageBufferPercent = 100/damageBufferMax;
		int damageRollBound = (int)(damage/damageBufferPercent);
		if (damageRollBound < 1){
			damageRollBound = 1;
		}
		
		
		int damageRoll = random.nextInt(++damageRollBound); //Gets up to damageBufferPercent% (20%) of the base damage as a buffer.
		damageRoll = input.reverseSign(damageRoll, false); //Returns either damageRoll, or -damageRoll, with equal probability.
		damage += damageRoll; //Changes damage appropriately.
		
		if (damage <= 0){ //Stops negative damage.
			damage = 1;
		}
		if (hit(target.getDexterity(), target.getSpeed())){
			target.damage(damage); //Inflicts damage to enemy.
		}
	}
	
	/**
	 * Inflicts damage upon the character, based on damage taken, and kills them if needed. Death currently stops the game.
	 * 
	 * @param damageTaken The amount of damage taken.
	 */
	private void damage(double damageTaken){
		health-= damageTaken;
		System.out.println(name + " has taken " + damageTaken + " points of damage!");
		if (health <= 0){
			System.out.println(name + " has died!");
			System.exit(0);
		}
	}
	
	/**
	 * Checks whether they can flee the enemy, based on speed stats. If they can, drops money based on level stats. 
	 * 
	 * @param enemyLevel
	 * @param enemySpeed
	 * 
	 * @return True if they can flee.
	 */
	public boolean flee(int enemyLevel, int enemySpeed){
		//TODO Buffs/Statuses affect it
		int maxChance = 95;
		int minChance = 5;
		int baseChance = 65;
		
		int fleeChance = baseChance - (3*(enemySpeed - speed));
		if (fleeChance > maxChance){
			fleeChance = maxChance;
		}
		else if (fleeChance < minChance){
			fleeChance = minChance;
		}

		int fleeRoll = random.nextInt(100);
		if (fleeRoll <= fleeChance){
			//TODO Drop money upon fleeing, based on level and your luck.
			int moneyDropModifier = (level - enemyLevel);
			int randomMoney = (1 + random.nextInt(10));
			long moneyDrop = (moneyDropModifier*randomMoney) + (money/randomMoney);
			if (moneyDrop > money){
				moneyDrop = money;
			}
			System.out.println("In " + name + "'s haste to flee, they dropped " + moneyDrop + " gold coins!");	
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
		if (mana >= healCost){
			int randomBound = (int) healAmount/5;
			int randomHeal = random.nextInt(randomBound);
			randomHeal = input.reverseSign(randomHeal, false);
			healAmount += randomHeal;
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
	
	/**
	 * Called when equipment changes, or a stat-affecting action (E.g. (de)buff, tactics) occurs.
	 */
	public void updateAttack(double attackModifier){
		attack = (int)(attackModifier*(baseAttack + equipAttack));
	}
	
	public void updateDefence(double defenceModifier){
		defence = (int)(defenceModifier*(baseDefence + equipDefence));
	}
	
	public void updateStrength(double strengthModifier){
		strength = (int)(strengthModifier*(baseStrength + equipStrength));
	}
	
	public void updateIntelligence(double intelligenceModifier){
		intelligence = (int)(intelligenceModifier*(baseIntelligence + equipIntelligence));
	}
	
	public void updateSpeed(double speedModifier){
		speed = (int)(speedModifier*(baseSpeed + equipSpeed));
	}
	
	public void updateDexterity(double dexterityModifier){
		dexterity = (int)(dexterityModifier*(baseDexterity + equipDexterity));
	}
	
	public void updateMaxHealth(double maxHealthModifier){
		setMaxHealth((int)(maxHealthModifier*(baseMaxHealth + equipMaxHealth)));
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

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getDexterity() {
		return dexterity;
	}

	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
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
		return baseMaxHealth;
	}

	public void setBaseMaxHealth(int baseMaxHealth) {
		this.baseMaxHealth = baseMaxHealth;
	}

	public int getEquipMaxHealth() {
		return equipMaxHealth;
	}

	public void setEquipMaxHealth(int equipMaxHealth) {
		this.equipMaxHealth = equipMaxHealth;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	private boolean hit(int enemyDex, int enemySpeed/*, int enemyStatus*/){
		//TODO Add increase/decrease based on status effects (frozen, paralyzed...)
		int maxChance = 95;
		int minChance = 20;
		double hitChance = 75;
		hitChance=hitChance+(dexterity-enemyDex)+(0.5*(speed-enemySpeed));
		
		if (hitChance > maxChance){
			hitChance = maxChance;
		}
		else if (hitChance < minChance){
			hitChance= minChance;
		}
		
		int randHit = random.nextInt(100);
		
		if (hitChance <= randHit){
			System.out.println(name + " missed!");
			return false;
		}
		return true;
	}
	
	private int manaRegen()
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

	public Character selectTarget() {
		// TODO Choose a target based on AI, or bring up menu.
		return null;
	}
	
}
