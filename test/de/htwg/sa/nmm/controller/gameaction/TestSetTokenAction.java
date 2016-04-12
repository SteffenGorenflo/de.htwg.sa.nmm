package de.htwg.sa.nmm.controller.gameaction;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.htwg.sa.nmm.controller.commands.SetTokenCommand;
import de.htwg.sa.nmm.model.IPlayer.Status;
import de.htwg.sa.nmm.model.IToken;
import de.htwg.sa.nmm.model.impl.Field;
import de.htwg.sa.nmm.model.impl.Player;
import de.htwg.sa.nmm.model.impl.Token;

public class TestSetTokenAction {

	SetTokenCommand action;
	
	@Test
	public void testSetTokenAction() {
		Player player = new Player("player", IToken.Color.BLACK);
		Field field = new Field(0, 0);
		action = new SetTokenCommand(player, field);
	}

	@Test
	public void testValid() {
		
		Player player = new Player("player", IToken.Color.BLACK);
		Field field = new Field(0, 0);
		player.setStatus(Status.MoveToken);
		action = new SetTokenCommand(player, field);
		
		// player is not allowed to set a token
		assertFalse(action.valid());

		// player has no tokens anymore
		player.setStatus(Status.SetToken);
		while (player.hasToken()) {
			player.takeToken();
		}			
		assertFalse(action.valid());
			
		// there is already another token
		player.init();
		field.setToken(new Token(IToken.Color.WHITE));
		assertFalse(action.valid());
				
		// all ok
		field.setToken(null);
		assertTrue(action.valid());
	}

	@Test
	public void testExecute() {
		Player player = new Player("player", IToken.Color.BLACK);
		Field field = new Field(0, 0);
		action = new SetTokenCommand(player, field);
		
		assert(action.valid());
		action.execute();
		assertTrue(field.hasToken());
	}

}
