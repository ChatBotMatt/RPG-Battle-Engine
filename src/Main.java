import java.io.IOException;
import java.util.ArrayList;

public class Main {
	
	Reader characterReader = new Reader("Character.txt");
	ArrayList<ArrayList<String[]>> fileData;
	Character player;
	
	public Main() {

	}

	/**
	 * Creates two characters, simulates a battle to the death. 
	 * @throws InterruptedException
	 */
	public void runApp() throws InterruptedException {
		ArrayList<ArrayList<String[]>> fileData = characterReader.readFile();
		
		for (ArrayList<String[]> character: fileData){
			player = new Character(character);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Main main = new Main();
		Game gameEngine = new Game();
		gameEngine.runGame();
		main.runApp();
	}
}
