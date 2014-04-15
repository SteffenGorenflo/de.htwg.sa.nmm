package model;

import java.awt.Color;

public final class Player {
	
	
	private final String name;
	private final Color color;	
	private String status;
	int token;
	
	// TODO: StateDesignPattern or DecoratorDesignPattern instead of String-Status or enum!?
	public final static String STATUS_SET = "set";
	public final static String STATUS_MOVE = "move";
	public final static String STATUS_PICK = "pick";	
	public final static String STATUS_GAME_OVER = "game over";
		
			
	public Player(String name, Color color) {
		this.name = name;
		this.color = color;
		init();		
	}
	
	public void init() {
		status = STATUS_SET;
		token = 9;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(final String status) {		
		this.status = status;		
	}
	
	public boolean hasToken() {
		return token > 0;
	}
	
	public int token() {
		return token;
	}
			
	public Token take() {
		Token t = null;
		if (token > 0) {
			t = new Token(this);
			token--;
		}
		return t;
	}
	
	public String name() {
		return name;
	}
	
	public Color color() {
		return color;
	}
	
	public boolean myToken(Token token) {
		assert token != null;		
		return color.equals(token.color());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Player))
			return false;
		Player other = (Player)obj;
		return name.equals(other.name) && color.equals(other.color);
	}
}
