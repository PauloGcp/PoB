package daodb4o;

import java.util.List;

import com.db4o.query.Candidate;
import com.db4o.query.Evaluation;
import com.db4o.query.Query;

import modelo.IngressoGrupo;
import modelo.Jogo;
import modelo.Time;

public class DAOIngressoGrupo extends DAO<IngressoGrupo>{

	@Override
	public IngressoGrupo read(Object chave) {
		int codigo = (int) chave;
		Query q = manager.query();
		q.descend("codigo").constrain(codigo);
		List<IngressoGrupo> resultados = q.execute();
		if(resultados.size() > 0) {
			return resultados.get(0);
		} else {			
			return null;
		}
	}
//	public List<IngressoGrupo> ingressoGrupoComMaisDeDoisJogos() {
//		Query q = manager.query();
//		q.constrain(IngressoGrupo.class);
//		q.constrain(new Filtro1());
//		List<IngressoGrupo> resultados = q.execute();
//		return resultados; 
//	}
	
}
class Filtro1 implements Evaluation {
	public void evaluate(Candidate candidate) {
		IngressoGrupo ig = (IngressoGrupo) candidate.getObject();
		if(ig.getJogos().size() > 2)
			candidate.include(true);
		else
			candidate.include(false);
	}
}