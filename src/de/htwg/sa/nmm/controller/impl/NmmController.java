package de.htwg.sa.nmm.controller.impl;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import de.htwg.sa.nmm.controller.INmmController;
import de.htwg.sa.nmm.controller.commands.IGameCommand;
import de.htwg.sa.nmm.controller.commands.MoveTokenCommand;
import de.htwg.sa.nmm.controller.commands.PickTokenCommand;
import de.htwg.sa.nmm.controller.commands.SetTokenCommand;
import de.htwg.sa.nmm.model.IField;
import de.htwg.sa.nmm.model.IGamefield;
import de.htwg.sa.nmm.model.IPlayer;
import de.htwg.sa.nmm.model.IToken;
import de.htwg.sa.nmm.model.IPlayer.Status;
import de.htwg.sa.nmm.persistence.IDAO;
import de.htwg.sa.nmm.persistence.OperationResult;
import de.htwg.sa.nmm.persistence.PersistenceStrategy;
import de.htwg.sa.nmm.persistence.couchdb.CouchDao;
import de.htwg.sa.nmm.persistence.db4o.db4oDao;
import de.htwg.sa.nmm.persistence.hibernate.util.HibernateDAO;
import de.htwg.sa.nmm.util.observer.Observable;

public final class NmmController extends Observable implements INmmController {
	
	private String status = "Nine Men's Morris";
	private IGamefield gamefield;
	private Stack<IGameCommand> undoStack = new Stack<>();
	private Map<PersistenceStrategy, IDAO> daos = new HashMap<>();
	
	public NmmController(IGamefield gamefield) {
		this.gamefield = gamefield;
		IDAO db4o = new db4oDao();
		if (db4o.init()) {
			daos.put(PersistenceStrategy.db4o, db4o);
		}
		IDAO couchdb = new CouchDao();
		if (couchdb.init()) {
			daos.put(PersistenceStrategy.couchdb, couchdb);
		}
		IDAO hibernate = new HibernateDAO();
		if (hibernate.init()) {
			daos.put(PersistenceStrategy.hibernate, hibernate);
		}
	}
			
	public void restart() {
		gamefield.init();
		status = "Nine Men's Morris";
		undoStack.clear();
		notifyObservers();
	}	
	
	public boolean pickToken(int grid, int index) {
		boolean ok = false;
		IField field = gamefield.field(grid, index);
		IGameCommand action = new PickTokenCommand(gamefield.getCurrentPlayer(), field);
		if (action.valid()) {
			action.execute();
			ok = true;
			undoStack.add(action);
			status = "picked token from " + field;
			if (hasWon(otherPlayer())){
				gamefield.nextPlayer();
				gamefield.setStatus(Status.GameLost);
				status += "\nplayer " + gamefield.getCurrentPlayer().name() + " lost game!";
			} else {
                nextPlayer();
            }
		} else {			
			status = "couldn'token pick token from " + field;
		}
		notifyObservers();
		return ok;
	}

	public boolean setToken(int grid, int index) {
		boolean ok = false;
		IField field = gamefield.field(grid, index);
		IGameCommand action = new SetTokenCommand(gamefield.getCurrentPlayer(), field);
		if (action.valid()) {
			action.execute();	
			ok = true;
			undoStack.add(action);
			if (gamefield.mill(field)) { 
				status = "mill!";
				gamefield.setStatus(Status.PickToken);
			} else {
				status = "set token to " + field;
				nextPlayer();
			}			
		} else {				
			status = "couldn'token set token to " + field;
		}		
		notifyObservers();	
		return ok;
	}

	public boolean moveToken(int sourceGrid, int sourceIndex, int destGrid, int destIndex) {
		boolean ok = false;
		IField source = gamefield.field(sourceGrid, sourceIndex);
		IField dest   = gamefield.field(destGrid, destIndex);
		IGameCommand action = new MoveTokenCommand(gamefield.getCurrentPlayer(), source, dest);
		if (action.valid()) {
			action.execute();
			ok = true;
			undoStack.add(action);
			if (gamefield.mill(dest)) {				
				status = "mill!";
				gamefield.setStatus(Status.PickToken);
			} else {
				status = "moved token to " + dest;
				nextPlayer();
			}
		} else {			
			status = "couldn'token move token to " + dest;			
		}		
		notifyObservers();
		return ok;
	}
	
	public IPlayer currentPlayer() {
		return gamefield.getCurrentPlayer();
	}
	
	public String color(int grid, int index) {
		String dump = dumpColor(grid, index);
		if (dump.equals("")) {
			return "+";
		} else if (dump.equals("BLACK")) {
			final String blackToken = "\u2B24";
			return blackToken;
		}
		final String whiteToken = "\u25CB";
		return whiteToken;	
	}
	
	public String dumpColor(int grid, int index) {
		IToken t = gamefield.field(grid, index).getToken();
		if (t == null) {
			return "";
		} else if (t.color().equals(Color.WHITE)) {
			return "WHITE";
		}
		return "BLACK";
	}
	
	public IPlayer otherPlayer() {
		return gamefield.getOtherPlayer();
	}
	
	private void nextPlayer() {
		gamefield.nextPlayer();
		if (gamefield.getCurrentPlayer().hasToken()) {
			gamefield.setStatus(Status.SetToken);
		} else {
			gamefield.setStatus(Status.MoveToken);
		}
	}

	public String getStatus() {		
		return status;
	}

	public boolean hasToken(int grid, int index) {
		return gamefield.field(grid, index).hasToken();		
	}
	
	public boolean valid(int grid, int index) {
		return gamefield.valid(grid, index);
	}			
	
	public int grids() {
		return gamefield.grids();
	}
	
	public int index() {
		return gamefield.index();
	}

	@Override
	public boolean undo() {
		if (!undoStack.empty()) {
			if (undoStack.pop().undo()) {
				nextPlayer(); 
			}
			notifyObservers();
			return true;
		} 
		return false;
	}

    @Override
	public boolean hasWon(IPlayer player) {
		boolean twoTokensOnField = gamefield.countToken(player) == 2;
		boolean noTokensLeft = !player.hasToken();
		return twoTokensOnField && noTokensLeft;
	}

	@Override
	public boolean storeGame(String id, PersistenceStrategy strategy) {
		gamefield.setName(id);
        IDAO dao = daos.get(strategy);
		OperationResult result = dao.storeGamefield(gamefield);
		if (result.successful) {
			status = "successfully saved game " + id;
		} else {
			status = "could not save game " + id + ": " + result.message;
		}
		notifyObservers();
		return result.successful;
	}

	@Override
	public boolean loadGame(String id, PersistenceStrategy strategy) {
		IDAO dao = daos.get(strategy);
		IGamefield newGame = dao.loadGamefieldByName(id);
		if (newGame == null) {
			return false;
		}
		status = "loaded game: " + id;
		gamefield = newGame;
		undoStack.clear();
		notifyObservers();
		return true;
	}

	@Override
	public List<String> getGameIds(PersistenceStrategy strategy) {
		IDAO dao = daos.get(strategy);
		return dao.getAllGamefieldNames();
	}

	@Override
	public boolean deleteGame(String id, PersistenceStrategy strategy) {
		IDAO dao = daos.get(strategy);
		OperationResult result = dao.deleteGamefieldByName(id);
		if (result.successful) {
			status = "deleted game " + id;
		} else {
			status = "could not delete game " + id + ": " + result.message;
		}
		notifyObservers();
		return result.successful;
	}
}
