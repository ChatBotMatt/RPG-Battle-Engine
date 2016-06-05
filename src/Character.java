import java.util.ArrayList;
import java.util.Random;

public class Character {

	private String name;
	private String description;
	private int maxHealth;
	private int health;
	
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
	
	private long experience;
	private int level;
	private int killCount;
	private int deathCount;
	private double damageDealt;
	private double damageTaken;
	private long money;
	private double turnPoints; //At 100, they can act.

	private Random random;
	
	/**
	 * Creates a character based on data read in by a Reader object. Iterates through characterData's stored key-value pairs, settings fields as appropriate.
	 * @param characterData Data read in by a Reader object, holds multiple key-value pairs.
	 */
	public Character(ArrayList<String[]> characterData) {
		for (String[] field: characterData){
			switch(field[0]){
			case("Name"):
				name = field[1];
				break;
			case("Description"):
				description = field[1];
				break;
			case("Max Health"):
				setMaxHealth(Integer.parseInt(field[1]));
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
	 * @param maxHealth
	 * @param attack
	 * @param defence
	 * @param strength
	 * @param intelligence
	 * @param speed
	 * @param dexterity
	 */
	public Character(String name, int maxHealth, int attack, int defence, int strength, int intelligence, int speed, int dexterity) {
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
	
	/**
	 * Performs a battle action against a target. Will be AI based on stats, currently oscillates evenly between attack and flee.
	 * 
	 * @param target The enemy being attacked/fleed.
	 */
	public void battleAction(Character target){
		boolean attack = random.nextBoolean();
		if (attack){
			if( hit(target.getDexterity(), target.getSpeed())){
				attack(target);
			}
		}
		else{
			flee(target.getLevel(), target.getSpeed());
		}
	}

	/**
	 * Attacks the target, based on their defence.
	 * @param target The target to attack.
	 */
	private void attack(Character target){
		int targetDefence = target.getDefence();
		double damage = (attack + (0.5*strength) - (0.5*targetDefence));
		if (damage <= 0){
			damage = 1;
		}
		target.damage(damage);
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
	private boolean flee(int enemyLevel, int enemySpeed){
		//TODO Buffs/Statuses affect it
		int fleeChance = 65 - (3*(enemySpeed - speed));
		if (fleeChance > 95){
			fleeChance = 95;
		}
		else if (fleeChance < 5){
			fleeChance = 5;
		}

		int fleeRoll = random.nextInt();
		if (fleeRoll <= fleeChance){
			//TODO Drop money upon fleeing, based on level and your luck.
			int moneyDropModifier = (level - enemyLevel);
			int randomMoney = (1 + random.nextInt(5));
			long moneyDrop = (moneyDropModifier*randomMoney) + (money/randomMoney);
			if (moneyDrop > money){
				moneyDrop = money;
			}
			System.out.println("In " + name + "'s haste to flee, they dropped " + moneyDrop + " gold coins!");			
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
	
	private int getLevel() {
		return level;
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
		double hitChance = 65;
		hitChance=hitChance+(dexterity-enemyDex)+(0.5*(speed-enemySpeed));
		
		if (hitChance>maxChance){
			hitChance = maxChance;
		}
		
		int randHit = random.nextInt();
		if (randHit >= hitChance){
			System.out.println(name + " missed!");
			return false;
		}
		return true;
	}
	
}
