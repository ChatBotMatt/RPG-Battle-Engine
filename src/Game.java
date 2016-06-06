import java.util.ArrayList;

public class Game {

	private ArrayList<Character> characters;
	private ArrayList<Character> activeCharacters;

	public Game() {

	}
	
	public void runGame(){
		runTurns();
	}

	private void runTurns() {
		while (true) {
			Character activeCharacter = checkTurns(50);
			activeCharacter.battleAction(activeCharacter.selectTarget());
		}
	}

	private Character checkTurns(int turnDelay) {
		while (true) {
			try {
				Thread.sleep(turnDelay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (Character active : activeCharacters) {
				if (active.updateTurnPoints()) {
					active.setTurnPoints(0);
					return active;
				}
			}
		}
	}

}
