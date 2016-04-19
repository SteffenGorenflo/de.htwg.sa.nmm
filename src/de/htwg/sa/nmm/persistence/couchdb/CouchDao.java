package de.htwg.sa.nmm.persistence.couchdb;

import de.htwg.sa.nmm.model.IGamefield;
import de.htwg.sa.nmm.persistence.IDAO;
import de.htwg.sa.nmm.persistence.OperationResult;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.ViewQuery;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;

import java.util.ArrayList;
import java.util.List;

public class CouchDao implements IDAO {

    private CouchDbConnector db;

    @Override
    public boolean init() {
        try {
            HttpClient client = new StdHttpClient.Builder().url("http://lenny2.in.htwg-konstanz.de:5984").build();
            CouchDbInstance dbInstance = new StdCouchDbInstance(client);
            db = dbInstance.createConnector("nmm", true);
            db.createDatabaseIfNotExists();
            return true;
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
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
    public IGamefield loadGamefieldByName(String name) {
        CouchGamefieldDocument gamefield = getGamefieldByName(name);
        if (gamefield == null) {
            return null;
        }
        return gamefield.toGamefield();
    }

    @Override
    public List<String> getAllGamefieldNames() {
        List<CouchGamefieldDocument> gamefields = getAllGamefields();
        List<String> gamefieldNames = new ArrayList<>(gamefields.size());
        for (CouchGamefieldDocument gamefield: gamefields) {
            gamefieldNames.add(gamefield.name);
        }
        return gamefieldNames;
    }

    @Override
    public OperationResult deleteGamefieldByName(String name) {
        CouchGamefieldDocument game = getGamefieldByName(name);
        if (game == null) {
            return new OperationResult(false, "no such game: " + name);
        }
        db.delete(game);
        return new OperationResult(true);
    }

    private List<CouchGamefieldDocument> getAllGamefields() {
        ViewQuery query = new ViewQuery().allDocs().includeDocs(true);
        return db.queryView(query, CouchGamefieldDocument.class);
    }

    private CouchGamefieldDocument getGamefieldByName(final String name) {
        List<CouchGamefieldDocument> documents = getAllGamefields();
        for (CouchGamefieldDocument doc: documents) {
            if (doc.name.equals(name)) {
                return doc;
            }
        }
        return null;
    }

    private boolean containsGamefield(String name) {
        return getAllGamefieldNames().contains(name);
    }
}
