import java.io.IOException;
import java.util.ArrayList;

public class Main {
	
	/*Reader characterReader = new Reader("Character.txt");
	ArrayList<ArrayList<String[]>> fileData;
	Character player;*/
	
	public Main() {

	}

	public void runApp() throws InterruptedException {
		/*try {
			ArrayList<ArrayList<String[]>> fileData = characterReader.readFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (ArrayList<String[]> character: fileData){
			player = new Character(character);
		}*/
		
		Character player = new Character("Player", 50, 2, 2, 2, 2, 2, 2);
		Character monster = new Character("Monster", 50, 2, 2, 2, 2, 2, 2);
		while (true) {
			while ((player.getTurnPoints() <= 100) && (monster.getTurnPoints() <= 100)) { //While neither is ready to act.
				Thread.sleep(1);
				boolean playerTurn = player.updateTurnPoints();
				boolean monsterTurn = monster.updateTurnPoints();
				if (playerTurn) {
					System.out.println(player.getName() + " is acting now. ");
					player.battleAction(monster);
					player.setTurnPoints(0);
				}
				if (monsterTurn) {
					System.out.println(monster.getName() + " is acting now. ");
					monster.battleAction(player);
					monster.setTurnPoints(0);
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Main main = new Main();
		main.runApp();
	}
}
