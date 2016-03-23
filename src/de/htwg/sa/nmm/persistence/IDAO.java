package de.htwg.sa.nmm.persistence;

import java.util.List;

import de.htwg.se.nmm.model.IGamefield;

public interface IDAO {

	OperationResult storeGamefield(IGamefield gamefield);
	
	IGamefield loadGamefiledbyId(String id);
	
	List<String> getAllGamefieldIds();
	
	OperationResult deleteGamefieldbyId(String id);
}
