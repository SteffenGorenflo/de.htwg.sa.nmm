package de.htwg.sa.nmm.persistence.couchdb;

import de.htwg.sa.nmm.model.IPlayer.Status;
import de.htwg.sa.nmm.model.IToken.Color;
import org.ektorp.support.CouchDbDocument;

public class CouchPlayerDocument extends CouchDbDocument {

    String name;
    int token;
    Color color;
    Status status;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public int getToken() {
        return token;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }



}
