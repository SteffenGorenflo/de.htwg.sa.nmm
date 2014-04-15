package controller;

import model.Grid;
import model.Token;
import util.observer.Observable;

public final class NmmController extends Observable {
	
	private String status = "Nine Men's Morris";
	private Grid model;	
	
	public NmmController(Grid model) {
		this.model = model;		
	}
		
	public void exit() {
		System.exit(0);
	}
	
	public void restart() {
		model.init();
		status = "Nine Men's Morris";
		notifyObservers();
	}
	
	private String toString(int grid, int index) {
		return "(" + grid + ", " + index + ")" + "[COLOR: " + model.getToken(grid, index).color() + "]";
	}
	
	public boolean pickToken(int grid, int index) {
		boolean pick;
		if (model.pickToken(grid, index)) {
			status = "picked token from " + toString(grid, index);
			pick = true;
		} else {
			status = "couldn't pick token from " + toString(grid, index);
			pick = false;
		}
		notifyObservers();
		return pick;
	}

	public boolean setToken(int grid, int index) {
		boolean set;
		if (model.setToken(grid, index)) {
			status = "set token to " + toString(grid, index);
			set = true;
		} else {
			status = "couldn't set token to " + toString(grid, index);
			set = false;
		}
		notifyObservers();
		return set;		
	}

	public boolean moveToken(int sourceGrid, int sourceIndex, int destGrid, int destIndex) {
		boolean move;
		if (model.moveToken(sourceGrid, sourceIndex, destGrid, destIndex)) {
			if (model.mill(destGrid, destIndex)) {
				status = "moved token to " + toString(destGrid, destIndex);
				notifyObservers();
				status = "mill!";				
			}			
			move = true;
		} else {
			status = "couldn't move token to " + toString(destGrid, destIndex);
			move = false;
		}		
		notifyObservers();				
		return move;
	}

	public String getStatus() {		
		return status;
	}

	public Token getToken(int grid, int index) {
		return model.getToken(grid, index);
	}
	
	public boolean valid(int grid, int index) {
		return model.valid(grid, index);
	}		
}
