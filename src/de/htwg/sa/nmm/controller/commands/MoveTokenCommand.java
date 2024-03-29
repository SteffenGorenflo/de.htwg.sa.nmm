package de.htwg.sa.nmm.controller.commands;


import de.htwg.sa.nmm.model.IField;
import de.htwg.sa.nmm.model.IPlayer;
import de.htwg.sa.nmm.model.IToken;
import de.htwg.sa.nmm.model.IPlayer.Status;

public class MoveTokenCommand implements IGameCommand {

	private final IPlayer player;
	private final IField source, destination;
	 
	public MoveTokenCommand(IPlayer player, IField source, IField destination) {
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
		IToken t = source.getToken();
		source.setToken(null);
		destination.setToken(t);
	}

	@Override
	public boolean undo() {
		IToken t = destination.getToken();
		source.setToken(t);
		destination.setToken(null);
		return true;
	}
}
