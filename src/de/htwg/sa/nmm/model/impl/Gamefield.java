package de.htwg.sa.nmm.model.impl;

import de.htwg.sa.nmm.model.IField;
import de.htwg.sa.nmm.model.IGamefield;
import de.htwg.sa.nmm.model.IPlayer;
import de.htwg.sa.nmm.model.IToken;

/**
 * Implementation of {@link IGamefield}
 * 
 * @author Markus Heilig
 * @since 2014-05-30
 */

public final class Gamefield implements IGamefield {

	/**
	 * Number of grids of the gamefield
	 */
	private static final int GRIDS = 3;

	/**
	 * Index of the grid to identify a specific position
	 */
	private static final int INDEX = 8;

	/**
	 * Name of the Game. Needed to identify the game in db4o database
	 */
	private String name = "";

	/**
	 * Field matrix to address the specific positions
	 */
	private IField[][] gamefield = null;

	/**
	 * Variable to store player1
	 */
	private IPlayer player1;

	/**
	 * Variable to store player2
	 */
	private IPlayer player2;

	/**
	 * Variable to store the current player
	 */
	private IPlayer currentPlayer;

	/**
	 * Constructor which initializes the players and calls
	 * {@link Gamefield#init()}
	 * 
	 * @param player1
	 * @param player2
	 */
	public Gamefield(IPlayer player1, IPlayer player2) {
		this.player1 = player1;
		this.player2 = player2;
		this.currentPlayer = player1;
		init();
	}

	/**
	 * Initializes the {@link Gamefield#gamefield}
	 */
	public void init() {
		gamefield = new Field[GRIDS][INDEX];
		for (int grid = 0; grid < GRIDS; grid++) {
			for (int index = 0; index < INDEX; index++) {
				gamefield[grid][index] = new Field(grid, index);
			}
		}
	}

	public void setGamefield(IField[][] gamefield) {
		this.gamefield = gamefield;
	}

	public IField[][] getGamefield() {
		return gamefield;
	}

	/**
	 * Returns a specific postition of {@link Gamefield#gamefield}
	 * 
	 * @return {@link Field}
	 */
	public IField field(int grid, int index) {
		return gamefield[grid][index];
	}

	/**
	 * Counts the tokens of a player
	 * 
	 * @return The number of tokens from the player
	 */
	public int countToken(IPlayer player) {
		int countToken = 0;
		for (int grid = 0; grid < GRIDS; grid++) {
			for (int index = 0; index < INDEX; index++) {
				IField field = gamefield[grid][index];
				if (field.hasToken() && field.getToken().color().equals(player.color())) {
					countToken++;
				}
			}
		}
		return countToken;
	}

	/**
	 * Checks for a mill
	 */
	public boolean mill(IField field) {
		int grid = field.grid();
		int index = field.index();
		if (field.corner()) {
			return checkCorner(grid, index);
		}
		return checkMid(grid, index);
	}

	/**
	 * Check if the neighbors are the same color
	 * 
	 * @param grid
	 * @param index
	 * @param betweenGrid
	 * @return true if three neighbors with same color, false if not.
	 */
	private boolean checkNeighbours(int grid, int index, boolean betweenGrid) {
		final int tokenForMill = 3;
		IToken[] three = new IToken[tokenForMill];
		for (int i = -1; i < 2; i++) {
			int curGrid = grid;
			int curIndex = index;
			if (!betweenGrid) {
				curIndex = (index + i) % INDEX;
			} else {
				curGrid = grid + i;
			}
			three[i + 1] = gamefield[curGrid][curIndex].getToken();
			if (three[i + 1] == null) {
				return false;
			}
		}
		// equals is implemented transitive
		return three[0].equals(three[1]) && three[1].equals(three[2]);
	}

	/**
	 * Check for corner
	 * 
	 * @param grid
	 * @param index
	 * @return true if the position is a corner, false if not
	 */
	private boolean checkCorner(int grid, int index) {
		final int indexOverflow = 7;
		return checkNeighbours(grid, (index + indexOverflow) % INDEX, false) || checkNeighbours(grid, index + 1, false);
	}

	/**
	 * Check if the position is a mid position
	 * 
	 * @param grid
	 * @param index
	 * @return true if mid position, false if not
	 */
	private boolean checkMid(int grid, int index) {
		return checkNeighbours(1, index, true) || checkNeighbours(grid, index, false);
	}

	/**
	 * Returns the constant {@link Gamefield#GRIDS}
	 */
	public int grids() {
		return GRIDS;
	}

	/**
	 * Returns the constant {@link Gamefield#INDEX}
	 */
	public int index() {
		return INDEX;
	}

	/**
	 * Check for valid position
	 * 
	 * @return true if valid, false if not
	 */
	public boolean valid(int grid, int index) {
		if (grid < GRIDS && index < INDEX) {
			return true;
		}
		return false;
	}

	@Override
	public IPlayer getCurrentPlayer() {
		return currentPlayer;
	}

	@Override
	public IPlayer getOtherPlayer() {
		if (currentPlayer == player1)
			return player2;
		return player1;
	}

	@Override
	public void nextPlayer() {
		if (currentPlayer != player1) {
			currentPlayer = player1;
		} else {
			currentPlayer = player2;
		}
	}

	@Override
	public void setStatus(IPlayer.Status status) {
		currentPlayer.setStatus(status);
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String id) {
		this.name = id;
	}
}
