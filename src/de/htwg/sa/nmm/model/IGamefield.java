package de.htwg.sa.nmm.model;

public interface IGamefield {
	
	void init();
	
	IField field(int grid, int index);
	
	int countToken(IPlayer player);
	
	boolean mill(IField field);
			
	int grids();
	
	int index();
	
	boolean valid(int grid, int index);

    IPlayer getCurrentPlayer();

    IPlayer getOtherPlayer();

    void nextPlayer();

    void setStatus(IPlayer.Status status);
    
    String getId();
    
    void setId(String id);
}
