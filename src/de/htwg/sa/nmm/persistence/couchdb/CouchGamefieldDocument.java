package de.htwg.sa.nmm.persistence.couchdb;

import de.htwg.sa.nmm.model.IField;
import de.htwg.sa.nmm.model.IGamefield;
import de.htwg.sa.nmm.model.impl.Gamefield;
import org.ektorp.support.CouchDbDocument;

class CouchGamefieldDocument extends CouchDbDocument {

    private CouchGamefieldDocument() {}

    public CouchPlayerDocument player1;
    public CouchPlayerDocument player2;
    public CouchPlayerDocument currentPlayer;
    public CouchFieldDocument[][] gamefield;
    public CouchFieldDocument[] grid0;
    public CouchFieldDocument[] grid1;
    public CouchFieldDocument[] grid2;
    public int grids;
    public int indexes;

    public static CouchGamefieldDocument toDocument(IGamefield gamefield) {
        if (gamefield == null) {
            return null;
        }
        CouchGamefieldDocument doc = new CouchGamefieldDocument();
        doc.setId(gamefield.getName());
        doc.grids = gamefield.grids();
        doc.indexes = gamefield.index();
        doc.currentPlayer = CouchPlayerDocument.toDocument(gamefield.getCurrentPlayer());
        doc.player1 = CouchPlayerDocument.toDocument(gamefield.getCurrentPlayer());
        doc.player2 = CouchPlayerDocument.toDocument(gamefield.getOtherPlayer());

        doc.grid0 = gridToArray(gamefield, 0);
        doc.grid1 = gridToArray(gamefield, 1);
        doc.grid2 = gridToArray(gamefield, 2);

        return doc;
    }

    private static CouchFieldDocument[] gridToArray(IGamefield gamefield, int grid) {
        CouchFieldDocument[] gridArray = new CouchFieldDocument[gamefield.index()];
        for (int i = 0; i < gridArray.length; i++) {
            gridArray[i] = CouchFieldDocument.toDocument(gamefield.field(grid, i));
        }
        return gridArray;
    }

    public IGamefield toGamefield() {

        IField[][] fields = new IField[grids][indexes];

        for (int i = 0; i < indexes; i++) {
            fields[0][i] = grid0[i].toField();
        }

        for (int i = 0; i < indexes; i++) {
            fields[1][i] = grid1[i].toField();
        }

        for (int i = 0; i < indexes; i++) {
            fields[2][i] = grid2[i].toField();
        }

        IGamefield gamefield = new Gamefield(player1.toPlayer(), player2.toPlayer());
        gamefield.setName(getId());
        gamefield.setGamefield(fields);
        return gamefield;
    }

}
