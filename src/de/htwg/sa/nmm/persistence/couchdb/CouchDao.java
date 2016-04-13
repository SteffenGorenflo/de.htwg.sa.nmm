package de.htwg.sa.nmm.persistence.couchdb;

import de.htwg.sa.nmm.model.IGamefield;
import de.htwg.sa.nmm.model.impl.Gamefield;
import de.htwg.sa.nmm.persistence.IDAO;
import de.htwg.sa.nmm.persistence.OperationResult;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;

import java.net.MalformedURLException;
import java.util.List;

public class CouchDao implements IDAO {

    private CouchDbConnector db;

    public CouchDao() {
        HttpClient client = null;
        try {
            client = new StdHttpClient.Builder().url(
                    "http://lenny2.in.htwg-konstanz.de:5984").build();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        CouchDbInstance dbInstance = new StdCouchDbInstance(client);
        db = dbInstance.createConnector("nmm", true);
        db.createDatabaseIfNotExists();
    }

    @Override
    public OperationResult storeGamefield(IGamefield gamefield) {
        return null;
    }

    @Override
    public IGamefield loadGamefiledByName(String name) {
        return null;
    }

    @Override
    public List<String> getAllGamefieldNames() {
        return null;
    }

    @Override
    public OperationResult deleteGamefieldByName(String name) {
        return null;
    }


    public IGamefield loadGamefiledById(String id) {
        CouchGamefieldDocument gamefield = db.find(CouchGamefieldDocument.class, id);
        if (gamefield == null) {
            return null;
        }
        return copy(gamefield);
    }

    private IGamefield copy(CouchGamefieldDocument couchGamefieldDocument) {

        //IGamefield gamefield = new Gamefield();
        return null;
    }

}
