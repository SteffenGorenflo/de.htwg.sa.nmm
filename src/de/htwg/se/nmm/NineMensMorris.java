package de.htwg.se.nmm;

import java.awt.Color;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.Scanner;

import de.htwg.se.nmm.aview.GraphicalUserInterface;
import de.htwg.se.nmm.aview.TextUserInterface;
import de.htwg.se.nmm.controller.impl.NmmController;
import de.htwg.se.nmm.model.IGamefield;
import de.htwg.se.nmm.model.IPlayer;
import de.htwg.se.nmm.model.impl.Gamefield;
import de.htwg.se.nmm.model.impl.Player;

public final class NineMensMorris {

	private NineMensMorris() {
	}

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new NineMensMorrisModule());
		IPlayer p1 = new Player("Spieler 1", Color.BLACK);
		IPlayer p2 = new Player("Spieler 2", Color.WHITE);
		IGamefield nmm = new Gamefield(p1, p2);
		NmmController controller = new NmmController(nmm);
		TextUserInterface tui = new TextUserInterface(controller);
		GraphicalUserInterface gui = new GraphicalUserInterface(controller);
		gui.setVisible(true);

        final Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			tui.handleUserInput(in.next());
		}
		in.close();
	}
}
