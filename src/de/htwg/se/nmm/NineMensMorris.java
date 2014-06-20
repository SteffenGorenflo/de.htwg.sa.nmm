package de.htwg.se.nmm;

import java.awt.Color;
import java.util.Scanner;

import de.htwg.se.nmm.aview.GraphicalUserInterface;
import de.htwg.se.nmm.aview.TextUserInterface;
import de.htwg.se.nmm.controller.impl.NmmController;
import de.htwg.se.nmm.model.impl.Gamefield;
import de.htwg.se.nmm.model.impl.Player;


public final class NineMensMorris {
	
	private NineMensMorris() { }
	
	public static void main(String[] args) {
		
		final Scanner in = new Scanner(System.in);
		
		Player p1 = new Player("Spieler 1", Color.BLACK);
		Player p2 = new Player("Spieler 2", Color.WHITE);		
		Gamefield nmm = new Gamefield();
		NmmController controller = new NmmController(nmm, p1, p2);
		TextUserInterface tui = new TextUserInterface(controller);
		GraphicalUserInterface gui = new GraphicalUserInterface(controller);
		gui.setVisible(true);
		
		while (in.hasNext()) {			
			tui.handleUserInput(in.next());			
		}
		in.close();
	}		
}
