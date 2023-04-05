package daodb4o;

import java.util.List;

import com.db4o.query.Query;

import modelo.Jogo;

public class DAOJogo extends DAO<Jogo>{

	@Override
	public Jogo read(Object chave) {
		int id = (int) chave;
		Query q = manager.query();
		q.descend("id").constrain(id);
		List<Jogo> resultados = q.execute();
		if(resultados.size() > 0) {
			return resultados.get(0);
		} else {			
			return null;
		}
	}
	public List<Jogo> jogoPorData(Object chave) {
		String data = (String) chave;
		Query q = manager.query();
		q.constrain(Jogo.class);
		q.descend("data").constrain(data);
		return q.execute();
	}
	
}
