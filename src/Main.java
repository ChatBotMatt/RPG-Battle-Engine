import java.io.IOException;
import java.util.ArrayList;

public class Main {
	
	Game gameEngine;
	
	public Main() {

	}

	/**
	 * Creates two characters, simulates a battle to the death. 
	 * @throws InterruptedException
	 */
	public void runApp() throws InterruptedException {
		gameEngine = new Game();
		gameEngine.runGame();
	}

	public static void main(String[] args) throws InterruptedException {
		Main main = new Main();
		main.runApp();
	}
}
