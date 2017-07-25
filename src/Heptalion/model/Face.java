package hw5.model;

// Face contains the symbol on the board and if its occupied with a domino or not
public class Face {
	private Symbol s;
	private boolean isOccupied;

	// Face takes on a symbol and sets it to false
	public Face(Symbol s) {
		this.s = s;
		this.isOccupied = false;
	}

	// returns if occupied or not
	public boolean isOccupied() {
		return isOccupied;
	}

	// if a domino is correctly placed on the current face then occupied is set
	// to true
	public void setOccupied() {
		this.isOccupied = true;
	}

	// returns the current symbol on the board
	public Symbol getSymbol() {
		return s;
	}

}
