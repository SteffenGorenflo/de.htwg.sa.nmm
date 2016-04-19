package de.htwg.sa.nmm.persistence.hibernate.util;

import java.util.List;

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
			System.out.println(e.getMessage());
			return new OperationResult(false);
		}
		
		return new OperationResult(true);
	}

	@Override
	public IGamefield loadGamefieldByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAllGamefieldNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OperationResult deleteGamefieldByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean init() {
		// TODO Auto-generated method stub
		return true;
	}

}
