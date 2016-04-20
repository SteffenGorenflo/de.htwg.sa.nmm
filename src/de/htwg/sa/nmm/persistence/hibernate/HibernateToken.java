package de.htwg.sa.nmm.persistence.hibernate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.htwg.sa.nmm.model.IToken;
import de.htwg.sa.nmm.model.IToken.Color;
import de.htwg.sa.nmm.model.impl.Token;

@Entity
@Table(name = "Hibernate_TEST4")
public class HibernateToken implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@Column(name = "color")
	Color color;
	
	@OneToOne
	@JoinColumn(name = "field")
	HibernateField field;

	static HibernateToken transformToHibernate(IToken token) {
		if (token == null)
			return null;
		
		HibernateToken t = new HibernateToken();

		t.color = token.color();

		return t;
	}

	static IToken transformFromHibernate(HibernateToken ht) {
		if (ht == null)
			return null;
		
		return new Token(ht.color);
	}

}
