package de.htwg.sa.nmm.persistence.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.db4o.internal.Null;

import de.htwg.sa.nmm.model.IToken;

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
	@OneToOne(targetEntity = Null.class)
	private IToken token;

	public int getGrid() {
		return grid;
	}

	public void setGrid(int grid) {
		this.grid = grid;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public IToken getToken() {
		return token;
	}

	public void setToken(IToken token) {
		this.token = token;
	}
}
