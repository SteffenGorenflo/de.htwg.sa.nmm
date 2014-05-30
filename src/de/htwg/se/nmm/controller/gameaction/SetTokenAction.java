package de.htwg.se.nmm.controller.gameaction;

import de.htwg.se.nmm.model.IField;
import de.htwg.se.nmm.model.IPlayer;
import de.htwg.se.nmm.model.IPlayer.Status;
import de.htwg.se.nmm.model.IToken;

public class SetTokenAction implements IAction {

	private final IPlayer player;
	private final IField field;
	
	public SetTokenAction(IPlayer player, IField field) {
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
		IToken t = player.takeToken();
		field.setToken(t);
	}
}
