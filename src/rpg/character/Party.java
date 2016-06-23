package rpg.character;

import java.util.ArrayList;

import rpg.world.Item;

/**
 * Represents the party which playable characters belong to. 
 * 
 * Singleton class. Instance is created via makeParty().
 * @author Matthew Maufe
 *
 */

public class Party {
	private static Party instance = null; //The single instance of Party allowed in the program. Instantiated in makeParty();
	
	long money; //Could potentially be an int, but 65000 is kind of a low ceiling based on other RPGs.
	ArrayList<Item> inventory; //TODO Max inventory size will be needed. Depends on if we add pages, multiple inventories, etc.
	
	PartyMember[] roster; //All the party characters.
	PartyMember[] activeRoster; //The characters currently active in the party.
	private int maxRoster = 10; //The maximum amount of available party members.
	private int maxActive = 4; //The maximum amount of party members who can be active at one time.
	
	/**
	 * Initialises roster, activeRoster, and inventory. It can only be called by makeParty().
	 */
	private Party(){
		roster = new PartyMember[maxRoster];
		activeRoster = new PartyMember[maxActive];
		inventory = new ArrayList<Item>(30);
	}
	
	/**
	 * Allows creation of a single Party object.
	 * @return instance The single instance of Party allowed. 
	 */
	public Party makeParty(){
		if (instance == null){
			instance = new Party();
		}
		return instance;
	}
}
