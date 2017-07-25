package hw5.model;

import java.util.ArrayList;
import java.util.List;

public class Player {

	private String name;
	private List<Symbol[]> dominoes;

	// constructor that takes the shuffled list of dominoes and the player's name
	public Player(String name, List<Symbol[]> dominoes) {
		this.dominoes = new ArrayList<>(dominoes);
		this.name = name;
	}

	// returns the name of the player
	public String getName() {
		return name;
	}

	// returns the domino at the list's position
	public Symbol[] getDom(int x) {
		return dominoes.get(x);
	}

	// returns the size of the player's hand
	public int getDomSize() {
		return dominoes.size();
	}

	// method that returns a list of the current player's hand
	public List<Symbol[]> playerHand() {
		return dominoes;
	}

	// removes the domino within the list
	public void removeDom(int index) {
//		for (Iterator<Symbol[]> iterator = dominoes.iterator(); iterator.hasNext();) {
//			Symbol[] symbol = iterator.next();
//			if (Arrays.equals(symbol, s)) {
//				iterator.remove();
//			}
//		}
		
		dominoes.remove(index);
	}

	// checks if list is empty or not
	public boolean empty() {
		if (dominoes.isEmpty())
			return true;
		return false;
	}

	// displays the current domino
	public void displayDom(Symbol[] p) {
		System.out.println("0 1");
		System.out.println(p[0].getChar() + "|" + p[1].getChar());
	}

	// displays the player's list of dominoes
	public void displayAllDom() {

		int num = 0;
		for (int j = 0; j < dominoes.size(); j++)
			System.out.printf("%-5d", num++);
		System.out.println();
		for (Symbol[] p : dominoes) {
			for (int i = 0; i < 2; i++) {
				System.out.print(p[i].getChar());
				if (i == 0)
					System.out.print("|");
			}
			System.out.print("  ");
		}
		System.out.println();
	}
}
