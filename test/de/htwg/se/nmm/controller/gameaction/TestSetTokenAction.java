package de.htwg.se.nmm.controller.gameaction;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import de.htwg.se.nmm.controller.commands.SetTokenCommand;
import de.htwg.se.nmm.model.impl.Field;
import de.htwg.se.nmm.model.impl.Player;
import de.htwg.se.nmm.model.impl.Token;
import de.htwg.se.nmm.model.IPlayer.Status;

public class TestSetTokenAction {

	SetTokenCommand action;
	
	@Test
	public void testSetTokenAction() {
		Player player = new Player("player", Color.BLACK);
		Field field = new Field(0, 0);
		action = new SetTokenCommand(player, field);
	}

	@Test
	public void testValid() {
		
		Player player = new Player("player", Color.BLACK);
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
		field.setToken(new Token(Color.WHITE));
		assertFalse(action.valid());
				
		// all ok
		field.setToken(null);
		assertTrue(action.valid());
	}

	@Test
	public void testExecute() {
		Player player = new Player("player", Color.BLACK);
		Field field = new Field(0, 0);
		action = new SetTokenCommand(player, field);
		
		assert(action.valid());
		action.execute();
		assertTrue(field.hasToken());
	}

}
