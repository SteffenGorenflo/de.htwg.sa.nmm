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
 * @since 2016-03-29
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
	 * initialize db4o
	 */
	@Override
	public boolean init() {
		return true;
	}

	/**
	 * Store Gamefield in db4o Database
	 * 
	 * @see de.htwg.sa.nmm.persistence.IDAO#storeGamefield(IGamefield)
	 */
	@Override
	public OperationResult storeGamefield(IGamefield gamefield) {

		LOG.info("Saving Game [" + gamefield.getName() + "]...");

		if (null != loadGamefiledByName(gamefield.getName())) {
			deleteGamefieldByName(gamefield.getName());
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
		LOG.info("Game [" + gamefield.getName() + "] successfully saved.");
		return new OperationResult(true);
	}

	/**
	 * Load Gamefield by Id from db4o Database
	 * 
	 * @see de.htwg.sa.nmm.persistence.IDAO#loadGamefiledByName(String)
	 */
	@Override
	public IGamefield loadGamefiledByName(final String name) {
		LOG.info("Load Game [" + name + "]...");
		ObjectSet<IGamefield> game = null;
		try {
			db = Db4o.openFile(FILENAME);
			game = db.query(new Predicate<IGamefield>() {

				private static final long serialVersionUID = 1L;

				@Override
				public boolean match(IGamefield gamefield) {
					return gamefield.getName().equals(name);
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
			LOG.info("Game [" + name + "] successfully loaded.");
			return res;
		}
		LOG.info("Game [" + name + "] not found.");
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
	 * @see de.htwg.sa.nmm.persistence.IDAO#getAllGamefieldNames()
	 */
	@Override
	public List<String> getAllGamefieldNames() {
		LOG.info("Retrieve all Games..");
		// Get all stored Games
		List<String> gameNames = new ArrayList<String>();
		try {
			db = Db4o.openFile(FILENAME);
			ObjectSet<IGamefield> games = db.query(IGamefield.class);
			// Extract all GameIds
			for (IGamefield game : games) {
				gameNames.add(game.getName());
			}
			db.close();
		} catch (Exception e) {
			LOG.warn(e.getMessage());
		}
		LOG.info(gameNames.size() + " Game(s) found.");
		return gameNames;
	}

	/**
	 * Delete Gamefield by Id from db4o Database
	 * 
	 * @see de.htwg.sa.nmm.persistence.IDAO#deleteGamefieldByName(String)
	 */
	@Override
	public OperationResult deleteGamefieldByName(String name) {
		LOG.info("Delete Game [" + name + "]...");
		try {
			db = Db4o.openFile(FILENAME);
			db.delete(loadGamefiledByName(name));
			db.close();
		} catch (DatabaseFileLockedException e) {
			LOG.warn("Database locked!");
			return new OperationResult(false, "Loeschen fehlgeschlagen! Bitte erneut versuchen.");
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return new OperationResult(false, "Ups! Da ist was falsch gelaufen.");
		}
		LOG.info("Game [" + name + "] successfully deleted.");
		return new OperationResult(true);
	}

}
