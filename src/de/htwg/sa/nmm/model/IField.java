package de.htwg.sa.nmm.model;

import de.htwg.sa.nmm.model.IToken;

public interface IField {

	int grid();
	
	int index();
	
	IToken getToken();
	
	boolean hasToken();
	
	void setToken(IToken token);
	
	boolean neighbour(IField other);
	
	boolean sameIndex(IField other);
	
	boolean indexNeighbour(IField other);
			
	boolean sameGrid(IField other);
	
	boolean gridNeighour(IField other);
	
	boolean corner();
	
}
