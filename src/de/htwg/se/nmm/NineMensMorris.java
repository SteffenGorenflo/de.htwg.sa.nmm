package de.htwg.se.nmm;

import java.awt.Color;
import java.util.Scanner;

import de.htwg.se.nmm.aview.TextUserInterface;
import de.htwg.se.nmm.controller.NmmController;
import de.htwg.se.nmm.model.Gamefield;
import de.htwg.se.nmm.model.Player;


public final class NineMensMorris {
	
	private NineMensMorris() { }
	
	public static void main(String[] args) {
		
		final Scanner IN = new Scanner(System.in);
		
		Player p1 = new Player("Markus", Color.BLACK);
		Player p2 = new Player("Andreas", Color.WHITE);
		Gamefield nmm = new Gamefield();
		NmmController controller = new NmmController(nmm, p1, p2);
		TextUserInterface tui = new TextUserInterface(controller);
		
		while (IN.hasNext()) {
			final String input = IN.next();
			if ("quit".equals(input)) {
				System.out.println("game ended");
				break;
			} else {
				tui.handleUserInput(input);
			}
		}
		IN.close();
	}
}
