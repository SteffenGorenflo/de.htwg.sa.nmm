package de.htwg.sa.nmm.persistence.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Utillityclass to store a Matrix in the Database
 * 
 * @author Patrick Schmidt
 * @since 2016-04-20
 */
@Entity
@Table(name = "Hibernate_TEST2")
public class HibernateMatrixRow implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@ManyToOne
	HibernateGamefield game;

	@OneToMany(mappedBy = "matrixRow")
	List<HibernateField> row = new ArrayList<HibernateField>();

	// ##### Getter/setter Methods ####

	public HibernateMatrixRow() {
	}

	public HibernateGamefield getGame() {
		return game;
	}

	public void setGame(HibernateGamefield game) {
		this.game = game;
	}

	public List<HibernateField> getRow() {
		return row;
	}

	public void setRow(List<HibernateField> row) {
		this.row = row;
	}

}
