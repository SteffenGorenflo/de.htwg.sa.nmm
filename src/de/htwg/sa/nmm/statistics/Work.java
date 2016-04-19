package de.htwg.sa.nmm.statistics;


import de.htwg.sa.nmm.model.IGamefield;

public class Work {

    public final IGamefield gamefield;
    public final int grid;

    public Work(IGamefield gamefield, int grid) {
        this.gamefield = gamefield;
        this.grid = grid;
    }
}
