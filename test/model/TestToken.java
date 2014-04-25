package model;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

public class TestToken {

	Player p = new Player("Player", Color.BLUE);
	Token t;
	@Test
	public void testToken() {
		t = new Token(p);
	}

	@Test
	public void testColor() {
		t = new Token(p);
		assertTrue(t.color().equals(p.color()));
	}

	@Test
	public void testEqualsObject() {
		Token other = new Token(new Player("Other Player", Color.YELLOW));
		t = new Token(p);
		assertFalse(t.equals(other));
		assertFalse(t.equals(new Object()));
		Token same = new Token(p);
		assertTrue(t.equals(same));
	}

}
