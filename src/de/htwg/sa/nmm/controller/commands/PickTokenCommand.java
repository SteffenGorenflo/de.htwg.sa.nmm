package de.htwg.sa.nmm.controller.commands;

import de.htwg.sa.nmm.model.IField;
import de.htwg.sa.nmm.model.IPlayer;
import de.htwg.sa.nmm.model.IToken;
import de.htwg.sa.nmm.model.IPlayer.Status;

public class PickTokenCommand implements IGameCommand {

	private final IPlayer player;
	private final IField field;
	private IToken token;
	
	public PickTokenCommand(IPlayer player, IField field) {
		this.player = player;
		this.field = field;
	}
	
	@Override
	public boolean valid() {
		
		// player isn'token allowed to pick a token at the moment
		if (!player.isStatus(Status.PickToken)) {
			return false;
		}
		
		// field is empty
		if (!field.hasToken()) {
			return false;
		}
		
		// player can'token token it's own token
		if (field.getToken().color().equals(player.color())) {
			return false;
		}
		
		return true;
	}

	@Override
	public void execute() { 
		token = field.getToken();
		field.setToken(null);
	}

	@Override
	public boolean undo() {
		field.setToken(token);
		return false;
	}
}