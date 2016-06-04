import java.util.ArrayList;

public class Character {

	String name;
	int attack;
	int defence;
	int strength;
	int intelligence;
	int speed; //Used for turn order.
	int dexterity;
	
	int baseAttack;
	int baseDefence;
	int baseStrength;
	int baseIntelligence;
	int baseSpeed;
	int baseDexterity;
	
	int equipAttack;
	int equipDefence;
	int equipStrength;
	int equipIntelligence;
	int equipSpeed;
	int equipDexterity;
	
	long experience;
	int level;
	int killCount;
	int deathCount;
	double damageDealt;
	double damageTaken;
	
	//More stats here.
	
	int turnPoints; //At 100, they can act.

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
	}
	
	public Character(String name, int attack, int defence, int strength, int intelligence, int speed, int dexterity) {
		this.name = name;
		this.attack = attack;
		this.defence = defence;
		this.strength = strength;
		this.intelligence = intelligence;
		this.speed = speed;
		this.dexterity = dexterity;
	}


	/**
	 * Called when equipment changes, or a stat-affecting action (E.g. (de)buff, tactics) occurs.
	 */
	public void updateStats(double attackModifier, double defenceModifier, double strengthModifier, double intelligenceModifier, double speedModifier, double dexterityModifier){
		attack = (int)(attackModifier*(baseAttack + equipAttack));
		defence = (int)(defenceModifier*(baseDefence + equipDefence));
		strength = (int)(strengthModifier*(baseStrength + equipStrength));
		intelligence = (int)(intelligenceModifier*(baseIntelligence + equipIntelligence));
		speed = (int)(speedModifier*(baseSpeed + equipSpeed));
		dexterity = (int)(dexterityModifier*(baseDexterity + equipDexterity));
	}
	
	public void updateTurnPoints(){
		turnPoints+=speed;
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

	public int getTurnPoints() {
		return turnPoints;
	}

	public void setTurnPoints(int turnPoints) {
		this.turnPoints = turnPoints;
	}

}
