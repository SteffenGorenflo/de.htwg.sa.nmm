package de.htwg.sa.nmm.persistence.couchdb;

import de.htwg.sa.nmm.model.IField;
import de.htwg.sa.nmm.model.IGamefield;
import de.htwg.sa.nmm.model.IPlayer;
import de.htwg.sa.nmm.model.IToken;
import de.htwg.sa.nmm.model.impl.Field;
import de.htwg.sa.nmm.model.impl.Gamefield;
import de.htwg.sa.nmm.model.impl.Player;
import de.htwg.sa.nmm.model.impl.Token;
import de.htwg.sa.nmm.persistence.IDAO;
import de.htwg.sa.nmm.persistence.OperationResult;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.ViewQuery;
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
            db.update(CouchGamefieldDocument.toDocument(gamefield));
        } else {
            db.create(CouchGamefieldDocument.toDocument(gamefield));
        }
        return new OperationResult(true);
    }

    @Override
    public IGamefield loadGamefiledByName(String name) {
        CouchGamefieldDocument gamefield = db.find(CouchGamefieldDocument.class, name);
        if (gamefield == null) {
            return null;
        }
        return gamefield.toGamefield();
    }

    @Override
    public List<String> getAllGamefieldNames() {

        ViewQuery query = new ViewQuery()
                                .allDocs()
                                .includeDocs(true);

        List<String> gamefieldNames = new LinkedList<>();
        for (CouchGamefieldDocument gamefield: db.queryView(query, CouchGamefieldDocument.class)) {
            gamefieldNames.add(   gamefield.getId());
        }
        return gamefieldNames;
    }

    @Override
    public OperationResult deleteGamefieldByName(String name) {
        if (!containsGamefield(name)) {
            return new OperationResult(false, "no such game: " + name);
        }
        db.delete(CouchGamefieldDocument.toDocument(loadGamefiledByName(name)));
        return new OperationResult(true);
    }

    private boolean containsGamefield(String name) {
        return getAllGamefieldNames().contains(name);
    }

}
