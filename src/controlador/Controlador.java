package controlador;
import java.util.List;

import modelo.HighScore;
import modelo.Partida;
import views.BateriaView;
import views.InvasorView;
import views.MuroView;
import views.ProyectilView;

public class Controlador {
	
	private Partida partida;
	private HighScore highscore;
	
    public Controlador(HighScore highscore) {
    	this.highscore = highscore;
    }
  
    public void moverBateria(boolean movDerecha) {
        partida.moverBateria(movDerecha);
    }

    public void disparar() {
        partida.disparar();
    }

    public void iniciarPartida(String nombre, int dificultad) {
    	partida = new Partida(nombre, dificultad, highscore);
    }

    public boolean finalizarPartida() {
        return partida.noHayMasVidas();
    }
    
    public int[] getArea() {
    	return partida.getArea();
    }
    
    public int getPuntos() {
    	return partida.getPuntos();
    }
    
    public int getVidas() {
    	return partida.getVidas();
    }
    
    public void atacarInvasores() {
    	partida.atacarInvasores();
    }
     
    public boolean impactarBateria() {
    	return partida.impactarBateria();
    }
    
    public boolean bateriaEstaActiva() {
    	return partida.bateriaEstaActiva();
	}
    
    public void renacer() {
    	partida.renacer();
    }
    
    public void finDeNivel() {
    	partida.finDeNivel();
    }
    
    public void refrescarObjetos() {
    	partida.refrescarObjetos();
    }  
    
    //Leer puntaje de archivo
    public List<String> leerPuntosFile() {
    	return highscore.leerPuntosFile();
    }
    //
    public int getNivel() {
    	return partida.getNivel();
    }
    
    public void darVidaExtra() {
    	partida.darVidaExtra();
    }
    
    //OBTENER VIEWS
    public List<ProyectilView> getProyectiles(){
    	return partida.getProyectilesViews();
    }
    
    public BateriaView getBateriaView() {
    	return partida.getBateriaView();
    }
    
    public List<InvasorView> getInvasoresViews(){
    	return partida.getInvasoresViews();
    }
    
    public List<MuroView> getMuros(){
    	return partida.getMurosViews();
    }
}