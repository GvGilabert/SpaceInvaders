package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import views.BateriaView;
import views.InvasorView;
import views.MuroView;
import views.ProyectilView;

public class Partida {


	private int velocidadEnemigosBase = 2; //VELOCIDAD BASE PARA LOS ENEMIGOS
	private float velocidadBateria = 10; //VELOCIDAD DE BATERIA
	private int areaTamañoX = 600;
	private int areaTamañoY = 600;
	
    private int nivel;
    private int puntaje = 0;
    private int vidas = 3;
    private int vidaExtraCount=500;
    private int dificultad;
    private int enemigosDestruidos;
    private int [] PosInicialMuros = new int [] {50,190,330,470} ; //TODO> CALCULAR EN BASE A AREA
    private int [] PosInicialInvasores = new int [] {80,160,240,320,400} ; //TODO> CALCULAR EN BASE A AREA
    private String nombreJugador;
    private List<Invasor> invasores;
    private List<BloqueMuroDeEnergia> muros;
    private List<Proyectil> proyectiles;
    private Bateria bateria;
    private HighScore highscore;

    public Partida(String nombre, int dificultad, HighScore highscore) {
    	
    	this.dificultad = dificultad;
    	this.nombreJugador = nombre;
    	this.bateria = new Bateria(velocidadBateria, this,areaTamañoX/2, areaTamañoY-100);
    	this.highscore = highscore;
    	nuevoNivel(1);
    }
    private List<Invasor> crearInvasores(){
    	int posInicialInvasoresY = 0;
    	List<Invasor> invasores = new ArrayList<Invasor>();
    	int count = 0; 
    	for (int i = 0; i < 15; i++) {//CONDICION ES LA CANTIDAD DE INVASORES A CREAR
    		invasores.add(
    				new Invasor(
    						calcularVelocidadEnemigo(Dificultad.values()[dificultad]),
    						PosInicialInvasores[count], //POSICION EN EL EJE X
    						posInicialInvasoresY,		//POSICION EN EL EJE Y
    						PosInicialInvasores[count]-70, //LIMITE IZQ DE MOVIMIENTO RELATIVO A LA POSICION INICIAL, EVITA CHOCAR CON LOS OTROS INVASORES
    						PosInicialInvasores[count]+150,//LIMITE DER DE MOVIMIENTO RELATIVO A LA POSICION INICIAL, EVITA CHOCAR CON LOS OTROS INVASORES
    						this)); //REFERENCIA A PARTIDA
    		count++;
    		if (count == 5) 
    		{
    			posInicialInvasoresY += 50;
    			count = 0;
    		}
		}
    	return invasores;
    }
    
    private List<BloqueMuroDeEnergia> crearMuros(){
    	int posInicialMurosY = areaTamañoY-250;
    	
    	for (int i = 0; i < 4; i++) {//CONDICION ES LA CANTIDAD DE GRUPOS DE BLOQUES A CREAR
    		for (int j = 0; j < 3; j++) {
    			muros.add(
        				new BloqueMuroDeEnergia(
        						PosInicialMuros[i]+j*25, //POSICION EN EL EJE X
        						posInicialMurosY));		//POSICION EN EL EJE Y
			}	
		}
    	return muros;
    }

    private void nuevoNivel(int nivel) {
    	this.nivel = nivel;
    	invasores = new ArrayList<Invasor>();
    	proyectiles = new ArrayList<Proyectil>();
    	muros = new	ArrayList<BloqueMuroDeEnergia>();
    	muros = crearMuros();
    	invasores = crearInvasores();
    	enemigosDestruidos=0;
    }
    
    private int calcularVelocidadEnemigo(Dificultad dificultad) 
    {
    	if (dificultad == Dificultad.Cadete)
			return velocidadEnemigosBase * nivel;
    	else if (dificultad == Dificultad.Guerrero)
    		return (int)(1.5 * velocidadEnemigosBase * nivel); 
    	else
    		return 2 * velocidadEnemigosBase * nivel;
    }

    public void finDeNivel() {
    	if (enemigosDestruidos==15) {
    		puntaje += 200;
    		nuevoNivel(++nivel);
    	}
    }
    
    public void darVidaExtra() {
        if(puntaje > vidaExtraCount) {
        	vidas++;
        	vidaExtraCount += 500;
        }
    }

    public void perderVida() {
    	this.vidas--;
    }
    
    public void disparar() {
        Proyectil proyectil = bateria.disparar();
        informarProyectilDeBateria(proyectil);
    }

    public void moverBateria(Boolean dir) {
		bateria.moverseX(dir);
    }	
    
