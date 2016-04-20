package de.htwg.sa.nmm.controller;

import java.util.List;

import de.htwg.sa.nmm.model.IPlayer;
import de.htwg.sa.nmm.persistence.PersistenceStrategy;
import de.htwg.sa.nmm.util.observer.IObservable;

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

	boolean storeGame(String id, PersistenceStrategy strategy);

	boolean loadGame(String id, PersistenceStrategy strategy);

	List<String> getGameIds(PersistenceStrategy strategy);

	boolean deleteGame(String id, PersistenceStrategy strategy);
}
