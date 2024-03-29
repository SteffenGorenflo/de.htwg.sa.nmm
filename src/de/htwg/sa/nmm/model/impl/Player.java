package de.htwg.sa.nmm.model.impl;

import de.htwg.sa.nmm.model.IPlayer;
import de.htwg.sa.nmm.model.IToken;

public final class Player implements IPlayer {
		
	private static final int STARTTOKEN = 9;
	private final String name;
	private final IToken.Color color;	
	private Status status;	
	private int token;
	 	
	//@Inject		
	public Player(String name, IToken.Color color) {		
		this.name = name;
		this.color = color;
		init();		
	}
			
	public void init() {
		status = Status.SetToken;
		token = STARTTOKEN;
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
	
	public IToken takeToken() {
		IToken t = null;
		if (token > 0) {
			t = new Token(color);
			token--;
		}
		return t;		
	}

	public void setToken(int token) {
		this.token = token;
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
	
	public IToken.Color color() {
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

	@Override
	public void addToken() {		
		this.token++;				
	}
		
}
