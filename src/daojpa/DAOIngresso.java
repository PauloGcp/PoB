package daojpa;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.TypedQuery;
import modelo.Ingresso;
import modelo.Time;

public class DAOIngresso extends DAO<Ingresso> {
	public Ingresso read (Object chave) {
		int id = (int) chave;
		try {
			TypedQuery<Ingresso> q = manager.createQuery("select i from Ingresso i where i.id=:x", Ingresso.class);
			q.setParameter('x', id);
			return q.getSingleResult();

		}
		catch(Exception e) {
			return null;
		}
	}
	
	public List<Ingresso> ingressosPorJogo(Object chave) {
	    int idJogo = (int) chave;
	    try {
	    	TypedQuery<Ingresso> q = manager.createQuery("select i from Ingresso i join i.jogos j where j.id=:x", Ingresso.class);
	    	q.setParameter('x', idJogo);
			return q.getResultList();
	    } catch (Exception e) {
	    	List<Ingresso> without = new ArrayList<Ingresso>();
			return without;
	    }
	
	}

	@Override
	public List<Ingresso> readAll() {
		TypedQuery<Ingresso> q = manager.createQuery("select i from Ingresso i", Ingresso.class);
		return q.getResultList();
	}

	

    
}