package modelo;

public class Proyectil extends Interactuable {

	private Boolean dirArriba;
	private float velocidad = 10;
	private float limiteInferior;
	
	public Proyectil(int posX, int posY, boolean direccion, float limiteInferior) {
    	this.posicionX = posX;
    	this.posicionY = posY;
    	this.dirArriba = direccion;
    	this.limiteInferior = limiteInferior;
    	estaActivo = true;
    }


    public void impactar() {
        this.estaActivo = false;
    }

    public void finDeRecorrido() {
    	if (this.posicionY < 0 || this.posicionY > limiteInferior)
    		impactar();
    }
    
    public int[] getPosicion(){
    	return new int[] {this.posicionX, this.posicionY};
    }
    
    public boolean getDireccion() {
    	return this.dirArriba;
    }
    
    public void moverse() {
    	if (dirArriba)
    		this.posicionY += velocidad;
    	else
    		this.posicionY -= velocidad;
    }
    
    public boolean estoyActivo() {
    	return estaActivo;
    }

}