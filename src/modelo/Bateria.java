package modelo;

import java.util.ArrayList;
import java.util.List;

import views.BateriaView;
import views.ProyectilView;

public class Bateria extends Interactuable {

	private float medioAncho = 25;
	private float velocidad;
	
	private Partida partida;
	private List<Proyectil> proyectiles;
	
    public Bateria(float velocidad, Partida partida, int posInicialX, int posInicialY) {
    	this.velocidad = velocidad;
    	proyectiles = new ArrayList<Proyectil>();
    	this.partida = partida;
    	this.posicionX = posInicialX;
    	this.posicionY = posInicialY;
    	this.estaActivo = true;
    }

    public void moverseX(Boolean dirDerecha) {

    	if (dirDerecha && this.posicionX < partida.getArea()[0] -40 )
 		   this.posicionX += velocidad;
    	else if (!dirDerecha && this.posicionX > 0) {			
    		this.posicionX -= this.velocidad;
		}
    }
    
    public Proyectil disparar() {
        return new Proyectil(this.posicionX + 25, this.posicionY - 80, false, partida.getArea()[1]);
    }
    
    public boolean impacto() {
    	//Primero chequea si está a la altura a la misma altura, si está chequea la posición en X.
    	//La posicion del proyectil esta entre la posicion de la bateria menos la mitad de su ancho y la de la bateria mas la mitad de su ancho.
    	
    	for (Proyectil proyectil : proyectiles) {
    		if (proyectil.estoyActivo() && this.estaActivo)
	    		if(proyectil.posicionY > this.posicionY -65)
	    			if(proyectil.posicionX < this.posicionX+medioAncho && proyectil.posicionX > this.posicionX)  
	    			{
	    				proyectil.estaActivo = false;
	    				morir();
	    				return true;
	    			}  
		}
    	return false;
    }
    
    @Override
    	protected void morir() {
    		super.morir();
    		partida.perderVida();
    		posicionX = (partida.getArea()[0]/2)-30;
    		estaActivo=false;
    	}
    
    public void informarProyectil(Proyectil proyectil) { 
    	proyectiles.add(proyectil);
    }
    
    public List<Proyectil> getProyectiles(){
    	return proyectiles;
    }
    
    public boolean estoyActivo() {
    	return this.estaActivo;
    }
    
    public void renacer() {
    	this.estaActivo = true;
    }
    
    //DEVOLVER VIEWS
    public BateriaView getBateriaView() {
    	return new BateriaView(posicionX) {
    	};
    }
    
    public List<ProyectilView> getProyectileViews(){
    	List<ProyectilView> resultado = new ArrayList<ProyectilView>();
    	for (Proyectil proyectil : proyectiles) {
			resultado.add(new ProyectilView(proyectil.getPosicion()[0],proyectil.getPosicion()[1],proyectil.estoyActivo()));
		}
    	return resultado;
    }
}