package de.htwg.sa.nmm.controller.commands;

public interface IGameCommand {
	
	boolean valid();
	
	void execute();
	
	boolean undo();
	
}
