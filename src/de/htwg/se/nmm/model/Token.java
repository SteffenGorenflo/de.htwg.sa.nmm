package de.htwg.se.nmm.model;

import java.awt.Color;


/**
 * Men represents the game pieces.
 */
public final class Token {
	
	private final Color color; 
	
	public Token(final Color color) {		
		this.color = color;
	}
		
	public Color color() {
		return color;
	}		
	
	@Override	
	public boolean equals(Object obj) {
		if (!(obj instanceof Token)) {
			return false;
		}
		Token other = (Token)obj;
		return color.equals(other.color);
	}		
}
