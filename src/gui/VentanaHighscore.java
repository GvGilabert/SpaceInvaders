package gui;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;

import controlador.Controlador;

@SuppressWarnings("serial")
public class VentanaHighscore extends JFrame {
	private JLabel labelTitulo;
	private JList<String> labelPodio;
	private Controlador controlador;
	
	public VentanaHighscore(Controlador controlador) {
		this.controlador = controlador;
		this.setTitle("Highscore");
		this.setSize(450, 400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.config();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
		
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void config() {
		Container contenedor = this.getContentPane();
		contenedor.setLayout(new GridLayout(2,1));
		labelTitulo = new JLabel("LOS 10 MEJORES");
		labelTitulo.setFont(new Font("Verdana", Font.PLAIN, 20));
		labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		labelPodio = new JList(controlador.leerPuntosFile().toArray());
		
		labelPodio.setFont(new Font("Courier", Font.PLAIN, 16));
		labelPodio.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		
		contenedor.add(labelTitulo);
		contenedor.add(labelPodio);
	}
	
	
}