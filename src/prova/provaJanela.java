package prova;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class provaJanela implements ActionListener {

	private JFrame frame;
	private JTextField nomeBem;
	private JTextField valorInicial;
	private JButton btnNewButton;
	private JLabel lblNewLabel_2;
	private JList<String> listaBensCadastrados;
	private JLabel lblNewLabel_3;
	private JTextField bemLeiloado;
	private JPanel panel;
	private JLabel lblNewLabel_4;
	private JTextField nomeArrematante;
	private JLabel lblNewLabel_5;
	private JTextField valorLance;
	private JLabel lblNewLabel_6;
	private JRadioButton sim;
	private JRadioButton nao;
	private JButton botaoRegistrar;
	private DefaultListModel<String> defaultListModel;
	private List<Leilao> leiloes;
	private Leilao leilaoCorrente;
	private JButton gravar;
	private JButton carregar;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					provaJanela window = new provaJanela();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public provaJanela() {
		leiloes = new ArrayList<Leilao>();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Leilão do joão");
		frame.setBounds(100, 100, 569, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[][grow][grow]", "[][][][][grow]"));
		
		JLabel lblNewLabel = new JLabel("Nome do bem");
		frame.getContentPane().add(lblNewLabel, "cell 0 0,alignx trailing");
		
		nomeBem = new JTextField();
		frame.getContentPane().add(nomeBem, "cell 1 0 2 1,growx");
		nomeBem.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Valor Inicial");
		frame.getContentPane().add(lblNewLabel_1, "cell 0 1,alignx trailing");
		
		valorInicial = new JTextField();
		frame.getContentPane().add(valorInicial, "cell 1 1 2 1,growx");
		valorInicial.setColumns(10);
		
		btnNewButton = new JButton("Cadastrar");
		btnNewButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				String nome = nomeBem.getText();
				double valorIncial = Double.parseDouble(valorInicial.getText());
			
				Leilao leilao = new Leilao(nome, valorIncial);
				defaultListModel.addElement(nome);
				leiloes.add(leilao);
				
			}
		});
		frame.getContentPane().add(btnNewButton, "cell 0 2 3 1,growx");
		
		lblNewLabel_2 = new JLabel("Bens Cadastrados");
		frame.getContentPane().add(lblNewLabel_2, "cell 0 3 2 1,alignx center");
		
		lblNewLabel_3 = new JLabel("Bem Leiloado");
		frame.getContentPane().add(lblNewLabel_3, "flowx,cell 2 3");
		
		defaultListModel = new DefaultListModel<String>();
		listaBensCadastrados = new JList<String>(defaultListModel);
		listaBensCadastrados.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				int index = listaBensCadastrados.locationToIndex(mouseEvent.getPoint());
				leilaoCorrente = leiloes.get(index);
				bemLeiloado.setText(leilaoCorrente.getNome());
				ativarBotoes();
				if(leilaoCorrente.isLeilaoAberto()) {
					sim.setSelected(true);
					limparCampos();
					ativarBotoes();
				}else {
					nao.setSelected(true);
					desabilitarBotoes();
				}
			}
		});
		JScrollPane jScrollPane = new JScrollPane(listaBensCadastrados);
		frame.getContentPane().add(jScrollPane, "cell 0 4 2 1,grow");
		
		bemLeiloado = new JTextField();
		bemLeiloado.setEditable(false);
		frame.getContentPane().add(bemLeiloado, "cell 2 3,growx");
		bemLeiloado.setColumns(10);
		
		panel = new JPanel();
		frame.getContentPane().add(panel, "cell 2 4,grow");
		panel.setLayout(new MigLayout("", "[][grow]", "[][][][][]"));
		
		lblNewLabel_4 = new JLabel("Nome Arrematante");
		panel.add(lblNewLabel_4, "cell 0 0,alignx left");
		
		nomeArrematante = new JTextField();
		nomeArrematante.setEnabled(false);
		panel.add(nomeArrematante, "cell 1 0,growx");
		nomeArrematante.setColumns(10);
		
		lblNewLabel_5 = new JLabel("Valor do lance");
		panel.add(lblNewLabel_5, "cell 0 1,alignx left");
		
		valorLance = new JTextField();
		valorLance.setEnabled(false);
		panel.add(valorLance, "cell 1 1,growx");
		valorLance.setColumns(10);
		
		lblNewLabel_6 = new JLabel("Aberto");
		panel.add(lblNewLabel_6, "cell 0 2");
		
		sim = new JRadioButton("Sim");
		panel.add(sim, "flowx,cell 1 2");
		
		nao = new JRadioButton("N\u00E3o");
		nao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent botaoNao) {
				
				leilaoCorrente.finalizar();
				desabilitarBotoes();
				nomeBem.setText("");
				valorInicial.setText("");
				
				
			}
		});
		panel.add(nao, "cell 1 2");
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(sim);
		buttonGroup.add(nao);
		
		botaoRegistrar = new JButton("Registrar Lance");
		botaoRegistrar.setEnabled(false);
		botaoRegistrar.addActionListener(this);
		panel.add(botaoRegistrar, "cell 0 3 2 1,growx");
		
		gravar = new JButton("Gravar");
		gravar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent botaoGravar) {
				try {
					FileOutputStream fileOutputStream = new FileOutputStream("leiloes.sis");
					ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
					objectOutputStream.writeObject(leiloes);
					objectOutputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		panel.add(gravar, "cell 0 4,growx");
		
		carregar = new JButton("Carregar");
		carregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent botaoCarregar) {
				FileInputStream fileInputStream;
				try {
					fileInputStream = new FileInputStream("leiloes.sis");
					ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
					leiloes = (List<Leilao>)inputStream.readObject();
					defaultListModel.clear();
					for(Leilao leilao : leiloes) {
						defaultListModel.addElement(leilao.getNome());
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		panel.add(carregar, "cell 1 4,growx");
	}
	protected void limparCampos() {
		nomeArrematante.setText("");
		valorLance.setText("");
		
		
	}

	protected void desabilitarBotoes() {
		nomeArrematante.setEnabled(false);
		valorLance.setEnabled(false);
		botaoRegistrar.setEnabled(false);
		
	}

	protected void ativarBotoes() {
		nomeArrematante.setEnabled(true);
		valorLance.setEnabled(true);
		botaoRegistrar.setEnabled(true);
		
	}

	public void actionPerformed(ActionEvent botaoRegistrar) {
		String nome = nomeArrematante.getText();
		double valor = Double.parseDouble(valorLance.getText());
		
		Lance lance = new Lance(nome, valor);
		
		
		leilaoCorrente.addLance(lance);
		
		
		
	}
}
