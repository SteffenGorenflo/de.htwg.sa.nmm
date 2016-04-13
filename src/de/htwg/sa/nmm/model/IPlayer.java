package de.htwg.sa.nmm.model;

/**
 * Interface for a player
 * 
 * @author Markus Heilig
 * @since 2014-05-30
 */
public interface IPlayer {
	
	/**
	 * Current status of the player
	 * 
	 * @author Markus Heilig
	 * @since 2014-05-30
	 */
	enum Status {
		SetToken,
		MoveToken,
		PickToken,
		GameLost;
	}
	
	Status getStatus();
	
	void init();
	
	boolean isStatus(Status status);
	
	void setStatus(Status status);

	void setToken(int token);

	IToken takeToken();
	
	boolean hasToken();
	
	int token();
	
	void addToken();
	
	String name();
	
	IToken.Color color();
	
}
