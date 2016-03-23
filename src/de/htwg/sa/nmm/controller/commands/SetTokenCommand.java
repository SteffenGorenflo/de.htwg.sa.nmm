package de.htwg.sa.nmm.controller.commands;

import de.htwg.sa.nmm.model.IField;
import de.htwg.sa.nmm.model.IPlayer;
import de.htwg.sa.nmm.model.IToken;
import de.htwg.sa.nmm.model.IPlayer.Status;

public class SetTokenCommand implements IGameCommand {

	private final IPlayer player;
	private final IField field;
	
	public SetTokenCommand(IPlayer player, IField field) {
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

	@Override
	public boolean undo() {
		field.setToken(null);
		player.addToken();	
		return true;
	}
}
