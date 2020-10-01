package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import controlador.Controlador;
import views.InvasorView;
import views.MuroView;
import views.ProyectilView;

@SuppressWarnings("serial")
public class VentanaPrincipal extends JFrame {
	private JLabel bateriaLabel;
	private List<JLabel> proyectilesLabel;
	private List<JLabel> invasoresLabel;
	private List<JLabel> murosLabel;
	private Controlador controlador;
	private Timer timer;
	private int[] area;
	boolean respawning = false;
	private JLabel puntos;
	private JLabel vidas;
	private JLabel pausa;
	private JLabel nivel;


	public VentanaPrincipal (String nombre, int dificultad, Controlador controlador){
		
		proyectilesLabel = new ArrayList<JLabel>();
		invasoresLabel = new ArrayList<JLabel>();
		murosLabel = new ArrayList<JLabel>();
		this.controlador = controlador;
		controlador.iniciarPartida(nombre, dificultad);
		area = controlador.getArea();
		
		this.setTitle("Space Invaders TPO Grupo 4");
		this.setSize(area[0],area[1]);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		config();
		eventos();
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private void config() {
		Container contenedor = this.getContentPane();
		contenedor.setLayout(null);
		contenedor.setBackground(Color.black);
		
		puntos = new JLabel("PUNTOS:" + controlador.getPuntos());
		puntos.setBounds(area[1]-100, area[0]-100, 100, 100);
		puntos.setForeground(Color.white);
		contenedor.add(puntos);
		
		vidas = new JLabel("VIDAS: "+controlador.getVidas());
		vidas.setBounds(20, area[1]-100, 100, 100);
		vidas.setForeground(Color.white);
		contenedor.add(vidas);
		
		nivel = new JLabel("NIVEL: "+controlador.getNivel());
		nivel.setBounds(20, 0, 100, 100);
		nivel.setForeground(Color.white);
		contenedor.add(nivel);
		
		bateriaLabel = new JLabel();
		bateriaLabel.setIcon(new javax.swing.ImageIcon(
				getClass().getResource("nave.png")));
		bateriaLabel.setBounds(0, area[1]-100, 50, 50);
		contenedor.add(bateriaLabel);
		
		pausa = new JLabel("PAUSA");
		pausa.setBounds((int)(area[0]/2.9),(int)(area[1]/4), 200, 200);
		pausa.setFont(new Font("Verdana", Font.PLAIN, 38));
		pausa.setVisible(false);
		pausa.setForeground(Color.white);
		contenedor.add(pausa);
		
	}
	
	private void limpiarProyectiles() {
		for (JLabel proyectil : proyectilesLabel) {
			proyectil.setVisible(false);
		}
	}
	
	private JLabel crearLabelProyectil(){
		JLabel proyectil = new JLabel();
		proyectil.setIcon(new javax.swing.ImageIcon(
				getClass().getResource("laser.png")));
		proyectil.setBounds(0, 0, 5, 22);
		getContentPane().add(proyectil);
		return proyectil;
	}
	
	private JLabel crearLabelInvasor(){
		JLabel invasor = new JLabel();
		invasor.setIcon(new javax.swing.ImageIcon(
				getClass().getResource("invasor.png")));
		invasor.setBounds(0, 0, 50, 50);
		getContentPane().add(invasor);
		return invasor;
	}
	
	private JLabel crearLabelMuro() {
		JLabel muro = new JLabel();
		muro.setIcon(new javax.swing.ImageIcon(
				getClass().getResource("bloque.png")));
		muro.setBounds(0, 0, 22, 22);
		getContentPane().add(muro);
		return muro;
	}
	
	private void eventos (){
		timer = new Timer(35, new ManejoTimer());
		timer.start();
		
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==37 && timer.isRunning() && bateriaLabel.isVisible()) {//FLECHA IZQ
					controlador.moverBateria(false);
				}
				else if (e.getKeyCode()==39 && timer.isRunning() && bateriaLabel.isVisible()) {//FLECHA DER
					controlador.moverBateria(true);
				}
				else if (e.getKeyCode()==32 && timer.isRunning() && bateriaLabel.isVisible()) {//BARRA ESPACIADORA
					controlador.disparar();
				}
				else if (e.getKeyCode()==80){//LETRA P
					pausa();
				}
				else if (e.getKeyCode()==72){//LETRA H
					highScore();
				}
			}
		});
		
	}
	
	public void pausa() {
		if(timer.isRunning()) {
			timer.stop();
			pausa.setVisible(true);
		}
		else {
			timer.start();
			pausa.setVisible(false);
		}
	}
	
	public void highScore() {
		pausa();
		new VentanaHighscore(controlador);
	}

	private void actualizarProyectiles() {
		
		limpiarProyectiles(); //desactivar todos los labels de proyectil en pantalla
		List<ProyectilView> proyectiles = controlador.getProyectiles();

		while (proyectilesLabel.size() < proyectiles.size()) { //Si hay más proyectiles instanciados que labels para mostrarlos crea las necesarias
			proyectilesLabel.add(crearLabelProyectil());
		}
		
		for (int i = 0; i < proyectiles.size(); i++) {
			JLabel label = proyectilesLabel.get(i); //TRAIGO UN LABEL POR INDICE
			ProyectilView proyectil = proyectiles.get(i); //TRAIGO UN PROYECTIL POR INDICE
			if (proyectil.estoyActivo()) {
				label.setBounds(proyectil.getPosX(),proyectil.getPosY(),200 ,200 ); // Posiciona el label del proyectil, 
				label.setVisible(true); //LO HAGO VISIBLE
			}
		}
	}
	
	private void actualizarBateria() {
		if(controlador.bateriaEstaActiva()) {
			bateriaLabel.setBounds(controlador.getBateriaView().getPosicionX(), bateriaLabel.getY(), bateriaLabel.getWidth(), bateriaLabel.getHeight());
			if(controlador.impactarBateria()) {
				bateriaLabel.setVisible(false);
			}
		}
		else
			respawning = true;
	}
	
	private void actualizarPuntos() {
		puntos.setText("PUNTOS: "+controlador.getPuntos());
		nivel.setText("NIVEL: "+controlador.getNivel());
		vidas.setText("VIDAS: "+controlador.getVidas());
		
	}
	
	private void actualizarInvasores() {
		List<InvasorView> invasores = controlador.getInvasoresViews();
		while (invasoresLabel.size() < invasores.size()) { //Si hay más invasores instanciados que labels para mostrarlos crea las necesarias
			invasoresLabel.add(crearLabelInvasor());
		}
		
		for (int i = 0; i < invasores.size(); i++) {
			JLabel label = invasoresLabel.get(i); //TRAIGO UN LABEL POR INDICE
			InvasorView invasor = invasores.get(i); //TRAIGO UN INVASOR POR INDICE

			if (invasor.estoyActivo()) {
				
				label.setBounds(invasor.getPosX(),invasor.getPosY(),200 ,200 ); // Posiciona el label del proyectil, 
				label.setVisible(true); //LO HAGO VISIBLE
			}
			else
				label.setVisible(false);
		}
	}
	
	private void actualizarMuros() {
		List<MuroView> muros = controlador.getMuros();
		while (murosLabel.size() < muros.size()) { //Si hay más invasores instanciados que labels para mostrarlos crea las necesarias
			murosLabel.add(crearLabelMuro());
		}
		
		for (int i = 0; i < muros.size(); i++) {
			JLabel label = murosLabel.get(i); //TRAIGO UN LABEL POR INDICE
			MuroView muro = muros.get(i); //TRAIGO UN MURO POR INDICE
			
			if (muro.estoyActivo()) {
				label.setBounds(muro.getPosX(),muro.getPosY(),200 ,200 ); 
				label.setVisible(true); //LO HAGO VISIBLE
			}
			else
				label.setVisible(false);
		}
	}
	
	private void finalizarPartida() {
		if(controlador.finalizarPartida()) {
			timer.stop();
			JOptionPane.showMessageDialog(getContentPane(),
				    "PERDISTE!",
				    "GAME OVER",
				    JOptionPane.ERROR_MESSAGE);
			dispose();
			controlador.leerPuntosFile();
			new VentanaMenu();
		}
	}
	
	//TIMER
	class ManejoTimer implements ActionListener {
		int countInvasorAtaca = 0;
		int countRespawn = 0;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			countInvasorAtaca++;
			controlador.refrescarObjetos();
			actualizarProyectiles(); // REVISAR
			actualizarBateria();
			actualizarInvasores();
			actualizarMuros();
			actualizarPuntos();
			controlador.finDeNivel();
			controlador.darVidaExtra();
			finalizarPartida();
			
			if (countInvasorAtaca >= 50 && bateriaLabel.isVisible()) //SI PASO EL TIEMPO INDICADO Y LA BATERIA ESTA ACTIVA
			{
				countInvasorAtaca = 0;
				controlador.atacarInvasores();
			}
			
			if (respawning)
				countRespawn++;
			if (countRespawn > 70) {
				controlador.renacer();
				respawning = false;
				bateriaLabel.setVisible(true);
				countRespawn = 0;
			}	
		}
	}
}
