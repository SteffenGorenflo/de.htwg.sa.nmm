package de.htwg.sa.nmm.persistence.db4o;

import java.util.ArrayList;
import java.util.List;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;

import de.htwg.sa.nmm.model.IGamefield;
import de.htwg.sa.nmm.persistence.IDAO;
import de.htwg.sa.nmm.persistence.OperationResult;

public class db4oDao implements IDAO {

	private static final String FILENAME = "nmm_db4o.db";

	private ObjectContainer db = null;

	public db4oDao() {
		db = Db4o.openFile(FILENAME);
	}

	@Override
	public OperationResult storeGamefield(IGamefield gamefield) {
		if (null != loadGamefiledById(gamefield.getId())) {
			deleteGamefieldById(gamefield.getId());
		}
		try {
			db.store(gamefield);
		} catch (Exception e) {
			return new OperationResult(false, e.getMessage());
		}
		return new OperationResult(true);
	}

	@Override
	public IGamefield loadGamefiledById(final String id) {
		ObjectSet<IGamefield> game = db.query(new Predicate<IGamefield>() {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean match(IGamefield game) {
				return game.getId().equals(id);
			}
		});
		
		if (!game.isEmpty())
			return game.get(0);
		return null;
	}

	@Override
	public List<String> getAllGamefieldIds() {
		// Get all stored Games
		ObjectSet<IGamefield> games = db.query(IGamefield.class);

		// Extract all GameIds
		List<String> gameIds = new ArrayList<String>();
		for (IGamefield game : games) {
			gameIds.add(game.getId());
		}
		return gameIds;
	}

	@Override
	public OperationResult deleteGamefieldById(String id) {
		try {
			db.delete(loadGamefiledById(id));
		} catch (Exception e){
			return new OperationResult(false, e.getMessage());
		}
		return new OperationResult(true);
	}

}
