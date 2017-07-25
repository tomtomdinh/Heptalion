package hw5.model;

public class Board {
	private Face[][] face;
	
	// Board constructs a 2-D array of faces 11x11
	public Board() {
		face = new Face[][] { { null, null, null, null, null, new Face(Symbol.XMARK), null, null, null, null, null },
				{ null, null, null, null, new Face(Symbol.CIRCLE), new Face(Symbol.DIAMOND), new Face(Symbol.SQUARE), null,
						null, null, null },
				{ null, null, null, new Face(Symbol.XMARK), new Face(Symbol.DOT), new Face(Symbol.DIAMOND),
						new Face(Symbol.DIAMOND), new Face(Symbol.DOT), null, null, null },
				{ null, null, new Face(Symbol.CLOVER), new Face(Symbol.DOT), new Face(Symbol.DOT), new Face(Symbol.XMARK),
						new Face(Symbol.DIAMOND), new Face(Symbol.CLOVER), new Face(Symbol.CIRCLE), null, null },
				{ null, new Face(Symbol.DOT), new Face(Symbol.SQUARE), new Face(Symbol.DOT), new Face(Symbol.PLUS), null,
						new Face(Symbol.PLUS), new Face(Symbol.CLOVER), new Face(Symbol.CLOVER), new Face(Symbol.CLOVER),
						null },
				{ new Face(Symbol.CIRCLE), new Face(Symbol.SQUARE), new Face(Symbol.SQUARE), new Face(Symbol.DIAMOND), null,
						null, null, new Face(Symbol.CIRCLE), new Face(Symbol.XMARK), new Face(Symbol.XMARK),
						new Face(Symbol.SQUARE) },
				{ null, new Face(Symbol.CLOVER), new Face(Symbol.SQUARE), new Face(Symbol.CIRCLE), new Face(Symbol.DOT),
						null, new Face(Symbol.SQUARE), new Face(Symbol.XMARK), new Face(Symbol.XMARK),
						new Face(Symbol.PLUS), null },
				{ null, null, new Face(Symbol.XMARK), new Face(Symbol.CIRCLE), new Face(Symbol.CIRCLE),
						new Face(Symbol.SQUARE), new Face(Symbol.PLUS), new Face(Symbol.PLUS), new Face(Symbol.CLOVER),
						null, null },
				{ null, null, null, new Face(Symbol.PLUS), new Face(Symbol.CIRCLE), new Face(Symbol.PLUS),
						new Face(Symbol.PLUS), new Face(Symbol.DIAMOND), null, null, null },
				{ null, null, null, null, new Face(Symbol.DIAMOND), new Face(Symbol.CLOVER), new Face(Symbol.DOT), null,
						null, null, null },
				{ null, null, null, null, null, new Face(Symbol.DIAMOND), null, null, null, null, null } };
				
	}
	
	// a toString method that returns a 11x11 string of the board
	// if the current Face is occupied :  '-' 
	@Override
	public String toString() {
		String s = "0 1 2 3 4 5 6 7 8 9 10\n";

		for (int i = 0; i < face.length; i++) {

			for (int j = 0; j < face[i].length; j++) {
				if (face[i][j] == null) {
					s += "  ";
				} else if (!face[i][j].isOccupied()) {
					s += face[i][j].getSymbol().getChar() + " ";
				} else
					s += "- ";
			}
			s += "  " + String.valueOf(i);
			s += "\n";
		}

		return s;
	}
	
	// returns the row length
	public int getRow(){
		return face.length;
	}
	
	// returns the col length
	// luckily the row and col length are always going to be the same
	public int getCol() {
		return face[0].length;
	}

	// returns the current Face at this position
	public Face getFace(int r,  int c) {
		return face[r][c];
	}
	
	// checks if the current Face on the board is null or occupied
	// also checks to see if the current domino symbol is equal to the current
	// symbol on the board
	public boolean isValidMove(Face f, Symbol p) {

		if (f == null)
			return false;
		if (f.isOccupied())
			return false;
		if (f.getSymbol() == p)
			return true;

		return false;
	}
}
