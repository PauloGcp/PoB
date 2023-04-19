/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Persistencia de objetos
 * Prof. Fausto Maranh�o Ayres
 **********************************/
package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;


import modelo.Ingresso;
import modelo.IngressoGrupo;
import modelo.IngressoIndividual;
import modelo.Jogo;
import modelo.Time;
import regras_negocio.Fachada;

public class TelaConsulta {
	private JFrame frmConsulta;
	private JTable table;
	private JScrollPane scrollPane;
	private JLabel label;
	private JLabel label_8;



	/**
	 * Launch the application.
	 */
	//	public static void main(String[] args) {
	//		EventQueue.invokeLater(new Runnable() {
	//			public void run() {
	//				try {
	//					TelaJogo window = new TelaJogo();
	//					window.frame.setVisible(true);
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//				}
	//			}
	//		});
	//	}

	/**
	 * Create the application.
	 */
	public TelaConsulta() {
		initialize();
		frmConsulta.setVisible(true);
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmConsulta = new JFrame();
		frmConsulta.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));
		frmConsulta.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				Fachada.inicializar();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				Fachada.finalizar();
			}
		});

		frmConsulta.setTitle("Consulta");
		frmConsulta.setBounds(100, 100, 1042, 351);
		frmConsulta.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmConsulta.getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 74, 844, 120);
		frmConsulta.getContentPane().add(scrollPane);

		table = new JTable();
		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.WHITE);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		label = new JLabel("");
		label.setBounds(26, 254, 990, 47);
		label.setForeground(Color.BLUE);
		label.setBackground(Color.RED);
		frmConsulta.getContentPane().add(label);

		label_8 = new JLabel("selecione");
		label_8.setBounds(26, 205, 561, 14);
		frmConsulta.getContentPane().add(label_8);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Times que jogarão em determinada data", "Times de determinado jogo", "Times que Jogarão em determinado local", "Times que possuem jogos com ingresso disponivel", "Jogos por time"}));
		comboBox.setToolTipText("");
		comboBox.setBounds(32, 22, 381, 22);
		frmConsulta.getContentPane().add(comboBox);
		
		JButton btnNewButton = new JButton("consultar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				int index = comboBox.getSelectedIndex();
				if(index < 0)
					label_8.setText("consulta nao selecionada");
				else
					switch(index) {
					case 0:
						String data = JOptionPane.showInputDialog("digite a data do jogo");
						List<Time> resultado1 = Fachada.timesQueJogaraoEmDeterminadaData(data);
						listagemTime(resultado1);
						break;
					case 1:
						String codJogo = JOptionPane.showInputDialog("digite o id do jogo");
						List<Time> resultado2 = Fachada.timesPorJogo(Integer.parseInt(codJogo));
						listagemTime(resultado2);
						break;
					case 2:
						String local = JOptionPane.showInputDialog("digite o local");												
						List<Time> resultado3 = Fachada.timesQueJogaraoEmDeterminadoLocal(local);
						timesQueJogaraoEmDeterminadoLocal(resultado3);
						break;
					case 3:
						List<Time> resultado4 = Fachada.timesQuePossuemJogosComIngressoDisponivel();
						listagemTime(resultado4);
						break;
					case 4:
						String time2 = JOptionPane.showInputDialog("digite o time");
						List<Jogo> resultado5 = Fachada.jogosPorTime(time2);
						jogosPorTime(resultado5);
						break;	
					}
			}
			catch(Exception erro) {
				DefaultTableModel model = new DefaultTableModel();
				table.setModel(model);
				label_8.setText(erro.getMessage());
			}
		}});
		btnNewButton.setBounds(456, 22, 89, 23);
		frmConsulta.getContentPane().add(btnNewButton);
	}

	//*****************************
	public void timesQueJogaraoEmDeterminadoLocal (List<Time> lista) {
		try{	
			DefaultTableModel model = new DefaultTableModel();
			//colunas
			model.addColumn("nome");
			model.addColumn("origem");
			model.addColumn("Quantidade de jogos");
			//linhas
			for(Time time : lista) {
				model.addRow(new Object[]{time.getNome()+"", time.getOrigem(), time.getJogos().size()});
			}
			table.setModel(model);
			label_8.setText("resultados: "+lista.size()+ " times que jogaram na localização");
			
			//model contem todas as linhas e colunas da tabela
		}
		catch(Exception erro){
			label.setText(erro.getMessage());
		}
	}
	
	public void listagemIngresso(List<Ingresso> lista) {
		try{
			
			//model contem todas as linhas e colunas da tabela
			DefaultTableModel model = new DefaultTableModel();

			//colunas
			model.addColumn("tipo");
			model.addColumn("codigo");
			model.addColumn("valor");
			model.addColumn("jogos");

			//linhas
			String texto;
			for(Ingresso ingresso : lista) {
				if(ingresso instanceof IngressoIndividual ind) {
					int id = ind.getJogo().getId();
					model.addRow(new Object[]{"Individual" ,ingresso.getCodigo(), ingresso.calcularValor(), id});
				}
				else 	
					if(ingresso instanceof IngressoGrupo gp) {
						texto="";
						for(Jogo j : gp.getJogos()) 	//obter os id  dos jogos
							texto += j.getId()+ "," ;

						model.addRow(new Object[]{"Grupo" ,ingresso.getCodigo(), ingresso.calcularValor(), texto});
					}
			}
			
			table.setModel(model);
		}
		catch(Exception erro){
			label.setText(erro.getMessage());
		}
	}
	
	public void listagemIngressoGrupo(List<IngressoGrupo> lista) {
		try{
			
			//model contem todas as linhas e colunas da tabela
			DefaultTableModel model = new DefaultTableModel();

			//colunas
			model.addColumn("tipo");
			model.addColumn("codigo");
			model.addColumn("valor");
			model.addColumn("jogos");

			//linhas
			String texto;
			
			texto="";
	
			model.addRow(new Object[]{"Grupo" ,((Ingresso) lista).getCodigo(), ((Ingresso) lista).calcularValor(), texto});
			
			
			table.setModel(model);
		}
		catch(Exception erro){
			label.setText(erro.getMessage());
		}
	}
	
	
	
	public void listagemTime(List<Time> lista) {
		try{
			//model contem todas as linhas e colunas da tabela
			DefaultTableModel model = new DefaultTableModel();
			//colunas
			model.addColumn("nome");
			model.addColumn("origem");
			model.addColumn("Quantidade de jogos");
			//linhas
			for(Time time : lista) {
				model.addRow(new Object[]{time.getNome()+"", time.getOrigem(), time.getJogos().size()});
			}
			table.setModel(model);
			
		}
		catch(Exception erro){
			label.setText(erro.getMessage());
		}
	}
	
	public void jogosPorTime(List<Jogo> lista) {
		try{
			//model contem todas as linhas e colunas da tabela
			DefaultTableModel model = new DefaultTableModel();
			//colunas
			model.addColumn("id");
			model.addColumn("data");
			model.addColumn("loca");
			model.addColumn("estoque");
			model.addColumn("preco");
			model.addColumn("time1");
			model.addColumn("time2");
			model.addColumn("arrecadacao");
			//linhas
			for(Jogo jogo : lista) {
				model.addRow(new Object[]{jogo.getId()+"", jogo.getData(), jogo.getLocal(), jogo.getEstoque(),jogo.getPreco(),
						jogo.getTime1().getNome(), jogo.getTime2().getNome(), jogo.obterValorArrecadado()});
			}
			table.setModel(model);
			label_8.setText("O time tem "+lista.size()+ " jogos marcados");
		}
		catch(Exception erro){
			label.setText(erro.getMessage());
		}
	}
}
