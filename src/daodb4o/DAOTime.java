package daodb4o;

import java.util.List;

import com.db4o.query.Query;

import modelo.Time;

public class DAOTime extends DAO<Time>{

	@Override
	public Time read(Object chave) {
		String name = (String) chave;
		Query q = manager.query();
		System.out.println("q" +  q);
		q.descend("nome").constrain(name);
		List<Time> resultados = q.execute();
		if(resultados.size() > 0) {
			return resultados.get(0);
		} else {			
			return null;
		}
		
	}
	public List<Time> timesQuePossuemJogosComIngressoDisponivel() {
		Query q = manager.query();
		q.constrain(Time.class);
		q.descend("jogos").descend("ingressos").descend("estoque").constrain(1).greater();
		return q.execute();
	}
	
	public List<Time> timesQueJogaraoEmDeterminadoLocal(Object chave) {
		String local = (String)chave;
		Query q = manager.query();
		q.constrain(Time.class);
		q.descend("jogos").constrain(local);
		return q.execute();
	}
	
	public List<Time> timesQueEnfretaráDeterminadoAdversario(Object chave) {
		String adversario = (String)chave;
		Query q = manager.query();
		q.constrain(Time.class);
		q.descend("jogos").descend("time1").constrain(adversario).or(q.descend("jogos").descend("time2").constrain(adversario));
		return q.execute();
	}
}
