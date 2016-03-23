package de.htwg.sa.nmm.aview;

import de.htwg.sa.nmm.controller.INmmController;
import de.htwg.sa.nmm.util.observer.Event;
import de.htwg.sa.nmm.util.observer.IObserver;

public final class TextUserInterface implements IObserver {	
	 
	private INmmController controller;
	
	public TextUserInterface(INmmController controller) {
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
		print(s + "\n");
	}		
	
	private void printGamefield() {
        println(gamefieldAsString());
	}		

	public String gamefieldAsString() {

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

        final String longDashLine = "-----------";
        final String middleDashLine = "-------";
        final String shortDashLine = "---";
        final String nl = System.lineSeparator();

		/* print first row */
        StringBuilder s = new StringBuilder();


        s.append(controller.color(outerGrid, topLeft));
        s.append(longDashLine);
		s.append(controller.color(outerGrid, top));
		s.append(longDashLine);
		s.append(controller.color(outerGrid, topRight));
		s.append(nl + "|           |           |" + nl);


		/* print second row */
        s.append("|   ");
        s.append(controller.color(middleGrid, topLeft));
		s.append(middleDashLine);
		s.append(controller.color(middleGrid, top));
		s.append(middleDashLine);
		s.append(controller.color(middleGrid, topRight));
		s.append("   |" + nl);
        s.append("|   |       |       |   |" + nl);


		/* print third row */

		s.append("|   |   ");
		s.append(controller.color(innerGrid, topLeft));
		s.append(shortDashLine);
		s.append(controller.color(innerGrid, top));
		s.append(shortDashLine);
		s.append(controller.color(innerGrid, topRight));
		s.append("   |   | " + nl);
		s.append("|   |   |       |   |   |" + nl);

		/* print fourth row */
		s.append(controller.color(outerGrid, left));
		s.append(shortDashLine);
		s.append(controller.color(middleGrid, left));
		s.append(shortDashLine);
		s.append(controller.color(innerGrid, left));
		s.append("       ");
		s.append(controller.color(innerGrid, right));
		s.append(shortDashLine);
		s.append(controller.color(middleGrid, right));
		s.append(shortDashLine);
		s.append(controller.color(outerGrid, right));
		s.append(nl);
		s.append("|   |   |       |   |   |" + nl);

		/* print fifth row */
		s.append("|   |   ");
		s.append(controller.color(innerGrid, bottomLeft));
		s.append(shortDashLine);
		s.append(controller.color(innerGrid, bottom));
		s.append(shortDashLine);
		s.append(controller.color(innerGrid, bottomRight));
		s.append("   |   | " + nl);
		s.append("|   |       |       |   |" + nl);

		/* print sixth row */
        s.append("|   ");
        s.append(controller.color(middleGrid, bottomLeft));
		s.append(middleDashLine);
		s.append(controller.color(middleGrid, bottom));
		s.append(middleDashLine);
		s.append(controller.color(middleGrid, bottomRight));
		s.append("   |" + nl);
        s.append("|           |           |" + nl);

		/* print seventh row */
        s.append(controller.color(outerGrid, bottomLeft));
        s.append(longDashLine);

        s.append(controller.color(outerGrid, bottom));
		s.append(longDashLine);
		s.append(controller.color(outerGrid, bottomRight));
        s.append(nl);

		return s.toString();
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
			
		} else if (input.equals("u")) {
			
			controller.undo();
			
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
