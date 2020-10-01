package modelo;

import java.util.ArrayList;
import java.util.List;

import views.ProyectilView;

public class Invasor extends Interactuable {

	private float medioAncho = 25;
	private float medioAlto = 25;
	private int velocidad;
	private int limiteIzq;
	private int limiteDer;
	private boolean direccion = true;
	private Partida partida;
	private List<Proyectil> proyectiles;

    public Invasor(int velocidad, int posInicialX, int posInicialY, int limiteIzq, int limiteDer, Partida partida) {
    	proyectiles = new ArrayList<Proyectil>();
    	this.velocidad = velocidad;
    	this.limiteIzq = limiteIzq;
    	this.limiteDer = limiteDer;
    	this.posicionX = posInicialX;
    	this.posicionY = posInicialY;
    	this.partida = partida;
    	estaActivo = true;
    }

    private void otorgarPuntos() {
    	partida.sumarPuntos(10);
    	partida.enemigoDestruido();
    }

    private void moverseX(boolean dirDerecha) {
    	if (dirDerecha)
        	this.posicionX += velocidad;
    	else
    		this.posicionX -= velocidad;
    }

    public void moverse() {
    	if (this.posicionX >= limiteDer && direccion) {
    		direccion = false;
    		this.posicionY += 10;
    	}
    	else if (this.posicionX <= limiteIzq && !direccion) {
    		direccion = true;
    		this.posicionY += 10;
		}
    	moverseX(direccion);
    }

    //CREA E INFORMA A LA PARTIDA UN NUEVO PROYECTIL
    public void disparar() {
         	partida.informarProyectilDeInvasor(new Proyectil(this.posicionX, this.posicionY + 5, true, partida.getArea()[1]-50));
    }

    //GUARDA LOS PROYECTILES DE LA BATERIA
    public void informarProyectil(Proyectil proyectil) { 
     	proyectiles.add(proyectil);
    }
    
    public boolean impacto() {
    	//Primero chequea si está a la altura a la misma altura, si está chequea la posición en X.
    	//La posicion del proyectil esta entre la posicion de la bateria menos la mitad de su ancho y la de la bateria mas la mitad de su ancho.
	
     	for (Proyectil proyectil : proyectiles) {
			if (proyectil.estoyActivo() && this.estaActivo)
	    		if(proyectil.posicionY < this.posicionY+medioAlto && proyectil.posicionY > this.posicionY-medioAlto )
	    			if(proyectil.posicionX < this.posicionX+medioAncho && proyectil.posicionX > this.posicionX-medioAncho) 
	    			{
	    				this.estaActivo = false;
	    				proyectil.estaActivo = false;
	    				otorgarPuntos();
	    				return true;
	    			}  
		}
    	return false;
    }
    
    public List<Proyectil> getProyectiles(){
    	return proyectiles;
    }
    
    public int[] getPosicion(){
    	return new int[] {this.posicionX, this.posicionY};
    }
    
    public boolean estoyActivo() {
    	return estaActivo;
    }
    
    //DEVOLER VIEWS DE PROYECTILES
    public List<ProyectilView> getProyectilesView(){
    	List<ProyectilView> resultado = new ArrayList<ProyectilView>();
    	for (Proyectil proyectil : proyectiles) {
			resultado.add(new ProyectilView(proyectil.getPosicion()[0],proyectil.getPosicion()[1],proyectil.estoyActivo()));
		}
    	return resultado;
    }
}