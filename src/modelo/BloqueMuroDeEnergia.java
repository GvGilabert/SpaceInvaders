package modelo;

import java.util.ArrayList;
import java.util.List;

public class BloqueMuroDeEnergia extends Interactuable {

	private float medioAncho = 20;
    private int porcDegradacion;
    
    private List<Proyectil> proyectiles;

    public BloqueMuroDeEnergia(int posX, int posY) {
    	proyectiles = new ArrayList<Proyectil>();
    	this.posicionX = posX;
    	this.posicionY = posY;
    	estaActivo = true;
    	porcDegradacion = 100;
    }
    
    private void degradarse(boolean dirDisparo) {
       if(dirDisparo)
    	   this.porcDegradacion -= 5;
       else 
    	   this.porcDegradacion -= 20;
       
       if(porcDegradacion <=0)
    	   morir();
    }
    
    public void informarProyectil(Proyectil proyectil) { 
    	proyectiles.add(proyectil);
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
	    				degradarse(proyectil.getDireccion());
	    				return true;
	    			}  
		}
    	return false;
    }
    
    @Override
    	protected void morir() {
    		super.morir();
    		estaActivo=false;
    		
    	}
    
    public int[] getPosicion(){
    	return new int[] {this.posicionX, this.posicionY};
    }
    
    public boolean estoyActivo() {
    	return estaActivo;
    }
}