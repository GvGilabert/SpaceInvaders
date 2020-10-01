package modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class HighScore {
	private Path filepath = Paths.get("src/gui/puntos.txt");
	public HighScore() {

    }
	
	private List<String> LeerArchivo(){
		List<String> resultados = new ArrayList<String>();
		try {
				
				File myObj = new File(filepath.toString());
			Scanner myReader = new Scanner(myObj);

			while (myReader.hasNextLine()) {
		    	String data = myReader.nextLine();
		        resultados.add(data);
		      }
		      myReader.close();
		    } 
		
		catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
		resultados.sort(new Comparator<String>() {
			@Override
		    public int compare(String m1, String m2) {
		        if(Integer.parseInt(m1.split(";")[1]) == Integer.parseInt(m1.split(";")[1]))
		            return 0;
		        else if(Integer.parseInt(m1.split(";")[1]) < Integer.parseInt(m1.split(";")[1]))
		        	return 1;
		        else 
		        	return -1;
				}});
		return resultados;
	}
    
	public List<String> leerPuntosFile() {
		List<String> resultados = LeerArchivo();
		
		resultados.sort(new Comparator<String>() {
			@Override
		    public int compare(String m1, String m2) {
		        if(Integer.parseInt(m1.split(";")[1]) == Integer.parseInt(m2.split(";")[1]))
		            return 0;
		        else if(Integer.parseInt(m1.split(";")[1]) < Integer.parseInt(m2.split(";")[1]))
		        	return 1;
		        else 
		        	return -1;
				}});
		
		List<String> ret = new ArrayList<String>();
		int pos = 0;
		for (String highScore : resultados) {
			ret.add(++pos+ " -  " + highScore.split(";")[0] +":"+highScore.split(";")[1]);
		}
		return ret;
	}
	
	public void setNuevoMejorPuntaje(String puntaje) {
		List<String> archivo = LeerArchivo();
		String[] nombre = new String[archivo.size()];
		int[] puntos = new int[archivo.size()];
		
		for (int i = 0; i < archivo.size(); i++) {
			nombre[i] = archivo.get(i).split(";")[0];
			puntos[i] = Integer.parseInt(archivo.get(i).split(";")[1]);
			if (Integer.parseInt(puntaje.split(";")[1]) > puntos[i]) {
				archivo.set(i, puntaje);
				break;
			}
		}

		PrintWriter writer;
		try {
			writer = new PrintWriter(Paths.get("src/gui/puntos.txt").toString());
			for (String line : archivo) {
		        writer.println(line.toString());
		    }
		    writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    

		

	}
}