    public boolean noHayMasVidas() {
    	if(vidas <= 0) {
    		setNuevoMejorPuntaje(nombreJugador+";"+puntaje);
    		return true;
    	}
    	return false;
    }
    
    public void informarProyectilDeBateria(Proyectil proyectil) {
    	for (Invasor invasor : invasores) {
    		invasor.informarProyectil(proyectil);
		}
    	for (BloqueMuroDeEnergia bloque : muros) {
    		bloque.informarProyectil(proyectil);		
		}
    }
    
    public void informarProyectilDeInvasor(Proyectil proyectil) {
    	bateria.informarProyectil(proyectil);
    	for (BloqueMuroDeEnergia bloque : muros) {
    		bloque.informarProyectil(proyectil);		
		}
    }
          
    public int[] getArea() {
    	return new int[] {areaTamañoX,areaTamañoY};
    }
    
    public List<Proyectil> getProyectiles(){
    	List<Proyectil> proyectiles = new ArrayList<Proyectil>();
    	
    	if (!bateria.getProyectiles().isEmpty())
    		proyectiles.addAll(bateria.getProyectiles()); //SUMA TODOS LOS PROYECTILES DE LA BATERIA
    	
    	if (!invasores.isEmpty())
    		if(!invasores.get(0).getProyectiles().isEmpty())
    			proyectiles.addAll(invasores.get(0).getProyectiles()); //SUMA TODOS LOS PROYECTILES DE LOS INVASORES
    	
    	return proyectiles;
    }
       
    public void sumarPuntos(int puntos) {
    	this.puntaje += puntos;
    }
    
    public int getPuntos() {
    	return puntaje;
    }
    
    public int getVidas() {
    	return vidas;
    }
    
    public void enemigoDestruido() {
    	this.enemigosDestruidos++;
    }
    
    public void refrescarObjetos() {
    	
    	//PROYECTILES
    	proyectiles = getProyectiles();
    	for (Proyectil proyectil : proyectiles) {
			if (proyectil.estoyActivo()) {
				proyectil.moverse(); //MUEVO EL PROYECTIL
				proyectil.finDeRecorrido();
			}
		}
    	
    	//INVASORES
    	for (Invasor invasor : invasores) {
			invasor.moverse();
			invasor.impacto();
		}
    	
    	//MUROS
    	for (BloqueMuroDeEnergia muro : muros) {
			muro.impacto();
		}  	
    }
     
    public boolean impactarBateria() {
    	return bateria.impacto();
    }
    
    public void atacarInvasores() {
		for (Invasor invasor : invasores) {
			if (invasor.estoyActivo()) {
				if ((new Random().nextInt(15) == 1)) {
					invasor.disparar();
				}
			}
		}
	}
    
    public boolean bateriaEstaActiva() {
    	return bateria.estaActivo;
    }
    
    public void renacer() {
    	muros = crearMuros();
    	bateria.renacer();
    }
    
    public List<String> leerPuntosFile() {
    	return highscore.leerPuntosFile();
    }
    
    public void setNuevoMejorPuntaje(String puntaje) {
    	highscore.setNuevoMejorPuntaje(puntaje);
    }
    
    public int getNivel() {
    	return nivel;
    }
    
    //VIEWS
    public BateriaView getBateriaView() {
    	return bateria.getBateriaView();
    }
    
    public List<ProyectilView> getProyectilesViews(){
    	List<ProyectilView> proyectiles = new ArrayList<ProyectilView>();
    	
    	if (!bateria.getProyectileViews().isEmpty())
    		proyectiles.addAll(bateria.getProyectileViews()); //SUMA TODOS LOS PROYECTILES DE LA BATERIA
    	
    	if (!invasores.isEmpty())
    		if(!invasores.get(0).getProyectiles().isEmpty())
    			proyectiles.addAll(invasores.get(0).getProyectilesView()); //SUMA TODOS LOS PROYECTILES DE LOS INVASORES
    	
    	return proyectiles;
    }
    
    public List<InvasorView> getInvasoresViews(){
    	List<InvasorView> invasoresView = new ArrayList<InvasorView>();
    	
    	for (Invasor invasor : invasores) {
			invasoresView.add(new InvasorView(invasor.getPosicion()[0],invasor.getPosicion()[1],invasor.estoyActivo()));
		}
    	return invasoresView;
    }
     
    public List<MuroView> getMurosViews(){
    	List<MuroView> murosView = new ArrayList<MuroView>();
    	for (BloqueMuroDeEnergia bloqueMuroDeEnergia : muros) {
			murosView.add(new MuroView(bloqueMuroDeEnergia.getPosicion()[0],bloqueMuroDeEnergia.getPosicion()[1],bloqueMuroDeEnergia.estoyActivo())); 
		}
    	return murosView;
    }
}