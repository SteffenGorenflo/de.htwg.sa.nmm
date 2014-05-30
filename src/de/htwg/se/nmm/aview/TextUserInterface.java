package de.htwg.se.nmm.aview;

import java.awt.Color;
import de.htwg.se.nmm.controller.NmmController;
import de.htwg.se.nmm.model.Token;
import de.htwg.se.nmm.util.observer.Event;
import de.htwg.se.nmm.util.observer.IObserver;


public final class TextUserInterface implements IObserver {	
	 
	private NmmController controller;
	
	public TextUserInterface(NmmController controller) {
		this.controller = controller;
		this.controller.addObserver(this);
		printGamefield();
	}
	 
	@Override
	public void update(Event e) {		
		String status = controller.getStatus();
		println("TUI received an updated from Controller: " + status);
		printGamefield();		
	}	
	
	private void print(final String s) {
		System.out.print(s);
	}
	
	private void println(final String s) {
		print(s+"\n");		
	}
	
	private void printGamefield() {
		
		/* print first row */
		printToken(0, 0);
		print(" - - - - - ");
		printToken(0, 1);
		print(" - - - - - ");
		printToken(0, 2);				
		print("\n|           |           |\n");
		
		/* print second row */
		print("|   ");
		printToken(1, 0);
		print(" - - - ");
		printToken(1, 1);
		print(" - - - ");
		printToken(1, 2);
		print("   |\n");
		print("|   |       |       |   |\n");
		
		
		/* print third row */
		print("|   |   ");
		printToken(2, 0);
		print(" - ");
		printToken(2, 1);
		print(" - ");
		printToken(2, 2);
		print("   |   | \n");				
		print("|   |   |       |   |   |\n");
		
		/* print fourth row */		
		printToken(0, 7);
		print(" - ");
		printToken(1, 7);
		print(" - ");
		printToken(2, 7);
		print("       ");
		printToken(2, 3);
		print(" - ");
		printToken(1, 3);
		print(" - ");
		printToken(0, 3);
		print("\n");				
		
		/* print fifth row */
		print("|   |   ");
		printToken(2, 0);
		print(" - ");
		printToken(2, 1);
		print(" - ");
		printToken(2, 2);
		print("   |   | \n");				
		print("|   |   |       |   |   |\n");
	}	
	
	private void printToken(int grid, int index) {
		assert(controller.valid(grid, index));
		Token t = controller.getToken(grid, index);
		/* we have no token */
		if (t == null) {
			print("x");
			return;
		}
		
		final String emptyCircle = "\u25EF";
		final String filledCircle = "\u2B24";
		if (t.color() == Color.BLACK) {
			print("B");
		} else if (t.color() == Color.WHITE){
			print("W");
		}		 	
	}
	
	public void handleUserInput(final String input) {
				
		if (input.equals("q")) {
			
			print("exit");
			System.exit(0);
		}
		else if (input.equals("r")) {
			
			controller.restart();
			println("restart game");
			
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
				println("invalid index");
			}
			
		} else if (input.matches("pick\\d\\d")) {
			
			final int strPosGrid = 4;
			final int strPosIndex = 5;
			int grid = Character.getNumericValue(input.charAt(strPosGrid));
			int index = Character.getNumericValue(input.charAt(strPosIndex));
			
			if (controller.valid(grid, index)) {
				controller.pickToken(grid, index);
			} else {
				println("invalid index");
			}
						
		} else if (input.matches("set\\d\\d")) {
			
			final int strPosGrid = 3;
			final int strPosIndex = 4;
			int grid = Character.getNumericValue(input.charAt(strPosGrid));
			int index = Character.getNumericValue(input.charAt(strPosIndex));
			
			print("grid/index: " + grid + "/" + index); 
			if (controller.valid(grid, index)) {					
				controller.setToken(grid, index);
			} else {
				println("invalid index");
			}
			
		} else {
			
			println("invalid input: " + input);
			
		}		
	}
}
