package de.htwg.se.nmm.controller.commands;

public interface IGameCommand {
	
	boolean valid();
	
	void execute();
	
	boolean undo();
	
}
