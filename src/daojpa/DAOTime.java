package daojpa;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.TypedQuery;
import modelo.Time;

public class DAOTime extends DAO<Time>{
	public Time read(Object chave) {
    	String nome = (String) chave;
		try {
			TypedQuery<Time> q = manager.createQuery("select t from Time t where t.nome=:x", Time.class);
			q.setParameter("x", nome);
			return q.getSingleResult();
		}
		catch(Exception e) {
			return null;
		}

    }
	
	public List<Time> timesQuePossuemJogosComIngressoDisponivel() {
		try {
			TypedQuery<Time> q = manager.createQuery("select t from Time t join t.jogos j where j.estoque > 0", Time.class);
			return q.getResultList();
		} catch (Exception e) {
			List<Time> without = new ArrayList<Time>();
			return without;
		}
	}
	
	public List<Time> timesPorJogo(Object chave) {
	    int idJogo = (int) chave;
	    try {
			TypedQuery<Time> q = manager.createQuery("select t from Time t join t.jogos j where j.id=:x", Time.class);
			q.setParameter("x", idJogo);
			return q.getResultList();
	    } catch (Exception e) {
			List<Time> without = new ArrayList<Time>();
			return without;
		}
	}
	
	public List<Time> timesQueJogaraoEmDeterminadoLocal (Object chave) {
		String local = (String)chave;
		try {
			TypedQuery<Time> q = manager.createQuery("select t from Time t join t.jogos j where j.local=:x", Time.class);
			q.setParameter("x", local);
			return q.getResultList();
		} catch (Exception e) {
			List<Time> without = new ArrayList<Time>();
			return without;
		}
    	
    }
	public List<Time> timesQueJogaraoEmDeterminadaData(Object chave) {
	    String data = (String) chave;
	    try {
	    	TypedQuery<Time> q = manager.createQuery("select t from Time t join t.jogos j where j.data=:x", Time.class);
			q.setParameter("x", data);
			return q.getResultList();
	    } catch (Exception e) {
			List<Time> without = new ArrayList<Time>();
			return without;
		}
	}
	
	@Override
	public List<Time> readAll() {
		TypedQuery<Time> q = manager.createQuery("select t from Time t", Time.class);
		return q.getResultList();
	}
}
