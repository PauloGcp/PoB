package daodb4o;

import java.util.List;

import com.db4o.query.Query;

import modelo.IngressoIndividual;

public class DAOIngressoIndividual extends DAO<IngressoIndividual>{

	@Override
	public IngressoIndividual read(Object chave) {
		int codigo = (int) chave;
		Query q = manager.query();
		q.descend("codigo").constrain(codigo);
		List<IngressoIndividual> resultados = q.execute();
		if(resultados.size() > 0) {
			return resultados.get(0);
		} else {			
			return null;
		}
	}
	
}
