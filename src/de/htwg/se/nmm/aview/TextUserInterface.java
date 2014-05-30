package de.htwg.se.nmm.aview;

import de.htwg.se.nmm.controller.impl.NmmController;
import de.htwg.se.nmm.util.observer.Event;
import de.htwg.se.nmm.util.observer.IObserver;

public final class TextUserInterface implements IObserver {	
	 
	private NmmController controller;
	
	public TextUserInterface(NmmController controller) {
		this.controller = controller;
		this.controller.addObserver(this);
		printGamefield();
		println(controller.currentPlayer().name() + ", now it's your turn!");
	}
	 
	@Override
	public void update(Event e) {		
		String status = controller.getStatus();
		println("TUI received an updated from Controller: " + status);
		printGamefield();	
		println(controller.currentPlayer().name() + ", now it's your turn!");
	}	
	
	private void print(final String s) {
		System.out.print(s);
	}
	
	private void println(final String s) {
		print(s+"\n");		
	}		
	
	private void printGamefield() {			
		
		final int outerGrid = 0;
		final int middleGrid = 1;
		final int innerGrid = 2;
		
		final int topLeft = 0;
		final int top = 1;
		final int topRight =2;
		final int right = 3;
		final int bottomRight = 4;
		final int bottom = 5;
		final int bottomLeft = 6;
		final int left = 7;		
		
		/* print first row */
		print(controller.color(outerGrid, topLeft));		
		print("-----------");
		print(controller.color(outerGrid, top));		
		print("-----------");
		print(controller.color(outerGrid, topRight));		
		print("\n|           |           |\n");
		
		/* print second row */
		print("|   ");		
		print(controller.color(middleGrid, topLeft));
		print("-------");
		print(controller.color(middleGrid, top));		
		print("-------");
		print(controller.color(middleGrid, topRight));		
		print("   |\n");
		print("|   |       |       |   |\n");
				
		/* print third row */
		print("|   |   ");
		print(controller.color(innerGrid, topLeft));		
		print("---");
		print(controller.color(innerGrid, top));		
		print("---");
		print(controller.color(innerGrid, topRight));		
		print("   |   | \n");				
		print("|   |   |       |   |   |\n");
		
		/* print fourth row */		
		print(controller.color(outerGrid, left));		
		print("---");
		print(controller.color(middleGrid, left));		
		print("---");
		print(controller.color(innerGrid, left));		
		print("       ");
		print(controller.color(innerGrid, right));		
		print("---");
		print(controller.color(middleGrid, right));		
		print("---");
		print(controller.color(outerGrid, right));		
		print("\n");		
		print("|   |   |       |   |   |\n");		
		
		/* print fifth row */
		print("|   |   ");
		print(controller.color(innerGrid, bottomLeft));		
		print("---");
		print(controller.color(innerGrid, bottom));		
		print("---");
		print(controller.color(innerGrid, bottomRight));		
		print("   |   | \n");				
		print("|   |       |       |   |\n");
		
		/* print sixth row */
		print("|   ");
		print(controller.color(middleGrid, bottomLeft));
		print("-------");
		print(controller.color(middleGrid, bottom));
		print("-------");
		print(controller.color(middleGrid, bottomRight));
		print("   |\n");
		print("|           |           |\n");
		
		/* print seventh row */
		print(controller.color(outerGrid, bottomLeft));
		print("-----------");
		print(controller.color(outerGrid, bottom));		
		print("-----------");
		print(controller.color(outerGrid, bottomRight));
		println("\n");
	}		
	
	private void printHelp() {
		println("setXY - set token to grid 'X' at index 'Y'");
		println("pickXY - pick token from grid 'X' at index 'Y'");
		println("moveABtoXY - move token from 'AB' to 'XY' (GridIndex)");
		println("r - restart game");
		println("q - quit game");	
		println("h - print help");
	}
	
	public void handleUserInput(final String input) {
				
		if (input.equals("q")) {
			
			print("exit");
			System.exit(0);
		}
		else if (input.equals("r")) {
			
			controller.restart();
			println("restart game");
			
		} else if (input.equals("h")) {
			
			printHelp();
			
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
