package daojpa;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.TypedQuery;
import modelo.Jogo;

public class DAOJogo extends DAO<Jogo> {
	public Jogo read (Object chave) {
		int id = (int) chave;
		try {
			TypedQuery<Jogo> q = manager.createQuery("select j from Jogo j where j.id=:x", Jogo.class);
			q.setParameter("x",id);
			return q.getSingleResult();

		}
		catch(Exception e) {
			return null;
		}
	}
	
	
	public List<Jogo> jogoPorData(Object chave) {
		String data = (String) chave;
		try {
			TypedQuery<Jogo> q = manager.createQuery("select j from Jogo j where data=:x", Jogo.class);
			q.setParameter("x",data);
			return q.getResultList();
		} catch (Exception e) {
			List<Jogo> without = new ArrayList<Jogo>(); 
			return without;
		}
		
	}
	public List<Jogo> jogosPorTime(Object chave) {
		String time = (String) chave;
		try {			
			TypedQuery<Jogo> q = manager.createQuery("select j from Jogo j join j.times t where t.nome=:x", Jogo.class);
			q.setParameter("x", time);
			return q.getResultList();
		} catch (Exception e) {
			List<Jogo> without = new ArrayList<Jogo>();
			return without;
		}

    }
	
	@Override
	public List<Jogo> readAll() {
		TypedQuery<Jogo> q = manager.createQuery("select j from Jogo j", Jogo.class);	
		return q.getResultList();
	}
	
}
