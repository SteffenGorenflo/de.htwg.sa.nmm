package de.htwg.sa.nmm.model.impl;

import de.htwg.sa.nmm.model.IField;
import de.htwg.sa.nmm.model.IToken;

public final class Field implements IField {
	
	private int grid;
	private int index;
	private IToken token;
	
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
	
	public IToken getToken() {
		return token;
	}
	
	public boolean hasToken() {
		return this.token != null;
	}
	
	public void setToken(IToken token) {
		this.token = token;
	}
	
	public boolean neighbour(IField other) {
		boolean neighbour = false;
		if (sameGrid(other) && indexNeighbour(other)) {
			neighbour = true;
		} else if (sameIndex(other) && gridNeighour(other) && !corner()) {
			neighbour = true;
		}				
		return neighbour;
	}
	
	public boolean sameIndex(IField other) {
		return (other.index() == index) ? true : false;
	}
	
	public boolean indexNeighbour(IField other) {
		int indexDelta = Math.abs(other.index() - index);
		final int indexOverflow = 7;
		return (indexDelta == indexOverflow || indexDelta == 1) ? true : false;
	}
			
	public boolean sameGrid(IField other) {
		return (other.grid() == grid) ? true : false;
	}
	
	public boolean gridNeighour(IField other) {
		int gridDelta = Math.abs(other.grid() - grid);
		return (gridDelta == 1) ? true : false;
	}
	
	public boolean corner() {
		return (index % 2 == 0) ? true : false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + grid;
		result = prime * result + index;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Field)) {
			return false;
		}			
		Field other = (Field) obj;
		return grid == other.grid && index == other.index;
	}		
	
	@Override
	public String toString() {
		return "Field [grid=" + grid + ", index=" + index + "]";
	}
}
