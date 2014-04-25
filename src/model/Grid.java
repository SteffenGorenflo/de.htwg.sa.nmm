package model;


public class Grid {	
	
	private final int GRID = 3;
	private final int INDEX = 8;
	private final int FLYLIMIT = 3;
	private Field[][] gamefield = null;
	private Player playerOne, playerTwo, currentPlayer;
	
	public Grid(Player playerOne, Player playerTwo) {	
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
		init();
	}
	
	public void init() {
		gamefield = new Field[GRID][INDEX];
		for (int grid = 0; grid < GRID; grid++) {
			for (int index = 0; index < INDEX; index++) {
				gamefield[grid][index] = new Field(grid, index);
			}
		}
		playerOne.init();
		playerTwo.init();
		currentPlayer = playerOne;
	}		
	
	public Token getToken(int grid, int index) {
		return gamefield[grid][index].getToken();
	}
	
	public boolean isEmpty(int grid, int index) {
		return !gamefield[grid][index].hasToken();
	}
	
	public boolean setToken(int grid, int index) {
		
		if (!currentPlayer.isStatus(Player.STATUS_SET)) {
			return false;
		}
		
		Field field = gamefield[grid][index];
		if (field.hasToken())
			return false;
		
		Token token = currentPlayer.take();
		field.setToken(token);		
		if (mill(grid, index)) {
			currentPlayer.setStatus(Player.STATUS_PICK);
		} else {
			nextPlayer();
		}
		return true;
	}		
	
	public int countToken(Player player) {
		int countToken = 0;
		for (int grid = 0; grid < GRID; grid++) {
			for (int index = 0; index < INDEX; index++) {
				Field field = gamefield[grid][index];				
				if (field.hasToken() && player.myToken(field.getToken())) {					
					countToken++;					
				}					
			}
		}		
		return countToken;
	}

	public boolean pickToken(int grid, int index) {
		
		if (!currentPlayer.isStatus(Player.STATUS_PICK))
			return false;
		
		Field field = gamefield[grid][index];		
		if (!field.hasToken())		
			return false;		
		if (currentPlayer().myToken(field.getToken()))
			return false;		
		field.setToken(null);		
		nextPlayer();
		return true;
	}
	
			
	public boolean moveToken(int sourceGrid, int sourceIndex, int destGrid, int destIndex) {
		
		if (!currentPlayer.isStatus(Player.STATUS_MOVE))
			return false;
		
		Field source = gamefield[sourceGrid][sourceIndex];
		Field dest   = gamefield[destGrid][destIndex];
		if (!source.hasToken() || dest.hasToken()) {
			return false;
		}
		
		if (countToken(currentPlayer) > FLYLIMIT && !source.neighbour(dest)) {
			return false;
		}
		Token token = source.getToken();
		dest.setToken(token);
		source.setToken(null);
		if (!mill(destGrid, destIndex)) {
			nextPlayer();
		} else {
			currentPlayer.setStatus(Player.STATUS_PICK);
		}
		return true;
	}
	
	public boolean mill(int grid, int index) {			
		Field field = gamefield[grid][index];
		if (field.corner()) {
			return checkCorner(grid, index);
		}								
		return checkMid(grid, index);		
	}					
	
	private boolean checkNeighbours(int grid, int index, boolean betweenGrid) {
		Token[] three = new Token[3];
		for (int i = -1; i < 2; i++) {			
			int curGrid = grid;
			int curIndex = index;
			if (!betweenGrid) {
				curIndex = (index+i)%8;				 
			} else {
				curGrid = grid +i;				
			}	
			three[i+1] = gamefield[curGrid][curIndex].getToken();
			if (three[i+1] == null)
				return false;			
		}				
		// equals is implemented transitive
		return three[0].equals(three[1]) && three[1].equals(three[2]);		
	}			
		
	private boolean checkCorner(int grid, int index) {
		return checkNeighbours(grid, (index+7)%8, false) || checkNeighbours(grid, index + 1, false);
	}
	
	private boolean checkMid(int grid, int index) {
		return checkNeighbours(1, index, true) || checkNeighbours(grid, index, false);
	}
	
	public void setCurrentPlayer(final Player player) {
		this.currentPlayer = player;
	}
	
	public Player currentPlayer() {			
		return currentPlayer;
	}
	
	public Player otherPlayer() {
		if (currentPlayer == playerOne){
			return playerTwo;
		}
		return playerOne;
	}

	private void nextPlayer() {
		currentPlayer = otherPlayer();			
		if (currentPlayer.hasToken()) {
			currentPlayer.setStatus(Player.STATUS_SET);
		} else {
			currentPlayer.setStatus(Player.STATUS_MOVE);
		}
	}			
	
	public int grids() {
		return GRID;
	}
	
	public int index() {
		return INDEX;
	}
	
	public boolean valid(int grid, int index) {	
		if (grid < GRID && index < INDEX) {
			return true;
		}
		return false;			
	}
}
