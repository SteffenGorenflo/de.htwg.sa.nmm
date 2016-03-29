package de.htwg.sa.nmm.persistence;

import java.util.List;

import de.htwg.sa.nmm.model.IGamefield;

public interface IDAO {

	OperationResult storeGamefield(IGamefield gamefield);
	
	IGamefield loadGamefiledById(String id);
	
	List<String> getAllGamefieldIds();
	
	OperationResult deleteGamefieldById(String id);
}
