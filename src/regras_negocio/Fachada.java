/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Persistencia de objetos
 * Prof. Fausto Maranh�o Ayres
 **********************************/

package regras_negocio;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import daodb4o.DAO;
import daodb4o.DAOIngresso;
import daodb4o.DAOIngressoGrupo;
import daodb4o.DAOIngressoIndividual;
import daodb4o.DAOJogo;
import daodb4o.DAOTime;
import daodb4o.DAOUsuario;
import modelo.Ingresso;
import modelo.IngressoGrupo;
import modelo.IngressoIndividual;
import modelo.Jogo;
import modelo.Time;
import modelo.Usuario;

public class Fachada {
	private Fachada() {}	

	private static DAOUsuario daousuario = new DAOUsuario(); 
	private static DAOTime daotime = new DAOTime(); 
	private static DAOJogo daojogo = new DAOJogo(); 
	private static DAOIngresso daoingresso = new DAOIngresso(); 
	private static DAOIngressoIndividual daoingressoindividual = new DAOIngressoIndividual(); 
	private static DAOIngressoGrupo daoingressogrupo = new DAOIngressoGrupo(); 


	public static Usuario logado;
	public static void inicializar(){
		DAO.open();
	}
	public static void finalizar(){
		DAO.close();
	}


	public static List<Time> listarTimes() {
		DAO.begin();
		List<Time> resultados =  daotime.readAll();
		DAO.commit();
		return resultados;
	}
	public static List<Jogo> listarJogos() {
		DAO.begin();
		List<Jogo> resultados =  daojogo.readAll();
		DAO.commit();
		return resultados;
	}
	public static List<Usuario> listarUsuarios() {
		DAO.begin();
		List<Usuario> resultados =  daousuario.readAll();
		DAO.commit();
		return resultados;
	}
	public static List<Ingresso> listarIngressos() {
		DAO.begin();
		List<Ingresso> resultados =  daoingresso.readAll();
		DAO.commit();
		return resultados;
	}
	public static List<Jogo> listarJogos(String data) {
		DAO.begin();
		List<Jogo> resultados =  daojogo.jogoPorData(data);
		DAO.commit();
		return resultados;
	}
	public static Ingresso localizarIngresso(int codigo) {
		DAO.begin();
		Ingresso resultado = daoingresso.read(codigo);
		DAO.commit();
		return resultado;
	}

	public static Jogo	localizarJogo(int id) {
		DAO.begin();
		Jogo resultado =  daojogo.read(id);
		DAO.commit();
		return resultado;
	}

	public static Usuario criarUsuario(String email, String senha) throws Exception{
		DAO.begin(); 
		Usuario usu = daousuario.read(email);
		if (usu!=null)
			throw new Exception("Usuario ja cadastrado:" + email);
		usu = new Usuario(email, senha);

		daousuario.create(usu);
		DAO.commit();
		return usu;
	}
	public static Usuario validarUsuario(String email, String senha) {
		DAO.begin();
		Usuario usu = daousuario.read(email);
		if (usu==null)
			return null;

		if (! usu.getSenha().equals(senha))
			return null;

		DAO.commit();
		return usu;
	}

	public static Time 	criarTime(String nome, String origem) throws Exception {
		DAO.begin();
		//verificar regras de negocio
		List<Time> times = listarTimes();
		for(Time t: times) {
			if(t.getNome() == nome) {
				throw new Exception("Nome ja existente");
			}
		}
		//criar o time
		Time time = new Time(nome,origem);
		
		//gravar time no banco
		DAO.commit();
		return time;
	}

	public static Jogo 	criarJogo(String data, String local, int estoque, double preco, String nometime1, String nometime2)  throws Exception {
		DAO.begin();
		//verificar regras de negocio
		if (data.trim().length() == 0 || local.trim().length() == 0) {
			throw new Exception("Data e/ou local devem ser preenchidos.");
		}
		if (estoque <= 0 || preco <= 0.0) {
			throw new Exception("Estoque e/ou preço devem ser maior que zero.");
		}
		//localizar time1 e time2
		Time time1 = daotime.read(nometime1);
		Time time2 = daotime.read(nometime2);
		if(time1 == null || time2 == null) {
			throw new Exception("Times inválidos.");
		}
		
		//criar  jogo 
		Jogo jogo = new Jogo(data, local, estoque, preco);

		//relacionar o jogo com os times e vice-versa 
		jogo.setTime1(time1);
		jogo.setTime2(time2);
		time1.adicionar(jogo);
		time2.adicionar(jogo);
		
		//gravar jogo no banco
		daojogo.create(jogo);
		DAO.commit();
		return jogo;
	}

