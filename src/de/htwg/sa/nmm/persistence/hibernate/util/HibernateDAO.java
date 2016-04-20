package de.htwg.sa.nmm.persistence.hibernate.util;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import de.htwg.sa.nmm.model.IGamefield;
import de.htwg.sa.nmm.persistence.IDAO;
import de.htwg.sa.nmm.persistence.OperationResult;
import de.htwg.sa.nmm.persistence.hibernate.HibernateGamefield;

public class HibernateDAO implements IDAO {

	@Override
	public OperationResult storeGamefield(IGamefield gamefield) {
		Session session = null;
		Transaction tx = null;

		HibernateGamefield game = HibernateGamefield.transformToHibernate(gamefield);

		try {
			session = HibernateSessionUtil.getInstance().getCurrentSession();
			tx = session.beginTransaction();

			session.saveOrUpdate(game);

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();

			return new OperationResult(false, e.getMessage());
		}

		return new OperationResult(true);
	}

	@Override
	public IGamefield loadGamefiledByName(final String name) {
		Session session = HibernateSessionUtil.getInstance().getCurrentSession();
		session.beginTransaction();

		return HibernateGamefield
				.transformFromHibernate((HibernateGamefield) session.get(HibernateGamefield.class, name));
	}

	@Override
	public List<String> getAllGamefieldNames() {
		Session session = HibernateSessionUtil.getInstance().getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(HibernateGamefield.class);
		
		@SuppressWarnings("unchecked")
		List<HibernateGamefield> results = criteria.list();
		
		List<String> names = new ArrayList<>();
		
		for (HibernateGamefield hg : results) 
			names.add(hg.getName());
		
		return names;
	}

	@Override
	public OperationResult deleteGamefieldByName(String name) {
		Session session = null;
		Transaction tx = null;

		IGamefield game = loadGamefiledByName(name);
		if (game == null)
			return new OperationResult(false, "No such game!");

		try {
			session = HibernateSessionUtil.getInstance().getCurrentSession();
			tx = session.beginTransaction();

			session.delete(game);

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();

			return new OperationResult(false, e.getMessage());
		}

		return new OperationResult(true);
	}

	@Override
	public boolean init() {
		// TODO Auto-generated method stub
		return true;
	}

}
