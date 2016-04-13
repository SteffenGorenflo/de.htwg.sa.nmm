package de.htwg.sa.nmm.model.impl;

import de.htwg.sa.nmm.model.IToken;

public final class Token implements IToken {
	
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
	
	@Override
	public int hashCode() {
		return color.hashCode();
	}	
	
}
