import java.util.ArrayList;

import javax.xml.stream.events.Characters;

public class Game {

	private ArrayList<Character> characters;
	private ArrayList<Character> activeCharacters;
	private ArrayList<Character> enemyFighters; //TODO Change to Enemy when subclasses complete.
	Reader characterReader;
	ArrayList<ArrayList<String[]>> characterFile;

	public Game() {
		characterReader = new Reader("Character.txt");
		characterFile = new ArrayList<ArrayList<String[]>>(2);
		characters = new ArrayList<Character>();
		activeCharacters = new ArrayList<Character>(2);
		enemyFighters = new ArrayList<Character>(1);
	}

	public void runGame() {
		characterFile = characterReader.readFile(); //Reads in data about characters.
		for (ArrayList<String[]> character : characterFile) {
			characters.add(new Character(character, this)); //Creates Character objects from the data, adds them to the ArrayList.
		}
		activeCharacters.add(characters.get(0)); //TODO Make more flexible. Use instanceof()?
		enemyFighters.add(characters.get(1));
		runTurns(); //Runs the turn-change algorithm.
	}
	
	private void printState(){
		for (Character current: characters){
			System.out.println(current.getName() + " has " + current.getHealth() + " health points and " + current.getMana() + " mana."); 
		}
	}

	private void runTurns() {
		while (true) {
			Character activeCharacter = checkTurns(50);
			//activeCharacter.battleAction();
			printState();
			String action = activeCharacter.chooseAction();
			switch (action.toLowerCase()) {
			case ("attack"):
				if (enemyFighters.contains(activeCharacter)){
					activeCharacter.attack(activeCharacters.get(0));
				}
				else{
					activeCharacter.attack(enemyFighters.get(0));
				}
				break;

			case ("heal"):
				activeCharacter.heal();
				break;

			case ("flee"):
				activeCharacter.flee((enemyFighters.get(0)).getLevel(), (enemyFighters.get(0)).getSpeed());
				break;
			default:
				System.out.println("Bad option chosen somehow! Error in IO class!");
			}
		}
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
			allFighters.addAll(enemyFighters);
			for (Character active : allFighters) {
				if (active.updateTurnPoints()) {
					active.setTurnPoints(0);
					System.out.println("It is now " + active.getName() + "'s turn. ");
					return active;
				}
			}		
		}
	}

	/*public Character getTarget(String type, int checkHealth) {
		boolean friendly = false;
		if (checkHealth > 0){
			friendly = true;
		}
		switch (type.toLowerCase()) {
		case ("weakest"):
			return getWeakest(friendly);
	
		case ("strongest"):
			return getStrongest();
	
		case ("smartest"):
			return getSmartest();
		}
		System.out.println("Error! Error!"); //TODO Increase jaffaMaturity stat for balance.
		return null;
	}
	
	private Character getWeakest(int checkHealth) {
		int currentLowest = Integer.MAX_VALUE; //The current minimum amount of Health.
		Character weakestCharacter;
		ArrayList<Character> searchList;
		if (checkHealth > 0){
			searchList = activeCharacters;
		}
		else{
			searchList = enemyFighters;
		}
		weakestCharacter = searchList.get(0);
		
		for (Character current : searchList) {
			int charHealth = current.getHealth();
			if (charHealth < currentLowest) {
				if ((checkHealth > 0)){
					double percentHealth = checkHealth/100;
					if (charHealth <= (current.getMaxHealth()*percentHealth)){
						currentLowest = charHealth;
						weakestCharacter = current;
					} 
				}
				currentLowest = charHealth;
				weakestCharacter = current;
			}
		}
		return weakestCharacter;
	}*/

	private Character getStrongest() {
		int currentHighest = Integer.MIN_VALUE; //The current maximum amount of Attack.
		Character strongestCharacter;
		ArrayList<Character> searchList;
		searchList = enemyFighters;
		strongestCharacter = searchList.get(0);

		for (Character current : searchList) {
			int charAttack = current.getAttack();
			if (charAttack < currentHighest) {
				currentHighest = charAttack;
				strongestCharacter = current;
			}
		}
		return strongestCharacter;
	}

	private Character getSmartest() {
		int currentHighest = Integer.MIN_VALUE; //The current maximum amount of Intelligence.
		Character smartestCharacter;
		ArrayList<Character> searchList;
		searchList = enemyFighters;
		smartestCharacter = searchList.get(0);

		for (Character current : searchList) {
			int charIntelligence = current.getIntelligence();
			if (charIntelligence < currentHighest) {
				currentHighest = charIntelligence;
				smartestCharacter = current;
			}
		}
		return smartestCharacter;
	}

}
