package de.htwg.sa.nmm.persistence.couchdb;

import de.htwg.sa.nmm.model.IPlayer;
import de.htwg.sa.nmm.model.IPlayer.Status;
import de.htwg.sa.nmm.model.IToken.Color;
import de.htwg.sa.nmm.model.impl.Player;
import org.ektorp.support.CouchDbDocument;

class CouchPlayerDocument extends CouchDbDocument {

    private CouchPlayerDocument() { }

    public String name;
    public int token;
    public Color color;
    public Status status;

    public static CouchPlayerDocument toDocument(IPlayer player) {
        CouchPlayerDocument doc = new CouchPlayerDocument();
        doc.name = player.name();
        doc.token = player.token();
        doc.color = player.color();
        doc.status = player.getStatus();
        return doc;
    }

    public IPlayer toPlayer() {
        IPlayer player = new Player(name, color);
        player.setToken(token);
        player.setStatus(status);
        return player;
    }

}
