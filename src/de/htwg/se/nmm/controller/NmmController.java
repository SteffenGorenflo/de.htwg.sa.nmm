package de.htwg.se.nmm.controller;

import de.htwg.se.nmm.controller.gameaction.IAction;
import de.htwg.se.nmm.controller.gameaction.MoveTokenAction;
import de.htwg.se.nmm.controller.gameaction.PickTokenAction;
import de.htwg.se.nmm.controller.gameaction.SetTokenAction;
import de.htwg.se.nmm.model.Field;
import de.htwg.se.nmm.model.Gamefield;
import de.htwg.se.nmm.model.Player;
import de.htwg.se.nmm.model.Token;
import de.htwg.se.nmm.model.Player.Status;
import de.htwg.se.nmm.util.observer.Observable;

public final class NmmController extends Observable {
	
	private String status = "Nine Men's Morris";
	private Gamefield gamefield;	
	private Player playerOne, playerTwo, currentPlayer;
	private IAction action;
	
	public NmmController(Gamefield gamefield, Player p1, Player p2) {
		this.gamefield = gamefield;		
		this.playerOne = p1;
		this.playerTwo = p2;
		this.currentPlayer = playerOne;
	}
		
	
	public void restart() {
		gamefield.init();
		playerOne.init();
		playerTwo.init();
		currentPlayer = playerOne;		
		status = "Nine Men's Morris";
		notifyObservers();
	}	
	
	public void pickToken(int grid, int index) {
		Field field = gamefield.field(grid, index);
		action = new PickTokenAction(currentPlayer, field);
		if (action.valid()) {
			action.execute();					
			status = "picked token from " + field;
			if (gamefield.countToken(otherPlayer()) == 2 && !otherPlayer().hasToken()){				
				currentPlayer = otherPlayer();
				currentPlayer.setStatus(Status.GameLost);
				status += "\nplayer " + currentPlayer + " lost game!";
			}
		} else {
			status = "couldn'token pick token from " + field;
		}
		notifyObservers();
	}

	public void setToken(int grid, int index) {
		Field field = gamefield.field(grid, index);
		action = new SetTokenAction(currentPlayer, field);
		if (action.valid()) {
			action.execute();	
			if (gamefield.mill(field)) {
				status = "mill!";
				currentPlayer.setStatus(Status.PickToken);
			} else {
				status = "set token to " + field;
				nextPlayer();
			}			
		} else {	
			status = "couldn'token set token to " + field;
		}		
		notifyObservers();			
	}

	public void moveToken(int sourceGrid, int sourceIndex, int destGrid, int destIndex) {
		Field source = gamefield.field(sourceGrid, sourceIndex);
		Field dest   = gamefield.field(destGrid, destIndex);
		action = new MoveTokenAction(currentPlayer, source, dest);		
		if (action.valid()) {
			action.execute();			
			if (gamefield.mill(dest)) {				
				status = "mill!";
				currentPlayer.setStatus(Status.PickToken);
			} else {
				status = "moved token to " + dest;
				nextPlayer();
			}
		} else {
			status = "couldn'token move token to " + dest;			
		}		
		notifyObservers();						
	}
	
	public Player currentPlayer() {
		return currentPlayer;
	}
	
	public Player otherPlayer() {
		if (currentPlayer == playerOne) {
			return playerTwo;
		}
		return playerOne;
	}
	
	private void nextPlayer() {
		currentPlayer = otherPlayer();		
		if (currentPlayer.hasToken()) {
			currentPlayer.setStatus(Status.SetToken);
		} else {
			currentPlayer.setStatus(Status.MoveToken);
		}
	}

	public String getStatus() {		
		return status;
	}

	public Token getToken(int grid, int index) {
		return gamefield.field(grid, index).getToken();
	}
	
	public boolean valid(int grid, int index) {
		return gamefield.valid(grid, index);
	}			
	
	public int grids() {
		return gamefield.grids();
	}
	
	public int index() {
		return gamefield.index();
	}
}
