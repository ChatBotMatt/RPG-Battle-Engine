package rpg.system;
import java.io.IOException;
import java.util.ArrayList;

import rpg.engine.Game;

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
