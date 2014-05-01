package de.htwg.se.nmm.controller.gameaction;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import de.htwg.se.nmm.controller.gameaction.MoveTokenAction;
import de.htwg.se.nmm.model.Field;
import de.htwg.se.nmm.model.Player;
import de.htwg.se.nmm.model.Token;
import de.htwg.se.nmm.model.Player.Status;

public class TestMoveTokenAction {

	MoveTokenAction action;
		
	@Test
	public void testMoveTokenAction() {
		Player p = new Player("player", Color.BLACK);
		Field source = new Field(0,0);
		Field dest = new Field(0,1);
		action = new MoveTokenAction(p, source, dest);
	}

	@Test
	public void testValid() {
		Player p = new Player("player", Color.BLACK);
		Field source= new Field(0, 0);
		Field dest = new Field(0, 1);
		action = new MoveTokenAction(p, source, dest);
		
		// wrong player status
		p.setStatus(Status.PickToken);
		assertFalse(action.valid());
		p.setStatus(Status.MoveToken);
		
		// there is no token to move		
		assertFalse(action.valid());
		source.setToken(new Token(Color.BLACK));
		
		// non empty destination field
		dest.setToken(new Token(Color.BLACK));
		assertFalse(action.valid());
		dest.setToken(new Token(Color.WHITE));
		assertFalse(action.valid());
		
		// it's the token of another player
		dest.setToken(null);
		source.setToken(new Token(Color.WHITE));
		assertFalse(action.valid());
		
		// move is regular
		source.setToken(new Token(Color.BLACK));
		assertTrue(action.valid());			
	}

	@Test
	public void testExecute() {
		Player p = new Player("player", Color.BLACK);
		p.setStatus(Status.MoveToken);
		Field source= new Field(0, 0);
		source.setToken(new Token(Color.BLACK));
		Field dest = new Field(0, 1);
		action = new MoveTokenAction(p, source, dest);
		
		assertTrue(action.valid());
		action.execute();
		assertFalse(source.hasToken());
		assertTrue(dest.hasToken());
		assertTrue(dest.getToken().color().equals(Color.BLACK));
	}

}
