package de.htwg.se.nmm.model;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import de.htwg.se.nmm.model.Player.Status;

public class TestPlayer {

	Player player;
	
	@Before
	public void initTest() {
		player = new Player("player's name", Color.BLACK);
	}
	
	@Test
	public void testPlayer() {
		player = new Player("name", Color.WHITE);
		assertTrue(player.name().equals("name"));
		assertTrue(player.color().equals(Color.WHITE));
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
		Token t = null;
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
		assertTrue(player.color().equals(Color.BLACK));
	}

	@Test
	public void testEqualsObject() {
		
		// same name and same color ==> equals true
		Player same = new Player("player's name", Color.BLACK);
		assertTrue(player.equals(same));
		
		// other name, other color
		Player other = new Player("opponent", Color.WHITE);		
		assertFalse(player.equals(other));
		
		// other name
		other = new Player("opponent", Color.BLACK);
		assertFalse(player.equals(other));
		
		// other color
		other = new Player("player's name", Color.WHITE);
		assertFalse(player.equals(other));
		
		// just allow to compare with other player
		assertFalse(player.equals(new Object()));
	}

}
