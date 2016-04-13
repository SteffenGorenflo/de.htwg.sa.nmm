package de.htwg.sa.nmm.persistence.couchdb;

import de.htwg.sa.nmm.model.IGamefield;
import de.htwg.sa.nmm.model.IPlayer;
import de.htwg.sa.nmm.model.impl.Gamefield;
import de.htwg.sa.nmm.model.impl.Player;
import de.htwg.sa.nmm.persistence.IDAO;
import de.htwg.sa.nmm.persistence.OperationResult;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;

import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;

public class CouchDao implements IDAO {

    private CouchDbConnector db;

    @Override
    public boolean init() {
        HttpClient client = null;
        try {
            client = new StdHttpClient.Builder().url("http://lenny2.in.htwg-konstanz.de:5984").build();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }
        CouchDbInstance dbInstance = new StdCouchDbInstance(client);
        db = dbInstance.createConnector("nmm", true);
        db.createDatabaseIfNotExists();
        return true;
    }

    @Override
    public OperationResult storeGamefield(IGamefield gamefield) {
        if (containsGamefield(gamefield.getName())) {
            db.update(objectToDocument(gamefield));
        } else {
            db.create(objectToDocument(gamefield));
        }
        return new OperationResult(true);
    }

    @Override
    public IGamefield loadGamefiledByName(String name) {
        CouchGamefieldDocument gamefield = db.find(CouchGamefieldDocument.class, name);
        if (gamefield == null) {
            return null;
        }
        return documentToObject(gamefield);
    }

    @Override
    public List<String> getAllGamefieldNames() {
        List<String> gamefieldNames = new LinkedList<>();
        return gamefieldNames;
    }

    @Override
    public OperationResult deleteGamefieldByName(String name) {
        if (!containsGamefield(name)) {
            return new OperationResult(false, "no such game: " + name);
        }
        db.delete(objectToDocument(loadGamefiledByName(name)));
        return new OperationResult(true);
    }

    private CouchPlayerDocument objectToDocument(IPlayer iplayer) {
        CouchPlayerDocument document = new CouchPlayerDocument();
        document.setColor(iplayer.color());
        document.setName(iplayer.name());
        document.setToken(iplayer.token());
        return document;
    }

    private CouchGamefieldDocument objectToDocument(IGamefield igamefield) {
        CouchGamefieldDocument document = new CouchGamefieldDocument();
        document.setPlayer1(objectToDocument(igamefield.getCurrentPlayer()));
        document.setPlayer2(objectToDocument(igamefield.getOtherPlayer()));
        document.setCurrentPlayer(objectToDocument(igamefield.getCurrentPlayer()));
        return document;
    }

    private IPlayer documentToObject(CouchPlayerDocument couchPlayerDocument) {
        IPlayer player = new Player(couchPlayerDocument.getName(), couchPlayerDocument.getColor());
        player.setStatus(couchPlayerDocument.getStatus());
        return player;
    }

    private IGamefield documentToObject(CouchGamefieldDocument couchGamefieldDocument) {
        IPlayer player1 = documentToObject(couchGamefieldDocument.getPlayer1());
        IPlayer player2 = documentToObject(couchGamefieldDocument.getPlayer2());
        IGamefield gamefield = new Gamefield(player1, player2);
        return gamefield;
    }

    private boolean containsGamefield(String name) {
        return getAllGamefieldNames().contains(name);
    }

}
