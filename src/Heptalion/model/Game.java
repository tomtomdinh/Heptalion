package hw5.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
	private Board board;
	private List<Symbol[]> dominoes;
	private List<Player> players;
	private int dom, numSymbol;
	private int playerTurn;

	private final int UP = 0;
	private final int DOWN = 1;
	private final int LEFT = 2;
	private final int RIGHT = 3;

	// constructor that creates a board, initializes the dominoes and shuffles
	public Game(String p1, String p2) {
		board = new Board();
		initializeSymbols();
		shuffle();
		initializePlayers(p1, p2);
		playerTurn = 0;
	}

	// initializes the two players and distributes the dominoes to the players
	// puts the players into an arraylist
	private void initializePlayers(String name1, String name2) {

		players = new ArrayList<>();

		List<Symbol[]> p1 = dominoes.subList(0, dominoes.size() / 2);
		List<Symbol[]> p2 = dominoes.subList(dominoes.size() / 2, dominoes.size());

		players.add(new Player(name1, p1));
		players.add(new Player(name2, p2));

	}

	// returns the player in the list
	public Player getPlayer(int i) {
		return players.get(i);
	}

	// shuffles the master list of dominioes
	private void shuffle() {
		Collections.shuffle(dominoes);
	}

	public Board getBoard() {
		return board;
	}

	// sets which domino was picked in the dominoes list
	public void setDom(int dom) {
		this.dom = dom;
	}
	
	// returns the domino in the list
	public int getDom() {
		return dom;
	}
	
	// returns which symbol was used
	public int getNumSymbol() {
		return numSymbol;
	}

	// sets which symbol was chosen on the array
	public void setNumSymbol(int numSymbol) {
		this.numSymbol = numSymbol;
	}

	// gets the current players turn
	public int getPlayerTurn() {
		return playerTurn;
	}

	// switches the players
	public void switchThePlayer() {
		playerTurn = (playerTurn + 1) % 2;
	}

	// checks if the symbol matches the board
	public boolean checkIfSame(int r, int c, int i) {
		Symbol s = players.get(i).getDom(dom)[numSymbol];
		if (board.isValidMove(board.getFace(r, c), s)) {
			return true;
		}
		return false;
	}
	
	// removes the domino from the list
	public void removeFromDomList(int player) {
		players.get(player).removeDom(dom);
	}
	

	// method that checks if the current player still has any moves left
	public boolean checkForValidMoves(int index) {
		for (Symbol[] p : players.get(index).playerHand()) {
			for (int i = 0; i < board.getRow(); i++) {
				for (int j = 0; j < board.getCol(); j++) {
					if (board.isValidMove(board.getFace(i, j), p[0])) {
						if ((i - 1) >= 0) {
							if (board.isValidMove(board.getFace((i - 1), j), p[1])) {
								return true;
							}
						}
						if ((i + 1) < board.getRow()) {
							if (board.isValidMove(board.getFace((i + 1), j), p[1])) {
								return true;
							}
						}
						if ((j - 1) >= 0) {
							if (board.isValidMove(board.getFace(i, (j - 1)), p[1])) {
								return true;
							}
						}
						if ((j + 1) < board.getCol()) {
							if (board.isValidMove(board.getFace(i, (j + 1)), p[1])) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	// method that selects from the user input to place the 2nd symbol on the
	// domino. also chekcs to see if the user choice is within bounds of the
	// board.
	// if correct then sets the Face's on the board to occupied and returns true
	public boolean selectChoice(int row, int column, int player, int choice) {
		int otherSelection = (numSymbol + 1) % 2;
		Symbol p = players.get(player).getDom(dom)[otherSelection];
		switch (choice) {
		case UP:
			if ((row - 1) >= 0) {
				if (board.isValidMove(board.getFace(row - 1, column), p)) {
					board.getFace(row, column).setOccupied();
					board.getFace(row - 1, column).setOccupied();
					return true;
				} else
					return false;
			} else
				return false;
		case DOWN:
			if ((row + 1) < board.getRow()) {
				if (board.isValidMove(board.getFace(row + 1, column), p)) {
					board.getFace(row, column).setOccupied();
					board.getFace(row + 1, column).setOccupied();
					return true;
				} else
					return false;
			} else
				return false;
		case LEFT:
			if ((column - 1) >= 0) {
				if (board.isValidMove(board.getFace(row, column - 1), p)) {
					board.getFace(row, column).setOccupied();
					board.getFace(row, column - 1).setOccupied();
					return true;
				} else
					return false;
			} else
				return false;
		case RIGHT:
			if ((column + 1) < board.getCol()) {
				if (board.isValidMove(board.getFace(row, column + 1), p)) {
					board.getFace(row, column).setOccupied();
					board.getFace(row, column + 1).setOccupied();
					return true;
				} else
					return false;
			} else
				return false;
		default: // shouldn't get here
			return false;
		}
	}

	// method that hard codes the master list of dominoes
	// a single domino is a size 2 array of Symbol
	private void initializeSymbols() {
		dominoes = new ArrayList<>();
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				if (i <= j) {
					Symbol[] domino = { Symbol.values()[i], Symbol.values()[j] };
					dominoes.add(domino);
				}
			}
		}
	}
}
