package de.htwg.se.nmm.controller;

import de.htwg.se.nmm.model.IPlayer;
import de.htwg.se.nmm.model.IToken;

public interface INmmController {
	
	void restart();
	
	void pickToken(int grid, int index);
	
	void setToken(int grid, int index);
	
	void moveToken(int sourceGrid, int sourceIndex, int destGrid, int destIndex);
	
	IPlayer currentPlayer();
	
	IPlayer otherPlayer();
	
	String color(int grid, int index);
	
	String getStatus();
	
	IToken getToken(int grid, int index);
	
	boolean valid(int grid, int index);
	
	int grids();
	
	int index();
	
	boolean undo();
}
