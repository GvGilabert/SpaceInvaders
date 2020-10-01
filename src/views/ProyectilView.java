package views;

public class ProyectilView {
	
	private int posX;
	private int posY;
	private boolean estaActivo;
	
	public ProyectilView() {
		
	}
	
	public ProyectilView(int posX, int posY, boolean estaActivo) {
		this.posX = posX;
		this.posY = posY;
		this.estaActivo = estaActivo;
	}
	
	public int getPosX() {
		return posX;
	}
	
	public int getPosY() {
		return posY;
	}
	
   public boolean estoyActivo() {
	   return estaActivo;
    }
   
   

}
