package de.htwg.se.nmm.model;

import java.awt.Color;

import de.htwg.se.nmm.model.IToken;

public interface IPlayer {
	
	enum Status {
		SetToken,
		MoveToken,
		PickToken,
		GameLost;
	}
	
	Status getStatus();
	
	void init();
	
	boolean isStatus(Status status);
	
	void setStatus(Status status);
	
	IToken takeToken();
	
	boolean hasToken();
	
	int token();
	
	String name();
	
	Color color();
	
}
