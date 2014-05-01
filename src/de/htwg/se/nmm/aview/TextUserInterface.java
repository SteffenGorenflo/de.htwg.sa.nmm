package de.htwg.se.nmm.aview;

import de.htwg.se.nmm.controller.NmmController;
import de.htwg.se.nmm.util.observer.Event;
import de.htwg.se.nmm.util.observer.IObserver;

public final class TextUserInterface implements IObserver {	
	
	private NmmController controller;
	
	public TextUserInterface(NmmController controller) {
		this.controller = controller;
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
		}				
		else if (input.matches("[move\\d\\dto\\d\\d]")) {
			int sourceGrid = Integer.valueOf(input.charAt(4));
			int sourceIndex = Integer.valueOf(input.charAt(5));
			int destGrid = Integer.valueOf(input.charAt(9));
			int destIndex = Integer.valueOf(input.charAt(10));	
			
			if (controller.valid(sourceGrid, sourceIndex) && controller.valid(destGrid, destIndex)) {
				controller.moveToken(sourceGrid, sourceIndex, destGrid, destIndex);
			} else {
				System.out.println("invalid index");
			}			
		}
		else if (input.matches("[pick\\d\\d]")) {
			int grid = Integer.valueOf(input.charAt(4));
			int index = Integer.valueOf(input.charAt(5));
			
			controller.pickToken(grid, index);
		}				
	}
}
