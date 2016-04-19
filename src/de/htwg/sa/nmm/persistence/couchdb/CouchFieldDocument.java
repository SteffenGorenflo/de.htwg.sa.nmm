package de.htwg.sa.nmm.persistence.couchdb;

import de.htwg.sa.nmm.model.IField;
import de.htwg.sa.nmm.model.impl.Field;
import org.ektorp.support.CouchDbDocument;

class CouchFieldDocument extends CouchDbDocument {

    private CouchFieldDocument() { }

    public int grid;
    public int index;
    public CouchTokenDocument token;

    public static CouchFieldDocument toDocument(IField field) {
        if (field == null) {
            return null;
        }
        CouchFieldDocument document = new CouchFieldDocument();
        document.grid = field.grid();
        document.index = field.index();
        document.token = CouchTokenDocument.toDocument(field.getToken());
        return document;
    }

    public IField toField() {
        IField field = new Field(grid, index);
        if (token != null) {
            field.setToken(token.toToken());
        }
        return field;
    }
}
