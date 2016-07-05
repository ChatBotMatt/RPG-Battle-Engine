package rpg.engine;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

import mam95.tools.io.IO;
import rpg.character.Character;
import rpg.character.Enemy;
import rpg.character.PartyMember;

public class Game {
	private ArrayList<Character> characters;
	private ArrayList<Character> activeCharacters;
	private ArrayList<Enemy> enemyFighters;
	PartyMember deathsmasher = new PartyMember();
	Enemy mike = new Enemy("Monster Mike", "a dedly monsta", 70, 15, 3, 3, 3, 3, 3, 3);
	IO input;
	Random random;
	//private Logger lumberjack = Logger.getLogger("Lumberjack");

	public Game() {
		//lumberjack.info("Lumberjack is in the forest.");
		//lumberjack.info("Moooooo");
		characters = new ArrayList<Character>();
		activeCharacters = new ArrayList<Character>();
		enemyFighters = new ArrayList<Enemy>();
		characters.add(this.deathsmasher);
		characters.add(this.mike);
		activeCharacters.add(this.deathsmasher);
		activeCharacters.add(this.mike);
		enemyFighters.add(this.mike);
		random = new Random();
		input = new IO();
		runGame();
	}

	public void runGame() {
		while (true) {
			runTurns();
		}
	}

	private void printState() {
		for (Character current : this.activeCharacters) {
			System.out.println(String.valueOf(current.getName()) + " has " + current.getHealth() + " health points and " + current.getMana() + " mana.");
		}
	}

	private void runTurns() {
		while (true) {
			Character active = checkTurns(50); // Active character is whichever reaches TT first.
			Character target; //TODO Update and make decent when we have multiple targets on any side. 
			if (active.equals(mike)) {
				target = deathsmasher;
			} else {
				target = mike;
			}
			printState(); // Print the current state of the battle.
			active.regenerateMana();
			String action = printBattleMenu(); // Get the action chosen by the user.
			switch (action.toLowerCase()) {

			case ("attack"):
				int randomHit = random.nextInt(100);
				int hitChance = active.hit(target.getDexterity(), target.getFitness());

				if (randomHit <= hitChance) {
					int damage = active.calculateDamage(target.getDefence());
					target.damage(damage);
				} else {
					System.out.println(active.getName() + " missed!");
				}
				break;

			case ("heal"):
				active.heal();
				break;

			case ("flee"):
				active.flee((enemyFighters.get(0)).getLevel(), (enemyFighters.get(0)).getFitness());
				break;

			default:
				System.out.println("Bad option chosen somehow! Error in IO class!");
			}
		}
	}

	private String printBattleMenu() {
		String[] options = new String[] { "Attack", "Heal", "Flee" };
		//int actionIndex = input.printMenu(options);
		String action = input.printMenu(options);//options[actionIndex];
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
}
