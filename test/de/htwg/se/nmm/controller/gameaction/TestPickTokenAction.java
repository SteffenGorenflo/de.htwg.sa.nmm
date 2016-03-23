package de.htwg.se.nmm.controller.gameaction;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import de.htwg.se.nmm.controller.commands.PickTokenCommand;
import de.htwg.se.nmm.model.impl.Field;
import de.htwg.se.nmm.model.impl.Player;
import de.htwg.se.nmm.model.impl.Token;
import de.htwg.se.nmm.model.IPlayer.Status;
import de.htwg.se.nmm.model.IToken;

public class TestPickTokenAction {

	PickTokenCommand action;
	
	@Test
	public void testPickTokenAction() {
		Player player = new Player("player", Color.BLACK);
		Field field = new Field(0, 0);
		action = new PickTokenCommand(player, field);
	}

	@Test
	public void testValid() {
		
		Player player = new Player("Player", Color.BLACK);
		Field field = new Field(0, 0);
		action = new PickTokenCommand(player, field);
		
		/* player is in wrong status */
		assertFalse(action.valid());
		
		/* field is empty */
		player.setStatus(Status.PickToken);
		assertFalse(action.valid());
		
		/* can't take your own token */
		IToken t = player.takeToken();
		field.setToken(t);
		assertFalse(action.valid());
		
		/* take another token is okay */
		t = new Token(Color.WHITE);
		field.setToken(t);
		assertTrue(action.valid());
	}

	@Test
	public void testExecute() {
		Player player = new Player("player", Color.BLACK);
		Field field = new Field(0, 0);
		field.setToken(new Token(Color.WHITE));
		player.setStatus(Status.PickToken);
		action = new PickTokenCommand(player, field);
		assert(action.valid());
		action.execute();
		assertFalse(field.hasToken());
	}

}