	public static IngressoIndividual criarIngressoIndividual(int id) throws Exception{
		DAO.begin();
		//verificar regras de negocio
		Jogo jogo = daojogo.read(id);
		if (jogo != null) {
			
			//array com todos os id já registrados 
			ArrayList<Integer> ingressosId = new ArrayList<>();
			for(Ingresso i: jogo.getIngressos()) {
				ingressosId.add(i.getCodigo());
			}
			//gerar codigo aleat�rio 
			int codigo = new Random().nextInt(1000000);
			//verificar unicididade do codigo no sistema 
			while(ingressosId.contains(codigo)) {
				codigo = new Random().nextInt(1000000);
			}
			//criar o ingresso individual 
			IngressoIndividual ingresso = new IngressoIndividual(codigo);
			
			//relacionar este ingresso com o jogo e vice-versa
			ingresso.setJogo(jogo);
			jogo.adicionar(ingresso);
			jogo.setEstoque(jogo.getEstoque()-1);
			//gravar ingresso no banco
			daoingressoindividual.create(ingresso);
			DAO.commit();
			return ingresso;
		}
		throw new Exception("Jogo nao encontrado");

	}

	public static IngressoGrupo	criarIngressoGrupo(int[] ids) throws Exception{
		DAO.begin();
		//verificar regras de negocio
		//gerar codigo aleat�rio 
		//verificar unicididade no sistema 		
		//criar o ingresso grupo 
		//relacionar este ingresso com os jogos indicados e vice-versa
		ArrayList<Jogo> jogosIndicados = new ArrayList<>();
		ArrayList<Integer> idIngressosJogos = new ArrayList<>();
		ArrayList<Integer> codJogos = new ArrayList<>();
		
		// criação dos codigos de jogos disponíveis no repositório
		for (Jogo j: listarJogos()) {
			codJogos.add(j.getId());
		}
		int codigo = new Random().nextInt(1000000);
		// Verificação se o código disponibilizado pelo usuário é condizente com os códigos 
		// disponíveis e no seguinte fluxo do código, caso não retorne uma exceção,
		// adição do jogo à lista de jogos indicados pelo usuário
		for (Integer i: ids) {
			if(!codJogos.contains(i)) {
				throw new Exception("O jogo de id '"+i+"' não existe.");
			}
			jogosIndicados.add(daojogo.read(i));
		}
		// Adição dos códigos de ingressos à lista que mostrará todos os ingressos dos possíveis jogos indicados pelo usuário
		for (Jogo j: jogosIndicados) {
			for (Ingresso ing: j.getIngressos()) {
				idIngressosJogos.add(ing.getCodigo());
			}
		}
		while(idIngressosJogos.contains(codigo)) {
			codigo = new Random().nextInt(1000000);
		}
		
		IngressoGrupo ingresso = new IngressoGrupo(codigo);
		
		for (Jogo j: jogosIndicados) {
			j.adicionar(ingresso);
			j.setEstoque(j.getEstoque()-1);
			ingresso.adicionar(j);
		}		
		//gravar ingresso no banco
		daoingressogrupo.create(ingresso);
		DAO.commit();
		return null;
	}

	public static void	apagarIngresso(int codigo) throws Exception {
		DAO.begin();
		//o codigo refere-se a ingresso individual ou grupo
		//verificar regras de negocio
		//remover o relacionamento entre o ingresso e o(s) jogo(s) ligados a ele
		Ingresso ingresso = daoingresso.read(codigo);
		
		if (ingresso == null) {			
			throw new Exception("Ingresso '"+ codigo +"' não encontrado");
		}
			
		if (ingresso instanceof IngressoGrupo) {
			ArrayList<Jogo> jogosGrupo = ((IngressoGrupo) ingresso).getJogos();
			((IngressoGrupo) ingresso).setJogos(null);
			for (Jogo j: jogosGrupo) {
				j.remover(ingresso);
				j.setEstoque(j.getEstoque()+1);
				//nao precisa remover o jogo do ingresso pq o ingresso será deletado
				((IngressoGrupo) ingresso).remover(j);
			}
			daoingresso.delete(ingresso);
			//___________________________precisa deletar o daoingressogrupo/daoingressoindividual?________________________
		}else {				
			Jogo jogoIndividual = ((IngressoIndividual) ingresso).getJogo();
			((IngressoIndividual) ingresso).setJogo(null);
			jogoIndividual.remover(ingresso);
			jogoIndividual.setEstoque(jogoIndividual.getEstoque()+1);
			daoingresso.delete(ingresso);
		}
		DAO.commit();
	}

	public static void	apagarTime(int id) throws Exception {
		DAO.begin();
		//verificar regras de negocio
		//apagar time no banco
		DAO.commit();
	}

	public static void 	apagarJogo(int id) throws Exception{
		DAO.begin();
		//verificar regras de negocio
		//apagar jogo no banco
		DAO.commit();
	}

	/**********************************
	 * 5 Consultas
	 **********************************/
}
