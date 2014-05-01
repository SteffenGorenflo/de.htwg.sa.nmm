package de.htwg.se.nmm.controller.gameaction;

import de.htwg.se.nmm.model.Field;
import de.htwg.se.nmm.model.Player;
import de.htwg.se.nmm.model.Token;
import de.htwg.se.nmm.model.Player.Status;

public class MoveTokenAction implements IAction {

	private final Player player;
	private final Field source, destination;
	
	public MoveTokenAction(Player player, Field source, Field destination) {
		this.player = player;
		this.source = source;
		this.destination = destination;
	}
	
	@Override
	public boolean valid() {
		
		// player is not allowed to set a token
		if (!player.isStatus(Status.MoveToken)) {
			return false;
		}			
		
		// there is no token to move
		if (!source.hasToken()) {
			return false;
		}
		
		// there is already another token
		if (destination.hasToken()) {
			return false;
		}
		
		// it's not the players token
		if (!player.color().equals(source.getToken().color())) {
			return false;
		}
					
		return true;
	}

	@Override
	public void execute() {
		Token t = source.getToken();
		source.setToken(null);
		destination.setToken(t);
	}

}
