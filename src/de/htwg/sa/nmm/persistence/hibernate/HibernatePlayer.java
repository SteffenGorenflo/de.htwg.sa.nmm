package de.htwg.sa.nmm.persistence.hibernate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import de.htwg.sa.nmm.model.IPlayer;
import de.htwg.sa.nmm.model.IPlayer.Status;
import de.htwg.sa.nmm.model.IToken;
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
	String name = "";

	/**
	 * Tokencolor
	 */
	@Column(name = "color")
	IToken.Color color = null;

	/**
	 * Status of the player
	 */
	@Column(name = "status")
	Status status;

	/**
	 * Number of tokens player can place
	 */
	@Column(name = "token")
	int token;
	
	static HibernatePlayer transformToHibernate(IPlayer player) {
		HibernatePlayer p = new HibernatePlayer();
		
		p.name = player.name();
		p.color = player.color();
		p.status = player.getStatus();
		p.token = player.token();
		
		return p;
	}
	
	static IPlayer transformFromHibernate(HibernatePlayer player) {
		Player p = new Player(player.name, player.color);
		
		p.setStatus(player.status);
		p.setToken(player.token);
		
		return p;
	}
}