package de.htwg.sa.nmm.persistence.hibernate.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * Build Hibernate Session
 * 
 * @author Patrick Schmidt
 * @since 2016-04-13
 */
public class HibernateSessionUtil {

	private static final SessionFactory sessionFactory;

	static {
		final AnnotationConfiguration cfg = new AnnotationConfiguration();
		cfg.configure("/hibernate.cfg.xml");
		sessionFactory = cfg.buildSessionFactory();
	}

	private HibernateSessionUtil() {
	}

	public static SessionFactory getInstance() {
		return sessionFactory;
	}
}
