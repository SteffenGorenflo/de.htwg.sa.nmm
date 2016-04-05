package de.htwg.sa.nmm.persistence.hibernate;

import java.awt.Color;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import de.htwg.sa.nmm.model.IPlayer.Status;
import de.htwg.sa.nmm.model.impl.Player;

/**
 * {@link Player} class with Hibernate annotations
 * 
 * @author Patrick Schmidt
 * @since 2016-04-05
 */
@Entity
@Table(name = "PLAYER")
public class HibernatePlayer implements Serializable {

	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = -1428233773590215096L;

	/**
	 * Name of the player
	 */
	@Column(name = "playername")
	private final String name = "";

	/**
	 * Tokencolor
	 */
	@Column(name = "color")
	private final Color color = null;

	/**
	 * Status of the player
	 */
	@Column(name = "status")
	private Status status;

	/**
	 * Number of tokens player can place
	 */
	@Column(name = "token")
	private int token;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getToken() {
		return token;
	}

	public void setToken(int token) {
		this.token = token;
	}

	public String getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}
}