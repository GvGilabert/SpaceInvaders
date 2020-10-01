package gui;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controlador.Controlador;
import modelo.Dificultad;
import modelo.HighScore;

@SuppressWarnings("serial")
public class VentanaMenu extends JFrame {
	
	private JLabel labelTitulo;
	private JLabel labelNombre;
	private JTextField fieldNombre;
	private JButton botonStart;
	private JButton botonHighScore;
	private JComboBox<String> dificultadCombo;
	private JLabel labelDificultad;
	private Controlador controlador;
	
	public VentanaMenu(){
		this.setTitle("MENU");
		this.setSize(450,400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.configMenu();
		this.eventosMenu();
		this.setVisible(true);
		controlador = new Controlador(new HighScore());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void configMenu() {
		Container contenedor = this.getContentPane();
		contenedor.setLayout(new GridLayout(8,1));

		labelTitulo = new JLabel("SPACE INVADERS TPO");
		labelTitulo.setFont(new Font("Verdana", Font.PLAIN, 28));
		labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		
		labelNombre = new JLabel("Ingrese su nombre");
		labelNombre.setFont(new Font("Courier", Font.PLAIN, 20));
		labelNombre.setHorizontalAlignment(SwingConstants.CENTER);
		
		labelDificultad = new JLabel("Dificultad");
		dificultadCombo = new JComboBox(Dificultad.values());
		
		fieldNombre = new JTextField("JUGADOR 1");
		fieldNombre.setHorizontalAlignment(JTextField.CENTER);
		fieldNombre.setFont(new Font("Courier", Font.PLAIN, 24));
		
		botonStart = new JButton("INICIAR PARTIDA");
		
		botonHighScore = new JButton("VER TABLA DE POSICIONES");
		
		contenedor.add(labelTitulo);
		contenedor.add(labelNombre);
		contenedor.add(fieldNombre);
		contenedor.add(labelDificultad);
		contenedor.add(dificultadCombo);
		contenedor.add(new JLabel(""));
		contenedor.add(botonStart);
		contenedor.add(botonHighScore);
	}
	
	private void eventosMenu() {
		botonStart.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new VentanaPrincipal(fieldNombre.getText(),dificultadCombo.getSelectedIndex(), controlador);
			}
		});
		botonHighScore.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaHighscore(controlador);
			}
		});
	}
}
