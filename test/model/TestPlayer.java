package model;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

public class TestPlayer {

	Player p;
	
	@Before
	public void init() {
		p = new Player("x", Color.BLACK);
	}
	
	@Test
	public void testPlayer() {
		p = new Player("x", Color.BLACK);
	}

	@Test
	public void testInit() {
		p.init();
		assertTrue(p.hasToken());		
	}

	@Test
	public void testGetStatus() {
		assertTrue(p.getStatus().equals(Player.STATUS_SET));	
	}

	@Test
	public void testIsStatus() {
		assertTrue(p.isStatus(Player.STATUS_SET));
		p.setStatus(Player.STATUS_MOVE);
		assertTrue(p.isStatus(Player.STATUS_MOVE));
	}

	@Test
	public void testSetStatus() {
		p.setStatus(Player.STATUS_MOVE);
		assert(p.getStatus().equals(Player.STATUS_MOVE));
	}

	@Test
	public void testHasToken() {
		assertTrue(p.hasToken());
		while (p.hasToken())
			p.take();
		assertFalse(p.hasToken());
	}

	@Test
	public void testToken() {
		assertTrue(p.token() == 9);
		p.take();
		assertTrue(p.token() == 8);
		while (p.hasToken())
			p.take();
		assertTrue(p.token() == 0);
	}

	@Test
	public void testTake() {
		Token t = p.take();
		assertTrue(t != null);
		while (p.hasToken())
			p.take();
		t = p.take();
		assertFalse(p.hasToken());
	}

	@Test
	public void testName() {
		assertTrue(p.name().equals("x"));
	}

	@Test
	public void testColor() {
		assertTrue(p.color().equals(Color.BLACK));
	}

	@Test
	public void testMyToken() {
		Token t = p.take();
		assertTrue(p.myToken(t));
		assertFalse(p.myToken(null));
		assertFalse(p.myToken(new Token(new Player("x", Color.YELLOW))));
	}

	@Test
	public void testEqualsObject() {
		Player p2 = new Player(p.name(), p.color());
		assertTrue(p2.equals(p));
		assertFalse(p2.equals(null));
		assertFalse(p2.equals(new Object()));
		p2 = new Player("other player name", Color.CYAN);
		assertFalse(p2.equals(p));
	}

}
