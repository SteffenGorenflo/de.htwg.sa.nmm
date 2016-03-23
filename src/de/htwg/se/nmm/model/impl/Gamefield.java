package de.htwg.se.nmm.model.impl;

import de.htwg.se.nmm.model.IField;
import de.htwg.se.nmm.model.IGamefield;
import de.htwg.se.nmm.model.IPlayer;
import de.htwg.se.nmm.model.IToken;

public final class Gamefield implements IGamefield {	

	private IPlayer player1, player2, currentPlayer;
	private static final int GRIDS = 3;
	private static final int INDEX = 8;	
	private Field[][] gamefield = null;	

	public Gamefield(IPlayer player1, IPlayer player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
		init();
	}
	
	public void init() {
        this.currentPlayer = player1;
		gamefield = new Field[GRIDS][INDEX];
		for (int grid = 0; grid < GRIDS; grid++) {
			for (int index = 0; index < INDEX; index++) {
				gamefield[grid][index] = new Field(grid, index);
			}
		}
	}			
	
	public Field field(int grid, int index) {
		return gamefield[grid][index];
	}
	
	public int countToken(IPlayer player) {
		int countToken = 0;
		for (int grid = 0; grid < GRIDS; grid++) {
			for (int index = 0; index < INDEX; index++) {
				Field field = gamefield[grid][index];				
				if (field.hasToken() && field.getToken().color().equals(player.color())) {			
					countToken++;					
				}					
			}
		}		
		return countToken;
	}

	
	public boolean mill(IField field) {				
		int grid = field.grid();
		int index = field.index();
		if (field.corner()) {
			return checkCorner(grid, index);
		}								
		return checkMid(grid, index);		
	}					
	
	private boolean checkNeighbours(int grid, int index, boolean betweenGrid) {
		final int tokenForMill = 3;				
		IToken[] three = new IToken[tokenForMill];
		for (int i = -1; i < 2; i++) {			
			int curGrid = grid;
			int curIndex = index;
			if (!betweenGrid) {
				curIndex = (index+i)%INDEX;				 
			} else {
				curGrid = grid +i;				
			}	
			three[i+1] = gamefield[curGrid][curIndex].getToken();
			if (three[i+1] == null) {
				return false;		
			}
		}				
		// equals is implemented transitive
		return three[0].equals(three[1]) && three[1].equals(three[2]);		
	}			
		
	private boolean checkCorner(int grid, int index) {
		final int indexOverflow = 7;
		return checkNeighbours(grid, (index+indexOverflow)%INDEX, false) || checkNeighbours(grid, index + 1, false);
	}
	
	private boolean checkMid(int grid, int index) {
		return checkNeighbours(1, index, true) || checkNeighbours(grid, index, false);
	}
			
	public int grids() {
		return GRIDS;
	}
	
	public int index() {
		return INDEX;
	}
	
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
			currentPlayer= player2;
		}
	}

	@Override
	public void setStatus(IPlayer.Status status) {
        currentPlayer.setStatus(status);
	}
}
