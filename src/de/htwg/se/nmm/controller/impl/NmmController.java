package de.htwg.se.nmm.controller.impl;

import java.util.Stack;

import de.htwg.se.nmm.controller.INmmController;
import de.htwg.se.nmm.controller.commands.IGameCommand;
import de.htwg.se.nmm.controller.commands.MoveTokenCommand;
import de.htwg.se.nmm.controller.commands.PickTokenCommand;
import de.htwg.se.nmm.controller.commands.SetTokenCommand;
import de.htwg.se.nmm.model.IField;
import de.htwg.se.nmm.model.IGamefield;
import de.htwg.se.nmm.model.IPlayer;
import de.htwg.se.nmm.model.IPlayer.Status;
import de.htwg.se.nmm.model.IToken;
import de.htwg.se.nmm.util.observer.Observable;

public final class NmmController extends Observable implements INmmController {
	
	private String status = "Nine Men's Morris";
	private IGamefield gamefield;	
	private IPlayer playerOne, playerTwo, currentPlayer;
	private IGameCommand action = null;
	private Stack<IGameCommand> undoStack = new Stack<>();
	
	public NmmController(IGamefield gamefield, IPlayer p1, IPlayer p2) {
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
		undoStack.clear();
		notifyObservers();
	}	
	
	public void pickToken(int grid, int index) {
		IField field = gamefield.field(grid, index);
		action = new PickTokenCommand(currentPlayer, field);
		if (action.valid()) {
			action.execute();	
			undoStack.add(action);
			status = "picked token from " + field;
			if (gamefield.countToken(otherPlayer()) == 2 && !otherPlayer().hasToken()){				
				currentPlayer = otherPlayer();
				currentPlayer.setStatus(Status.GameLost);
				status += "\nplayer " + currentPlayer + " lost game!";
			} else {
				nextPlayer();
			}
		} else {			
			status = "couldn'token pick token from " + field;
		}
		notifyObservers();
	}

	public void setToken(int grid, int index) {
		IField field = gamefield.field(grid, index);
		action = new SetTokenCommand(currentPlayer, field);
		if (action.valid()) {
			action.execute();	
			undoStack.add(action);
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
		IField source = gamefield.field(sourceGrid, sourceIndex);
		IField dest   = gamefield.field(destGrid, destIndex);
		action = new MoveTokenCommand(currentPlayer, source, dest);		
		if (action.valid()) {
			action.execute();		
			undoStack.add(action);
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
	
	public IPlayer currentPlayer() {
		return currentPlayer;
	}
	
	public String color(int grid, int index) {
		final String whiteToken = "\u25CB";
		final String blackToken = "\u2B24";
		final String noToken = "+";
		IToken t = gamefield.field(grid, index).getToken();
		if (t == null) {
			return noToken;
		} else if (t.color() == java.awt.Color.WHITE) {
			return whiteToken;
		}
		return blackToken;
	}
	
	public IPlayer otherPlayer() {
		if (currentPlayer.equals(playerOne)) {
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

	public IToken getToken(int grid, int index) {
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

	@Override
	public boolean undo() {
		if (!undoStack.empty()) {
			if (undoStack.pop().undo()) {
				nextPlayer();
			}
			notifyObservers();
			return true;
		} 
		return false;
	}
}
