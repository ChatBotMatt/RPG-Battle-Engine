import java.util.ArrayList;
import java.util.Random;

public class Character {

	private String name;
	private int maxHealth;
	private int health;
	
	private int attack;
	private int defence;
	private int strength;
	private int intelligence;
	private int speed; //Used for turn order.
	private int dexterity;
	
	private int baseAttack;
	private int baseDefence;
	private int baseStrength;
	private int baseIntelligence;
	private int baseSpeed;
	private int baseDexterity;
	
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
	
	private Random random;
	
	//More stats here.
	
	private double turnPoints; //At 100, they can act.

	public Character(ArrayList<String[]> characterData) {
		for (String[] field: characterData){
			switch(field[0]){
			case("Name"):
				this.name = field[1];
				break;
			case("Attack"):
				this.attack = Integer.parseInt(field[1]);
				break;
			case("Defence"):
				this.defence = Integer.parseInt(field[1]);
				break;
			case("Strength"):
				this.strength = Integer.parseInt(field[1]);
				break;
			case("Intelligence"):
				this.intelligence = Integer.parseInt(field[1]);
				break;
			case("Dexterity"):
				this.dexterity = Integer.parseInt(field[1]);
				break;
			default:
				System.out.println("Uh oh!");
				break;
			}
		}
		random = new Random();
	}
	
	public Character(String name, int maxHealth, int attack, int defence, int strength, int intelligence, int speed, int dexterity) {
		this.name = name;
		this.maxHealth = maxHealth;
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
	
	public boolean updateTurnPoints(){
		int randomBuffer = random.nextInt(100);
		turnPoints+=(speed+(randomBuffer/100));
		if (turnPoints > 100){ //Stops it from going over the max value.
			turnPoints = 100;
			return true;
		}
		return false;
	}
	
	public void attack(Character target){
		int targetDefence = target.getDefence();
		double damage = (attack + (0.5*strength) - (0.5*targetDefence));
		if (damage <= 0){
			damage = 1;
		}
		target.damage(damage);
	}
	
	public void damage(double damageTaken){
		health-= damageTaken;
		System.out.println(name + " has taken " + damageTaken + " points of damage!");
		if (health <= 0){
			System.out.println(name + " has died!");
			System.exit(0);
		}
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

}
