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
		print();		
	}	
	
	private void print() {
		
	}
	
	public void handleUserInput(final String input) {
				
		if (input.equals("q")) {
			System.out.println("exit game");
			System.exit(0);
			
		} else if (input.equals("r")) {
			controller.restart();
			System.out.println("restart game");
			
		} else if (input.matches("move\\d\\dto\\d\\d")) {
			int sourceGrid = Character.getNumericValue(input.charAt(4));
			int sourceIndex = Character.getNumericValue(input.charAt(5));
			int destGrid = Character.getNumericValue(input.charAt(8));
			int destIndex = Character.getNumericValue(input.charAt(9));
			
			if (controller.valid(sourceGrid, sourceIndex) && controller.valid(destGrid, destIndex)) {
				controller.moveToken(sourceGrid, sourceIndex, destGrid, destIndex);
			} else {
				System.out.println("invalid index");
			}
			
		} else if (input.matches("pick\\d\\d")) {
			int grid = Character.getNumericValue(input.charAt(4));
			int index = Character.getNumericValue(input.charAt(5));
			
			if (controller.valid(grid, index)) {
				controller.pickToken(grid, index);
			} else {
				System.out.println("invalid index");
			}
			
			
		} else if (input.matches("set\\d\\d")) {
			int grid = Character.getNumericValue(input.charAt(3));
			int index = Character.getNumericValue(input.charAt(4));
			System.out.println("grid/index: " + grid + "/" + index); 
			if (controller.valid(grid, index)) {					
				controller.setToken(grid, index);
			} else {
				System.out.println("invalid index");
			}
		}
	}
}
