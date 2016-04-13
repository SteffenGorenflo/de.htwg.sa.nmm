package de.htwg.sa.nmm.persistence.couchdb;

import de.htwg.sa.nmm.model.IToken;
import de.htwg.sa.nmm.model.IToken.Color;
import de.htwg.sa.nmm.model.impl.Token;
import org.ektorp.support.CouchDbDocument;

class CouchTokenDocument extends CouchDbDocument {

    private CouchTokenDocument() { }

    public Color color;

    public static CouchTokenDocument toDocument(IToken token) {
        if (token == null) {
            return null;
        }
        CouchTokenDocument doc = new CouchTokenDocument();
        doc.color = token.color();
        return doc;
    }

    public IToken toToken() {
        IToken token = new Token(color);
        return token;
    }

}
