package de.htwg.sa.nmm.model;

public interface IGamefield {

	void init();

	IField field(int grid, int index);

	int countToken(IPlayer player);

	boolean mill(IField field);

	int grids();

	int index();

	boolean valid(int grid, int index);

	/**
	 * Get the current player
	 * 
	 * @return active player
	 */
	IPlayer getCurrentPlayer();

	/**
	 * Return the, which is not active
	 * 
	 * @return non-active player
	 */
	IPlayer getOtherPlayer();

	/**
	 * change the active player
	 */
	void nextPlayer();

	/**
	 * Set the status of the game
	 * 
	 * @param status
	 */
	void setStatus(IPlayer.Status status);

	/**
	 * Get the name of the game
	 * 
	 * @return String
	 */
	String getName();

	/**
	 * Set the name of the Game
	 * 
	 * @param name
	 */
	void setName(String name);
}
