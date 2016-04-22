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

/**
 * Hibernate Class to store the Tokencolor
 * 
 * @author Patrick Schmidt
 * @since 2016-04-20
 */
@Entity
@Table(name = "Hibernate_TEST4")
public class HibernateToken implements Serializable {

	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Generated ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	/**
	 * Tokencolor
	 */
	@Column(name = "color")
	private Color color;

	/**
	 * The Field, which the Token belongs to
	 */
	@OneToOne
	@JoinColumn(name = "field")
	private HibernateField field;

	/**
	 * Transform a {@link de.htwg.sa.nmm.model.IField} Object to
	 * {@link de.htwg.sa.nmm.persistence.hibernate.HibernateToken}
	 * 
	 * @param token
	 * @return An instance of Hibernate Token with the data of the parameter
	 */
	static HibernateToken transformToHibernate(IToken token) {
		if (token == null)
			return null;

		HibernateToken t = new HibernateToken();

		t.color = token.color();

		return t;
	}

	/**
	 * Transform a {@link de.htwg.sa.nmm.persistence.hibernate.HibernateToken}
	 * Object to {@link de.htwg.sa.nmm.model.IField}
	 * 
	 * @param HibernateToken
	 * @return An instance of IToken with the data of the parameter
	 */
	static IToken transformFromHibernate(HibernateToken ht) {
		if (ht == null)
			return null;

		return new Token(ht.color);
	}

	// ##### Getter/setter Methods ####

	public HibernateToken() {
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public HibernateField getField() {
		return field;
	}

	public void setField(HibernateField field) {
		this.field = field;
	}
}
