import java.util.ArrayList;

public class Character {


	String name;
	int attack;
	int defence;
	int strength;
	int intelligence;
	int dexterity;
	//More stats here.

	public Character(ArrayList<String[]> characterData) {
		for (String[] field: characterData){
			switch(field[0]){
			case("Name"):
				this.name = field[1];
				break;
			case("Attack"):
				this.attack = Integer.parseInt(field[1]);
				break;
			case("Defence"):
				this.defence = Integer.parseInt(field[1]);
				break;
			case("Strength"):
				this.strength = Integer.parseInt(field[1]);
				break;
			case("Intelligence"):
				this.intelligence = Integer.parseInt(field[1]);
				break;
			case("Dexterity"):
				this.dexterity = Integer.parseInt(field[1]);
				break;
			default:
				System.out.println("Uh oh!");
				break;
			}
		}
	}
	
	public Character(String name, int attack, int defence, int strength, int intelligence, int dexterity) {
		this.name = name;
		this.attack = attack;
		this.defence = defence;
		this.strength = strength;
		this.intelligence = intelligence;
		this.dexterity = dexterity;
	}

}
