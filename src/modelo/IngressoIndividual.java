/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Persistencia de objetos
 * Prof. Fausto Maranh�o Ayres
 **********************************/
package modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class IngressoIndividual extends Ingresso {
	//teve que padronizar para array e tratar sempre no primeiro indice na fachada para nao dar conflito no jpa
	@ManyToMany(mappedBy = "ingressos", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Jogo> jogos = new ArrayList<>();
	public IngressoIndividual() {
	}

	public IngressoIndividual(int codigo) {
		super(codigo);
	}

	public double calcularValor() {
		return 1.2 * jogos.get(0).getPreco();
	}

	public Jogo getJogo() {
		return jogos.get(0);
	}

	public void setJogo(Jogo jogo) {
		this.jogos.add(jogo);
		//jogo.setEstoque(jogo.getEstoque() - 1 );
	}

	@Override
	public String toString() {
		return "codigo=" + codigo + ", jogo=" + jogos.get(0).getId();
	}
	
	
	
}
