package views;

public class MuroView {
	private int posX;
	private int posY;
	private boolean estaActivo;
	
	public MuroView() {
		
	}
	
	public MuroView(int posX, int posY, boolean estaActivo) {
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
