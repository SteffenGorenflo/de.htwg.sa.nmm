package de.htwg.se.nmm.aview;

import de.htwg.se.nmm.controller.NmmController;
import de.htwg.se.nmm.util.observer.Event;
import de.htwg.se.nmm.util.observer.IObserver;

public final class TextUserInterface implements IObserver {	
	 
	private NmmController controller;
	
	public TextUserInterface(NmmController controller) {
		this.controller = controller;
		this.controller.addObserver(this);
	}
	 
	@Override
	public void update(Event e) {		
		String status = controller.getStatus();
		System.out.println("TUI received an updated from Controller: " + status);
		printGamefield();		
	}	
	
	private void printGamefield() {
		
	}
	
	public void handleUserInput(final String input) {
				
		if (input.equals("r")) {
			controller.restart();
			System.out.println("restart game");
			
		} else if (input.matches("move\\d\\dto\\d\\d")) {
			
			final int strPosSourceGrid = 4;
			final int strPosSourceIndex = 5;			
			int sourceGrid = Character.getNumericValue(input.charAt(strPosSourceIndex));
			int sourceIndex = Character.getNumericValue(input.charAt(strPosSourceGrid));
			
			final int strPosDestGrid = 8;
			final int strPosDestIndex = 9;
			int destGrid = Character.getNumericValue(input.charAt(strPosDestGrid));
			int destIndex = Character.getNumericValue(input.charAt(strPosDestIndex));
			
			if (controller.valid(sourceGrid, sourceIndex) && controller.valid(destGrid, destIndex)) {
				controller.moveToken(sourceGrid, sourceIndex, destGrid, destIndex);
			} else {
				System.out.println("invalid index");
			}
			
		} else if (input.matches("pick\\d\\d")) {
			
			final int strPosGrid = 4;
			final int strPosIndex = 5;
			int grid = Character.getNumericValue(input.charAt(strPosGrid));
			int index = Character.getNumericValue(input.charAt(strPosIndex));
			
			if (controller.valid(grid, index)) {
				controller.pickToken(grid, index);
			} else {
				System.out.println("invalid index");
			}
			
			
		} else if (input.matches("set\\d\\d")) {
			
			final int strPosGrid = 3;
			final int strPosIndex = 4;
			int grid = Character.getNumericValue(input.charAt(strPosGrid));
			int index = Character.getNumericValue(input.charAt(strPosIndex));
			
			System.out.println("grid/index: " + grid + "/" + index); 
			if (controller.valid(grid, index)) {					
				controller.setToken(grid, index);
			} else {
				System.out.println("invalid index");
			}
		}
	}
}
