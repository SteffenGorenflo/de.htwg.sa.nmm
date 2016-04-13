package de.htwg.sa.nmm.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.htwg.sa.nmm.model.IPlayer.Status;
import de.htwg.sa.nmm.model.impl.Player;

public class TestPlayer {

	Player player;
	
	@Before
	public void initTest() {
		player = new Player("player's name", IToken.Color.BLACK);
	}
	
	@Test
	public void testPlayer() {
		player = new Player("name", IToken.Color.WHITE);
		assertTrue(player.name().equals("name"));
		assertTrue(player.color().equals(IToken.Color.WHITE));
		assertTrue(player.hasToken());
	}

	@Test
	public void testInit() {
		
		// take away all token
		while (player.hasToken()) {
			player.takeToken();
		}
		
		// call init
		player.init();
		
		// make sure that player has token
		assertTrue(player.hasToken());
		// and make sure that player is in 'start mode' ==> SetToken
		assertTrue(player.getStatus() == Status.SetToken);
	}

	@Test
	public void testGetStatus() {
		assertTrue(player.getStatus() == Status.SetToken);
		player.setStatus(Status.MoveToken);
		assertTrue(player.getStatus() == Status.MoveToken);
	}

	@Test
	public void testIsStatus() {
		assertTrue(player.isStatus(Status.SetToken));
		player.setStatus(Status.PickToken);
		assertTrue(player.isStatus(Status.PickToken));
		assertFalse(player.isStatus(Status.MoveToken));
	}

	@Test
	public void testSetStatus() {
		player.setStatus(Status.SetToken);
		
		// we have to call this to get 100% test coverage!
		Status.valueOf(Status.SetToken.toString());
	}

	@Test
	public void testTakeToken() {
		IToken t = null;
		while (player.hasToken()) {
			t = player.takeToken();
			assertTrue(t != null);
			assertTrue(t.color().equals(player.color()));
		}
		
		t = player.takeToken();
		assertTrue(t == null);
	}

	@Test
	public void testHasToken() {
		assertTrue(player.hasToken());
		while (player.hasToken()) {
			assertTrue(player.hasToken());
			player.takeToken();
		}
		assertFalse(player.hasToken());		
	}

	@Test
	public void testToken() {
		assertTrue(player.token() == 9);		
		player.takeToken();
		assertTrue(player.token() == 8);
		player.takeToken();
		assertTrue(player.token() == 7);
		player.takeToken();
		
		while (player.hasToken()) {
			player.takeToken();
		}
		assertTrue(player.token() == 0);		
	}

	@Test
	public void testName() {
		assertTrue(player.name().equals("player's name"));
	}

	@Test
	public void testColor() {
		assertTrue(player.color().equals(IToken.Color.BLACK));
	}

	@Test
	public void testEqualsObject() {
		
		// same color
		Player same = new Player("player's name", IToken.Color.BLACK);
		assertTrue(player.equals(same));	
		
		// other color
		Player other = new Player("player's name", IToken.Color.WHITE);
		assertFalse(player.equals(other));
		
		// just allow to compare with other player
		assertFalse(player.equals(new Object()));
	}
	
	@Test
	public void testHashCode() {
		Player p1 = new Player("max", IToken.Color.BLACK);
		Player p2 = new Player("moritz", IToken.Color.BLACK);
		assertTrue(p1.hashCode() == p2.hashCode());
	}
}
