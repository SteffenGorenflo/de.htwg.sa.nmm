package model;


/**
 * Grid represents the gamefield.
 */
public class Grid {	
	
	private final int GRID = 3;
	private final int INDEX = 8;
	private final int FLYLIMIT = 3;
	private final int DEATH_LIMIT = 2;
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
		if (gamefield[grid][index].hasToken())
			return false;
		Token token = currentPlayer.take();
		gamefield[grid][index].setToken(token);
		return true;
	}		
	
	public int countToken(Player player) {
		int countToken = 0;
		for (int grid = 0; grid < GRID; grid++) {
			for (int index = 0; index < INDEX; index++) {
				Token token = gamefield[grid][index].getToken();
				if (token != null && player.myToken(token))
					countToken++;
			}
		}		
		return countToken;
	}

	public boolean pickToken(int grid, int index) {
		Token token = gamefield[grid][index].getToken();
		if (token == null)
			return false;
		gamefield[grid][index].setToken(null);
		// lost game
		return (countToken(otherPlayer()) == DEATH_LIMIT) ? true : false;
	}
			
	public boolean moveToken(int sourceGrid, int sourceIndex, int destGrid, int destIndex) {
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

	public Player currentPlayer() {			
		return currentPlayer;
	}
	
	public Player otherPlayer() {
		return (currentPlayer == playerOne) ? playerTwo : playerOne;			
	}

	private void nextPlayer() {
		currentPlayer = (currentPlayer == playerOne) ? playerTwo : playerOne;			
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
		return (grid < GRID && index < INDEX) ? true : false;		
	}
}
