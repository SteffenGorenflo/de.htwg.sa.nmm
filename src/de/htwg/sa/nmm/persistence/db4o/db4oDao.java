package de.htwg.sa.nmm.persistence.db4o;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.DatabaseFileLockedException;
import com.db4o.ext.Db4oIOException;
import com.db4o.query.Predicate;

import de.htwg.sa.nmm.model.IGamefield;
import de.htwg.sa.nmm.persistence.IDAO;
import de.htwg.sa.nmm.persistence.OperationResult;

/**
 * Implementation of DAO Pattern with DB4O - Database
 * 
 * @author Patrick Schmidt
 * @since 29.03.2016
 */
public class db4oDao implements IDAO {

	/**
	 * log4j Logger
	 */
	private static final Logger LOG = Logger.getLogger("logfile");

	/**
	 * db4o Database-File
	 */
	private static final String FILENAME = "nmm_db4o.db";

	/**
	 * ObjectContainer for Database-Access
	 */
	private ObjectContainer db = null;

	/**
	 * Default Constructor
	 **/
	public db4oDao() {
		LOG.info("Connected to db4o Database");
	}

	/**
	 * Store Gamefield in db4o Database
	 * 
	 * @see de.htwg.sa.nmm.persistence.IDAO#storeGamefield(IGamefield)
	 */
	@Override
	public OperationResult storeGamefield(IGamefield gamefield) {

		LOG.info("Saving Game [" + gamefield.getId() + "]...");

		if (null != loadGamefiledById(gamefield.getId())) {
			deleteGamefieldById(gamefield.getId());
		}
		try {
			db = Db4o.openFile(FILENAME);
			db.store(gamefield);
			db.close();
		} catch (DatabaseFileLockedException e) {
			LOG.warn("Database currently used!");
			return new OperationResult(false, "Speichern fehlgeschlagen! Bitte erneut versuchen.");
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return new OperationResult(false, "Ups! Da ist was falsch gelaufen.");
		}
		LOG.info("Game [" + gamefield.getId() + "] successfully saved.");
		return new OperationResult(true);
	}

	/**
	 * Load Gamefield by Id from db4o Database
	 * 
	 * @see de.htwg.sa.nmm.persistence.IDAO#loadGamefiledById(String)
	 */
	@Override
	public IGamefield loadGamefiledById(final String id) {
		LOG.info("Load Game [" + id + "]...");
		ObjectSet<IGamefield> game = null;
		try {
			db = Db4o.openFile(FILENAME);
			game = db.query(new Predicate<IGamefield>() {

				private static final long serialVersionUID = 1L;

				@Override
				public boolean match(IGamefield game) {
					return game.getId().equals(id);
				}
			});

		} catch (Exception e) {
			LOG.warn(e.getMessage());
		}

		if (null != game && !game.isEmpty()) {
			IGamefield res = game.get(0);
			try {
				db.close();
			} catch (Db4oIOException e) {
				LOG.error(e.getMessage());
			}
			LOG.info("Game [" + id + "] successfully loaded.");
			return res;
		}
		LOG.info("Game [" + id + "] not found.");
		try {
			db.close();
		} catch (Db4oIOException e) {
			LOG.error(e.getMessage());
		}
		return null;
	}

	/**
	 * Get all Gamefield Id's from db4o Database
	 * 
	 * @see de.htwg.sa.nmm.persistence.IDAO#getAllGamefieldIds()
	 */
	@Override
	public List<String> getAllGamefieldIds() {
		LOG.info("Retrieve all Games..");
		// Get all stored Games
		List<String> gameIds = new ArrayList<String>();
		try {
			db = Db4o.openFile(FILENAME);
			ObjectSet<IGamefield> games = db.query(IGamefield.class);
			// Extract all GameIds
			for (IGamefield game : games) {
				gameIds.add(game.getId());
			}
			db.close();
		} catch (Exception e) {
			LOG.warn(e.getMessage());
		}
		LOG.info(gameIds.size() + " Game(s) found.");
		return gameIds;
	}

	/**
	 * Delete Gamefield by Id from db4o Database
	 * 
	 * @see de.htwg.sa.nmm.persistence.IDAO#deleteGamefieldById(String)
	 */
	@Override
	public OperationResult deleteGamefieldById(String id) {
		LOG.info("Delete Game [" + id + "]...");
		try {
			db = Db4o.openFile(FILENAME);
			db.delete(loadGamefiledById(id));
			db.close();
		} catch (DatabaseFileLockedException e) {
			LOG.warn("Database locked!");
			return new OperationResult(false, "Loeschen fehlgeschlagen! Bitte erneut versuchen.");
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return new OperationResult(false, "Ups! Da ist was falsch gelaufen.");
		}
		LOG.info("Game [" + id + "] successfully deleted.");
		return new OperationResult(true);
	}

}
