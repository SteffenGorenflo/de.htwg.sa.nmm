package de.htwg.sa.nmm;

import java.util.Scanner;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.htwg.sa.nmm.aview.GraphicalUserInterface;
import de.htwg.sa.nmm.aview.TextUserInterface;
import de.htwg.sa.nmm.controller.impl.NmmController;
import de.htwg.sa.nmm.model.IGamefield;
import de.htwg.sa.nmm.model.IPlayer;
import de.htwg.sa.nmm.model.IToken;
import de.htwg.sa.nmm.model.impl.Gamefield;
import de.htwg.sa.nmm.model.impl.Player;

public final class NineMensMorris {

	private NineMensMorris() {
	}

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new NineMensMorrisModule());
		IPlayer p1 = new Player("Spieler 1", IToken.Color.BLACK);
		IPlayer p2 = new Player("Spieler 2", IToken.Color.WHITE);
		IGamefield nmm = new Gamefield(p1, p2);
		NmmController controller = new NmmController(nmm);
		TextUserInterface tui = new TextUserInterface(controller);
		GraphicalUserInterface gui = new GraphicalUserInterface(controller);
		gui.setVisible(true);

        final Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			tui.handleUserInput(in.nextLine());
		}
		in.close();
	}
}
