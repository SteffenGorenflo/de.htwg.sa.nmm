package de.htwg.sa.nmm.persistence.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.htwg.sa.nmm.model.IField;
import de.htwg.sa.nmm.model.IToken;
import de.htwg.sa.nmm.model.impl.Field;

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
	int grid;

	/**
	 * Index of Grid
	 */
	@Column(name = "index")
	int index;
	
	/**
	 * placed token
	 */
	@OneToOne
	@JoinColumn(name = "token")
	IToken token;

	static HibernateField transformToHibernate(IField field) {
		HibernateField f = new HibernateField();
		
		f.grid = field.grid();
		f.index = field.index();
		f.token = field.getToken();
		
		return f;
	}
	
	static IField transformFromHibernate(HibernateField field) {
		Field f = new Field(field.grid, field.index);
		
		f.setToken(field.token);
		
		return f;
	}
}
