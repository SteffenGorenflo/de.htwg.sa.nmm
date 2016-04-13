package de.htwg.sa.nmm.persistence.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.htwg.sa.nmm.model.IToken;

/**
 * Field class for Hibernate
 * 
 * @author Patrick Schmidt
 * @since 2016-04-12
 */
@Entity
@Table(name = "FIELD")
public class HibernateField {

	/**
	 * Number of Grid
	 */
	@Column(name = "grid")
	private int grid;

	/**
	 * Index of Grid
	 */
	@Column(name = "index")
	private int index;
	
	/**
	 * placed token
	 */
	@OneToOne
	@JoinColumn(name = "token")
	private IToken token;

	/**
	 * Returns the number of the grid
	 * 
	 * @return grid
	 */
	public int getGrid() {
		return grid;
	}

	/**
	 * Set the number of the grid
	 * 
	 * @param grid
	 */
	public void setGrid(int grid) {
		this.grid = grid;
	}

	/**
	 * Get the index (postition of the grid)
	 * 
	 * @return
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Set the index of the grid
	 * 
	 * @param index
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * Get the Token 
	 * 
	 * @return token
	 */
	public IToken getToken() {
		return token;
	}

	/**
	 * Set the Token of the specific position
	 * 
	 * @param token
	 */
	public void setToken(IToken token) {
		this.token = token;
	}
}
