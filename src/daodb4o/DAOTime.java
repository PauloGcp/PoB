package daodb4o;

import java.util.List;

import com.db4o.query.Query;

import modelo.Time;

public class DAOTime extends DAO<Time>{

	@Override
	public Time read(Object chave) {
		String name = (String) chave;
		Query q = manager.query();
		q.descend("nome").constrain(name);
		List<Time> resultados = q.execute();
		if(resultados.size() > 0) {
			return resultados.get(0);
		} else {			
			return null;
		}
	}
	
}
