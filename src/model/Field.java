package model;

public final class Field {
	
	private int grid;
	private int index;
	private Token token;
	
	public Field(int grid, int index) {
		this.grid = grid;
		this.index = index;
	}
	
	public int grid() {
		return grid;
	}
	
	public int index() {
		return index;
	}
	
	public Token getToken() {
		return token;
	}
	
	public boolean hasToken() {
		return this.token != null;
	}
	
	public void setToken(Token token) {
		this.token = token;
	}
	
	public boolean neighbour(Field other) {
		boolean neighbour = false;
		if (sameGrid(other) && indexNeighbour(other)) {
			neighbour = true;
		} else if (sameIndex(other) && gridNeighour(other) && !corner()) {
			neighbour = true;
		}				
		return neighbour;
	}
	
	public boolean sameIndex(Field other) {
		return (other.index == index) ? true : false;
	}
	
	public boolean indexNeighbour(Field other) {
		int indexDelta = Math.abs(other.index - index);
		return (indexDelta == 7 || indexDelta == 1) ? true : false;
	}
			
	public boolean sameGrid(Field other) {
		return (other.grid == grid) ? true : false;
	}
	
	public boolean gridNeighour(Field other) {
		int gridDelta = Math.abs(other.grid - grid);
		return (gridDelta == 1) ? true : false;
	}
	
	public boolean corner() {
		return (index % 2 == 0) ? true : false;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Field))
			return false;
		Field other = (Field) obj;
		return grid == other.grid && index == other.index;
	}
}
