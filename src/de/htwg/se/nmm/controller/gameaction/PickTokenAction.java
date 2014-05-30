package de.htwg.se.nmm.controller.gameaction;

import de.htwg.se.nmm.model.IField;
import de.htwg.se.nmm.model.IPlayer;
import de.htwg.se.nmm.model.IPlayer.Status;;

public class PickTokenAction implements IAction {

	private IPlayer player;
	private IField field;
	
	public PickTokenAction(IPlayer player, IField field) {
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
		field.setToken(null);
	}
}
