package de.htwg.se.nmm.model;

import java.awt.Color;

public final class Player {
		
	private final int nrStartToken = 9;
	private final String name;
	private final Color color;	
	private Status status;	
	private int token;
	 
	public enum Status {
		SetToken,
		MoveToken,
		PickToken,
		GameLost;
	}	
			
	public Player(String name, Color color) {		
		this.name = name;
		this.color = color;
		init();		
	}
			
	public void init() {
		status = Status.SetToken;
		token = nrStartToken;
	}
	
	public Status getStatus() {
		return this.status;
	}
	
	public boolean isStatus(Status status) {
		return this.status == status;
	}
	
	public void setStatus(Status status) {		
		this.status = status;		
	}
	
	public Token takeToken() {
		Token t = null;
		if (token > 0) {
			t = new Token(color);
			token--;
		}
		return t;		
	}
	
	public boolean hasToken() {
		return token > 0;
	}
	
	public int token() {
		return token;
	}				
	
	public String name() {
		return name;
	}
	
	public Color color() {
		return color;
	}
			
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Player)) {
			return false;
		}
		Player other = (Player)obj;
		return color.equals(other.color);
	}	
	
	@Override
	public int hashCode() {
		return color.hashCode();
	}
		
}
