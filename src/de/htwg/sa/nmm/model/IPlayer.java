package de.htwg.sa.nmm.model;

import java.awt.Color;

import de.htwg.sa.nmm.model.IToken;

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
	
	IToken takeToken();
	
	boolean hasToken();
	
	int token();
	
	void addToken();
	
	String name();
	
	Color color();
	
}
