package de.htwg.sa.nmm.persistence.couchdb;

import org.ektorp.support.CouchDbDocument;

public class CouchGamefieldDocument extends CouchDbDocument {

    String name;
    CouchPlayerDocument player1;
    CouchPlayerDocument player2;
    CouchPlayerDocument currentPlayer;
    CouchFieldDocument[][] gamefield;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPlayer1(CouchPlayerDocument player1) {
        this.player1 = player1;
    }

    public CouchPlayerDocument getPlayer1() {
        return player1;
    }

    public void setPlayer2(CouchPlayerDocument player2) {
        this.player2 = player2;
    }

    public CouchPlayerDocument getPlayer2() {
        return player2;
    }

    public void setCurrentPlayer(CouchPlayerDocument currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public CouchPlayerDocument getCurrentPlayer() {
        return currentPlayer;
    }

    public void setGamefield(CouchFieldDocument[][] gamefield) {
        this.gamefield = gamefield;
    }

    public CouchFieldDocument[][] getGamefield() {
        return gamefield;
    }

}
