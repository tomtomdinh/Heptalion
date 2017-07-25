package hw5.model;

// Going to rename this so symbol instead of piece. in my design i called this piece but symbol makes more sense
// Symbol contains the enum for both characters on the board and on the dominoes
public enum Symbol {
	
	// the special characters that represent the ASCII characters are:
	// DIAMOND : §
	// SQUARE : ¤
	// DOT : ·
	// all the others have their true characters represented
	CIRCLE('O'), DIAMOND((char)167), PLUS('+'), SQUARE((char)164), XMARK('X'), CLOVER('8'), DOT((char)183);
	
	private char character;
	
	Symbol(char character) {
		this.character = character;
	}
	
	// returns the character that corresponds to the enum 
	public char getChar(){
		return this.character;
	}
	
}
