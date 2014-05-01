package de.htwg.se.nmm.controller.gameaction;

import de.htwg.se.nmm.model.Field;
import de.htwg.se.nmm.model.Player;
import de.htwg.se.nmm.model.Token;
import de.htwg.se.nmm.model.Player.Status;

public class SetTokenAction implements IAction {

	private final Player player;
	private final Field field;
	
	public SetTokenAction(Player player, Field field) {
		this.player = player;		
		this.field = field;
	}
	
	@Override
	public boolean valid() {
		
		// player is not allowed to set a token
		if (!player.isStatus(Status.SetToken)) {
			return false;
		}			
		
		// player has no tokens anymore
		if (!player.hasToken()) {
			return false;
		}
		
		// there is already another token
		if (field.hasToken()) {
			return false;
		}
					
		return true;
	}

	@Override
	public void execute() {
		Token t = player.takeToken();
		field.setToken(t);
	}
}
