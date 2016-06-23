/**
 * Party members are part of this class. 
 * 
 * @author Matt Maufe (mam95)
 *
 */
package rpg.character;

public class PartyMember extends Character{
	
	private long experience;
	private int killCount;
	private int deathCount;
	private double damageDealt;
	private double damageTaken;
	
	/**
	 * Creates a generic Party Member with average stats.
	 */
	public PartyMember() {
		super("Blankles", "A default base", 100, 20, 5, 5, 5, 5, 5, 5);
		experience = 0;
		killCount = 0;
		deathCount = 0;
		damageDealt = 0;
		damageTaken = 0;
	}
	
	public PartyMember(String name, String description, int maxHealth, int maxMana, int attack, int defence, int strength, 
			int intelligence, int fitness, int dexterity) {
		super(name, description, maxHealth, maxMana, attack, defence, strength, intelligence, fitness, dexterity);
		experience = 0;
		killCount = 0;
		deathCount = 0;
		damageDealt = 0;
		damageTaken = 0;
	}

}
