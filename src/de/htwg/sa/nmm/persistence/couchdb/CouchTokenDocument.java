package de.htwg.sa.nmm.persistence.couchdb;

import de.htwg.sa.nmm.model.IToken;
import de.htwg.sa.nmm.model.IToken.Color;
import org.ektorp.support.CouchDbDocument;

public class CouchTokenDocument extends CouchDbDocument {

    Color color;

    public CouchTokenDocument(Color white) {

    }

    public CouchTokenDocument() {
        this(IToken.Color.WHITE);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

}
