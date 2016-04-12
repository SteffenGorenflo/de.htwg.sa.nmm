package de.htwg.sa.nmm.model;

import static org.junit.Assert.*;

import org.junit.Test;

import de.htwg.sa.nmm.model.impl.Token;

public class TestToken {

	
	Token token = new Token(IToken.Color.BLACK);
	
	@Test
	public void testToken() {
		token = new Token(IToken.Color.WHITE);
		assertTrue(token.color().equals(IToken.Color.WHITE));
	}

	@Test
	public void testColor() {		
		assertTrue(token.color().equals(IToken.Color.BLACK));
	}

	@Test
	public void testEqualsObject() {
		
		// same color means 'equals == true'
		Token other = new Token(IToken.Color.BLACK);		
		assertTrue(token.equals(other));
		
		// different color menas 'equals == false'
		other = new Token(IToken.Color.WHITE);
		assertFalse(token.equals(other));
		
		// token can only be compared with token
		assertFalse(token.equals(new Object()));		
	}

	@Test
	public void testHashCode() {
		Token t1 = new Token(IToken.Color.BLACK);
		Token t2 = new Token(IToken.Color.BLACK);
		assertTrue(t1.hashCode() == t2.hashCode());
	}
}
