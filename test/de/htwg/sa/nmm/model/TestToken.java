package de.htwg.sa.nmm.model;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import de.htwg.sa.nmm.model.impl.Token;

public class TestToken {

	
	Token token = new Token(Color.BLACK);
	
	@Test
	public void testToken() {
		token = new Token(Color.WHITE);
		assertTrue(token.color().equals(Color.WHITE));
	}

	@Test
	public void testColor() {		
		assertTrue(token.color().equals(Color.BLACK));
	}

	@Test
	public void testEqualsObject() {
		
		// same color means 'equals == true'
		Token other = new Token(Color.BLACK);		
		assertTrue(token.equals(other));
		
		// different color menas 'equals == false'
		other = new Token(Color.WHITE);
		assertFalse(token.equals(other));
		
		// token can only be compared with token
		assertFalse(token.equals(new Object()));		
	}

	@Test
	public void testHashCode() {
		Token t1 = new Token(Color.BLACK);
		Token t2 = new Token(Color.BLACK);
		assertTrue(t1.hashCode() == t2.hashCode());
	}
}
