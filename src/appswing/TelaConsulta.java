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
import java.text.ParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import modelo.Time;
import regras_negocio.Fachada;

public class TelaConsulta {
	private JFrame frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton button;
	private JButton button_4;
	private JTextField textField_1;
	private JLabel label;
	private JLabel label_5;
	private JLabel label_8;
	private JLabel label_2;
	private JTextField textField;
	private JButton button_2;



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
		frame.setVisible(true);
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				Fachada.inicializar();
				listagem();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				Fachada.finalizar();
			}
		});

		frame.setTitle("Times");
		frame.setBounds(100, 100, 912, 351);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 42, 844, 120);
		frame.getContentPane().add(scrollPane);

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


		button = new JButton("Criar time");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(textField_1.getText().isEmpty() ||
					   textField.getText().isEmpty() )
					{
						label.setText("campo vazio");
						return;
					}

					
					String nome = textField.getText();
					String origem = textField_1.getText();
					Time time = Fachada.criarTime(nome, origem);
					label.setText("time criado: "+time.getNome());
					listagem();
				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button.setBounds(314, 188, 95, 23);
		frame.getContentPane().add(button);

		label = new JLabel("");
		label.setForeground(Color.BLUE);
		label.setBackground(Color.RED);
		label.setBounds(26, 287, 830, 14);
		frame.getContentPane().add(label);

		label_5 = new JLabel("Origem");
		label_5.setHorizontalAlignment(SwingConstants.LEFT);
		label_5.setFont(new Font("Dialog", Font.PLAIN, 12));
		label_5.setBounds(26, 216, 50, 14);
		frame.getContentPane().add(label_5);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_1.setColumns(10);
		textField_1.setBounds(70, 213, 169, 20);
		frame.getContentPane().add(textField_1);

		label_8 = new JLabel("selecione");
		label_8.setBounds(26, 163, 561, 14);
		frame.getContentPane().add(label_8);

		button_4 = new JButton("Listar times com ingressos disponiveis: ");
		button_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagem();
			}
		});
		button_4.setBounds(26, 253, 243, 23);
		frame.getContentPane().add(button_4);

		label_2 = new JLabel("Nome");
		label_2.setHorizontalAlignment(SwingConstants.LEFT);
		label_2.setFont(new Font("Dialog", Font.PLAIN, 12));
		label_2.setBounds(26, 188, 50, 14);
		frame.getContentPane().add(label_2);

		textField = new JTextField();
		textField.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField.setColumns(10);
		textField.setBounds(70, 188, 169, 20);
		frame.getContentPane().add(textField);
		
		button_2 = new JButton("Apagar time");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if (table.getSelectedRow() >= 0){
						String nomeTime = (String) ""+table.getValueAt( table.getSelectedRow(), 0);
						Fachada.apagarTime(nomeTime);
						label.setText("apagou o time " +nomeTime);
						listagem();
					}
					else
						label.setText("time nao selecionado");
				}
				catch(Exception ex) {
					System.out.println(ex);
					label.setText(ex.getMessage());
				}

			}
		});
		button_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_2.setBounds(314, 238, 123, 23);
		frame.getContentPane().add(button_2);
	}

	//*****************************
	public void listarTimesComIngressoDisponivel () {
		try{
			List<Time> lista = Fachada.timesQuePossuemJogosComIngressoDisponivel();

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
			label_8.setText("resultados: "+lista.size()+ " times que possuem ingressos disponiveis");
		}
		catch(Exception erro){
			label.setText(erro.getMessage());
		}
	}
}
