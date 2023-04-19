package daodb4o;

import java.util.List;

import com.db4o.query.Query;

import modelo.Ingresso;

public class DAOIngresso extends DAO<Ingresso>{

	@Override
	public Ingresso read(Object chave) {
		int codigo = (int) chave;
		Query q = manager.query();
		q.descend("codigo").constrain(codigo);
		List<Ingresso> resultados = q.execute();
		if(resultados.size() > 0) {
			return resultados.get(0);
		} else {			
			return null;
		}
	}
	public List<Ingresso> ingressosPorJogo(Object chave) {
	    int idJogo = (int) chave;
	    Query 	q = manager	.query();
	    q.constrain(Ingresso.class);
	    q.descend("jogo").descend("id").constrain(idJogo);
	
	    return q.execute();
	}
}
