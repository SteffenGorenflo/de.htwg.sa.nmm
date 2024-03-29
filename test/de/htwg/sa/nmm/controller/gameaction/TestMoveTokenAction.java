package de.htwg.sa.nmm.controller.gameaction;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import de.htwg.sa.nmm.controller.commands.MoveTokenCommand;
import de.htwg.sa.nmm.model.IToken;
import de.htwg.sa.nmm.model.IPlayer.Status;
import de.htwg.sa.nmm.model.impl.Field;
import de.htwg.sa.nmm.model.impl.Player;
import de.htwg.sa.nmm.model.impl.Token;

public class TestMoveTokenAction {

	MoveTokenCommand action;

	@Test
	public void testMoveTokenAction() {
		Player p = new Player("player", IToken.Color.BLACK);
		Field source = new Field(0, 0);
		Field dest = new Field(0, 1);
		action = new MoveTokenCommand(p, source, dest);
	}

	@Test
	public void testValid() {
		Player p = new Player("player", IToken.Color.BLACK);
		Field source = new Field(0, 0);
		Field dest = new Field(0, 1);
		action = new MoveTokenCommand(p, source, dest);

		// wrong player status
		p.setStatus(Status.PickToken);
		assertFalse(action.valid());
		p.setStatus(Status.MoveToken);

		// there is no token to move
		assertFalse(action.valid());
		source.setToken(new Token(IToken.Color.BLACK));

		// non empty destination field
		dest.setToken(new Token(IToken.Color.BLACK));
		assertFalse(action.valid());
		dest.setToken(new Token(IToken.Color.WHITE));
		assertFalse(action.valid());

		// it's the token of another player
		dest.setToken(null);
		source.setToken(new Token(IToken.Color.WHITE));
		assertFalse(action.valid());

		// move is regular
		source.setToken(new Token(IToken.Color.BLACK));
		assertTrue(action.valid());
	}

	@Test
	public void testExecute() {
		Player p = new Player("player", IToken.Color.BLACK);
		p.setStatus(Status.MoveToken);
		Field source = new Field(0, 0);
		source.setToken(new Token(IToken.Color.BLACK));
		Field dest = new Field(0, 1);
		action = new MoveTokenCommand(p, source, dest);

		assertTrue(action.valid());
		action.execute();
		assertFalse(source.hasToken());
		assertTrue(dest.hasToken());
		assertTrue(dest.getToken().color().equals(Color.BLACK));
	}

}
