package de.htwg.se.nmm.controller;

import de.htwg.se.nmm.model.IPlayer;
import de.htwg.se.nmm.model.IToken;
import de.htwg.se.nmm.util.observer.IObservable;

public interface INmmController extends IObservable {
	
	void restart();
	
	boolean pickToken(int grid, int index);
	
	boolean setToken(int grid, int index);
	
	boolean moveToken(int sourceGrid, int sourceIndex, int destGrid, int destIndex);
	
	IPlayer currentPlayer();
	
	IPlayer otherPlayer();
	
	String color(int grid, int index);
	
	String getStatus();
	
	boolean hasToken(int grid, int index);
	
	String dumpColor(int grid, int index);
	
	boolean valid(int grid, int index);
	
	int grids();
	
	int index();
	
	boolean undo();

	boolean hasWon(IPlayer player);
}
