package de.htwg.sa.nmm.persistence;

import java.util.List;

import de.htwg.sa.nmm.model.IGamefield;

// nth: create abstract dao which implements some logic with template design pattern
public interface IDAO {

	boolean init();

	OperationResult storeGamefield(IGamefield gamefield);
	
	IGamefield loadGamefiledByName(String name);
	
	List<String> getAllGamefieldNames();
	
	OperationResult deleteGamefieldByName(String name);

}
