package de.htwg.sa.nmm.persistence.couchdb;

import org.ektorp.support.CouchDbDocument;

public class CouchFieldDocument extends CouchDbDocument {

    int grid;
    int index;
    CouchTokenDocument token;

    public void setGrid(int grid) {
        this.grid = grid;
    }

    public int getGrid() {
        return grid;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setToken(CouchTokenDocument token) {
        this.token = token;
    }

    public CouchTokenDocument getToken() {
        return token;
    }

}
