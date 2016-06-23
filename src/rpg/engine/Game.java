package rpg.engine;

import java.util.ArrayList;
import java.util.Random;

import java.io.IOException; // For logging.
import java.util.logging.Level;
import java.util.logging.Logger;

import rpg.character.Character;
import rpg.character.Enemy;
import rpg.character.PartyMember;
import mam95.tools.io.IO;

public class Game {

	private ArrayList<Character> characters;
	private ArrayList<Character> activeCharacters;
	private ArrayList<Enemy> enemyFighters;

	PartyMember deathsmasher;
	Enemy mike;

	IO input;
	Random random;
	private Logger lumberjack = Logger.getLogger("Lumberjack");

	public Game() {
		deathsmasher = new PartyMember();
		mike = new Enemy("Monster Mike", "a dedly monsta", 70, 15, 3, 3, 3, 3, 3, 3); // Creates a weak monster.

		lumberjack.info("Lumberjack is in the forest.");
		lumberjack.info("Moooooo");

		characters = new ArrayList<Character>();
		activeCharacters = new ArrayList<Character>();
		enemyFighters = new ArrayList<Enemy>();

		characters.add(deathsmasher);
		characters.add(mike);
		activeCharacters.add(deathsmasher);
		activeCharacters.add(mike);
		enemyFighters.add(mike);

		random = new Random();
		input = new IO();

		runGame();
	}

	public void runGame() {
		runTurns(); // Runs the turn-change algorithm.
	}

	private void printState() {
		for (Character current : activeCharacters) {
			System.out.println(current.getName() + " has " + current.getHealth() + " health points and " + current.getMana() + " mana.");
		}
	}

	private void runTurns() {
		while (true) {
			Character activeCharacter = checkTurns(50); // Active character is whichever reaches TT first.
			printState(); // Print the current state of the battle.
			activeCharacter.regenerateMana();
			String action = printBattleMenu(); // Get the action chosen by the user.
			switch (action.toLowerCase()) {
			case ("attack"):
				int randomHit = random.nextInt(100);

				if (activeCharacter.equals(mike)) {
					int hitChance = activeCharacter.hit(deathsmasher.getDexterity(), deathsmasher.getFitness());
					if (randomHit <= hitChance) {
						int damage = activeCharacter.calculateDamage(deathsmasher.getDefence());
						deathsmasher.damage(damage);
					} else {
						System.out.println(activeCharacter.getName() + " missed!");
					}
				}

				else {
					int hitChance = activeCharacter.hit(mike.getDexterity(), mike.getFitness());
					if (randomHit < hitChance) {
						int damage = activeCharacter.calculateDamage(mike.getDefence());
						mike.damage(damage);
					} else {
						System.out.println(activeCharacter.getName() + " missed!");
					}
				}
				break;

			case ("heal"):
				activeCharacter.heal();
				break;

			case ("flee"):
				activeCharacter.flee((enemyFighters.get(0)).getLevel(), (enemyFighters.get(0)).getFitness());
				break;
			default:
				System.out.println("Bad option chosen somehow! Error in IO class!");
			}
		}
	}

	/**
	 * Prints a battle menu with the three current options. Returns the chosen
	 * option.
	 * 
	 * @return
	 */
	private String printBattleMenu() {
		String[] options = { "Attack", "Heal", "Flee" };
		int actionIndex = input.printMenu(options);
		String action = options[actionIndex];
		return action;
	}

	private Character checkTurns(int turnDelay) {
		while (true) {
			try {
				Thread.sleep(turnDelay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ArrayList<Character> allFighters;
			allFighters = new ArrayList<Character>((activeCharacters.size() + enemyFighters.size()));
			allFighters.addAll(activeCharacters);
			for (Character active : allFighters) {
				if ((active.updateTurnPoints() == 100)) {
					active.setTurnPoints(0);
					System.out.println("It is now " + active.getName() + "'s turn. \n");
					return active;
				}
			}
		}
	}

	/*
	 * public Character getTarget(String type, int checkHealth) { boolean
	 * friendly = false; if (checkHealth > 0){ friendly = true; } switch
	 * (type.toLowerCase()) { case ("weakest"): return getWeakest(friendly);
	 * 
	 * case ("strongest"): return getStrongest();
	 * 
	 * case ("smartest"): return getSmartest(); } System.out.println(
	 * "Error! Error!"); //TODO Increase jaffaMaturity stat for balance. return
	 * null; }
	 * 
	 * private Character getWeakest(int checkHealth) { int currentLowest =
	 * Integer.MAX_VALUE; //The current minimum amount of Health. Character
	 * weakestCharacter; ArrayList<Character> searchList; if (checkHealth > 0){
	 * searchList = activeCharacters; } else{ searchList = enemyFighters; }
	 * weakestCharacter = searchList.get(0);
	 * 
	 * for (Character current : searchList) { int charHealth =
	 * current.getHealth(); if (charHealth < currentLowest) { if ((checkHealth >
	 * 0)){ double percentHealth = checkHealth/100; if (charHealth <=
	 * (current.getMaxHealth()*percentHealth)){ currentLowest = charHealth;
	 * weakestCharacter = current; } } currentLowest = charHealth;
	 * weakestCharacter = current; } } return weakestCharacter; }
	 * 
	 * private Character getStrongest() { int currentHighest =
	 * Integer.MIN_VALUE; //The current maximum amount of Attack. Character
	 * strongestCharacter; ArrayList<Character> searchList; searchList =
	 * enemyFighters; strongestCharacter = searchList.get(0);
	 * 
	 * for (Character current : searchList) { int charAttack =
	 * current.getAttack(); if (charAttack < currentHighest) { currentHighest =
	 * charAttack; strongestCharacter = current; } } return strongestCharacter;
	 * }
	 * 
	 * private Character getSmartest() { int currentHighest = Integer.MIN_VALUE;
	 * //The current maximum amount of Intelligence. Character
	 * smartestCharacter; ArrayList<Character> searchList; searchList =
	 * enemyFighters; smartestCharacter = searchList.get(0);
	 * 
	 * for (Character current : searchList) { int charIntelligence =
	 * current.getIntelligence(); if (charIntelligence < currentHighest) {
	 * currentHighest = charIntelligence; smartestCharacter = current; } }
	 * return smartestCharacter; }
	 */

}
