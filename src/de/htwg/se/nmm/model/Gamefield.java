package de.htwg.se.nmm.model;


public final class Gamefield {	
	
	private static final int GRIDS = 3;
	private static final int INDEX = 8;	
	private Field[][] gamefield = null;	
	
	public Gamefield() {			
		init();
	}
	
	public void init() {
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
	
	public int countToken(Player player) {
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

	
	public boolean mill(Field field) {				
		int grid = field.grid();
		int index = field.index();
		if (field.corner()) {
			return checkCorner(grid, index);
		}								
		return checkMid(grid, index);		
	}					
	
	private boolean checkNeighbours(int grid, int index, boolean betweenGrid) {
		final int tokenForMill = 3;				
		Token[] three = new Token[tokenForMill];
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
}
