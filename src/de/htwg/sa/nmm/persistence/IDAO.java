package de.htwg.sa.nmm.persistence;

import java.util.List;

import de.htwg.sa.nmm.model.IGamefield;

public interface IDAO {

	OperationResult storeGamefield(IGamefield gamefield);
	
	IGamefield loadGamefiledByName(String name);
	
	List<String> getAllGamefieldNames();
	
	OperationResult deleteGamefieldByName(String name);
}
